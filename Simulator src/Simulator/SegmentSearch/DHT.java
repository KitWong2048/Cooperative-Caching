package Simulator.SegmentSearch;

import Simulator.*;
import Simulator.Peer.*;
import java.util.*;

public class DHT implements ISegmentSearch
{

	TreeMap<Integer, PeerList> segAvailabilityMap; // <segment id, peer list>

	public class PeerList
	{
		Vector<Integer> list;

		public PeerList()
		{
			list = new Vector<Integer>();
		}

		public boolean add(int e)
		{
			return list.add(e);
		}

		public Integer remove(int index)
		{
			return list.remove(index);
		}

		public Integer get(int index)
		{
			return list.get(index);
		}

		public int size()
		{
			return list.size();
		}
	}

	public void buildMap(Simulator sim)
	{
		int numSeg = sim.getConfig().numSegment;
		segAvailabilityMap = new TreeMap<Integer, PeerList>();
		for (int s = 0; s < numSeg; s++)
		{
			segAvailabilityMap.put(s, new PeerList());
		}
		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		while (iter.hasNext())
		{
			Peer peer = iter.next();
			for (int s = 0; s < numSeg; s++)
			{
				if (peer.cached(s))
					segAvailabilityMap.get(s).add(peer.pid);
			}
		}
	}

	public SearchResult search(Simulator sim)
	{
		System.out.println("DHT::search()");

		System.out.println("buildMap");
		buildMap(sim);

		System.out.println("start search");
		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		Config config = sim.getConfig();
		double[] popularity = sim.getPopularity();
		double dist = 0;
		double latency = 0;
		double hitrate = 0;

		while (iter.hasNext())
		{
			Peer peer = iter.next();
			for (int s = 0; s < config.numSegment; s++)
			{
				// System.out.println("seg=" + s);
				dist += popularity[s] * this.getD1(sim, peer.pid, s);
				// System.out.println("dist="+dist);
				latency += popularity[s] * lookup(sim, peer.pid, s);
				// System.out.println("latency="+latency);
				if (segAvailabilityMap.get(s).size() > 0)
				{
					hitrate += popularity[s];
				}
			}
			System.out.println("DHT search pid=" + peer.pid);
		}

		dist /= pc.size();
		latency /= pc.size();
		hitrate /= pc.size();

		SearchResult sr = new SearchResult();
		sr.segmentDistance = dist;
		sr.latency = latency;
		sr.hitRate = hitrate;
		return sr;
	}

	public double getD1(Simulator sim, int pid, int sid)
	{
		Config config = sim.getConfig();
		PeerCollection pc = sim.getPeerCollection();
		Peer peer = pc.get(pid);
		if (peer.cached(sid))
			return 0;
		double d1 = 0;

		/*
		 * if (sid == 10) { System.out.println("peerSet.size() = " +
		 * peerSet.size()); }
		 */
		// if (peerSet.size() == 0) {
		if (segAvailabilityMap.get(sid).size() == 0)
		{
			d1 = sim.getDistance().d[pid][sim.getServerID()];
		}
		else
		{
			Random r = new Random();
			Vector<Integer> peerSet = new Vector<Integer>();
			int size = segAvailabilityMap.get(sid).size();
			for (int i = 0; i < size; i++)
				peerSet.add(segAvailabilityMap.get(sid).get(i));
			while (peerSet.size() > config.tableRowSize)
			{
				int index = r.nextInt(peerSet.size());
				peerSet.remove(index);
			}
			int minDist = 9999;
			for (int i = 0; i < peerSet.size(); i++)
			{
				if (sim.getDistance().d[pid][peerSet.get(i)] < minDist)
					minDist = sim.getDistance().d[pid][peerSet.get(i)];
			}
			d1 = minDist;
		}
		return d1;
	}

	public class Node
	{ // DHT node
		public Node()
		{
		}

		public Node(int sid, int pid)
		{
			this.sid = sid;
			this.pid = pid;
		}

		public void set(Node node)
		{
			this.sid = node.sid;
			this.pid = node.pid;
		}

		public int sid;
		public int pid;
	}

	// peer (pid) performs a DHT lookup of a segment (objectId)
	// return the search latency
	public double lookup(Simulator sim, int pid, int objectId)
	{

		/*
		 * if (segAvailabilityMap.get(objectId).size() == 0) { return
		 * sim.getDistance().d[pid][sim.getServerID()]; }
		 */

		PeerCollection pc = sim.getPeerCollection();
		if (pc.get(pid).cached(objectId))
			return 0;
		boolean found = false;
		int numSeg = sim.getConfig().numSegment;

		// initialize id of requestor
		int sid = 0;
		for (int s = 0; s < numSeg; s++)
		{
			if (pc.get(pid).cached(s))
			{
				sid = s;
				break;
			}
		}

		Node successor = new Node(sid, pid);
		Node pred = new Node();
		// System.out.println("object id = " + objectId);
		// System.out.println("successor pid = " + successor.pid + ", sid = " +
		// successor.sid);
		int numIteration = 0;
		double latency = 0;
		while (!found)
		{
			pred.set(successor);
			successor = getSuccessor(sim, successor, objectId);
			if (successor == null)
				break;
			// System.out.println("successor pid = " + successor.pid +
			// ", sid = " + successor.sid);
			latency += sim.getDistance().d[pred.pid][successor.pid];
			/*
			 * if (pc.get(successor.pid) == null) {
			 * 
			 * System.out.println("null? successor pid = " + successor.pid +
			 * ", sid=" + successor.sid + ", exist=" + pc.exist(successor.pid));
			 * System.exit(1); }
			 */
			if (pc.get(successor.pid).cached(objectId))
				found = true;
			numIteration++;
			if (numIteration > 7)
			{
				System.out.println("warning: DHT not found, visit server");
				// return sim.getDistance().d[pid][sim.getServerID()];
				// System.exit(1);
				return latency;
			}
		}
		return latency;
	}

	public Node getSuccessor(Simulator sim, Node node, int objectId)
	{
		int numSeg = sim.getConfig().numSegment;
		// int numBit = new Double(Math.log(numSeg) / Math.log(2)).intValue();
		// System.out.println("numBit = " + numBit);

		int diff = (objectId - node.sid < 0) ? (objectId - node.sid + numSeg)
				: (objectId - node.sid);
		int k = new Double(Math.log(diff) / Math.log(2)).intValue() + 1;
		int fingerStart = new Double(node.sid + Math.pow(2, k - 1)).intValue()
				% numSeg;
		int fingerEnd = new Double(node.sid + Math.pow(2, k)).intValue()
				% numSeg;

		// System.out.println("object = " + objectId + ", k = " + k +
		// ", int = [" + fingerStart + "," + fingerEnd + ")");

		Vector<Node> successorList = new Vector<Node>();
		int fingerIndex = fingerStart;
		Random r = new Random();
		while (true)
		{
			// System.out.print(fingerIndex + " ");
			if (segAvailabilityMap.get(fingerIndex).size() > 0)
			{
				/*
				 * int index =
				 * r.nextInt(segAvailabilityMap.get(fingerIndex).size());
				 * successorList.add(new Node(fingerIndex,
				 * segAvailabilityMap.get(fingerIndex).get(index)));
				 */
				int size = segAvailabilityMap.get(fingerIndex).size();
				int minDist = 999999;
				int minDistPeerId = 0;
				for (int i = 0; i < size; i++)
				{
					int successorId = segAvailabilityMap.get(fingerIndex)
							.get(i);
					int dist = sim.getDistance().d[node.pid][successorId];
					if (dist < minDist && dist != 0)
					{
						minDist = dist;
						minDistPeerId = successorId;
					}
				}
				// System.out.println("minDistPeerId = " + minDistPeerId
				// + ", minDist = " + minDist);
				if (minDist == 999999)
					return null;

				successorList.add(new Node(fingerIndex, minDistPeerId));
			}
			fingerIndex = (fingerIndex + 1) % numSeg;
			if (fingerIndex == fingerEnd)
				break;
		}

		if (successorList.isEmpty())
			return null;

		int index = r.nextInt(successorList.size());
		node.set(successorList.get(index));
		return node;
	}
}
