package Simulator.SegmentSearch;

import java.util.*;

import Simulator.Simulator;
import Simulator.Config;
import Simulator.Peer.*;
import Simulator.SegmentSearch.SearchResult;

public class MySegmentSearch implements ISegmentSearch
{

	// private boolean [][] closeNeighbor; // close neighbor relationship
	private boolean[][] randomNeighbor; // random neighbor relationship
	private int[] numRandomNeighbor;
	private int topologySize;
	private TreeMap<Integer, SegmentTable> table; // <peer id, segment table>

	// int observee;
	public SearchResult search(Simulator sim)
	{
		System.out.println("MySegmentSearch::search() begin");

		Config config = sim.getConfig();
		topologySize = config.topologySize;
		// closeNeighbor = new boolean[topologySize][topologySize];
		randomNeighbor = new boolean[topologySize][topologySize];
		numRandomNeighbor = new int[topologySize];
		for (int i = 0; i < topologySize; i++)
			numRandomNeighbor[i] = 0;
		table = new TreeMap<Integer, SegmentTable>();

		// Steps
		// 1. find neighbor
		// 2. advertise table
		// 3. search

		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		System.out.println("find neighbors");

		while (iter.hasNext())
		{

			Peer peer = iter.next();
			/*
			 * if (observee == 0) observee = peer.pid;
			 */
			table.put(peer.pid, new SegmentTable(config.numSegment));
			findRandomNeighbor(sim, peer.pid);
		}

		iter.set(pc.iterator());
		while (iter.hasNext())
		{
			Peer peer = iter.next();
			int num = numRandomNeighbor[peer.pid];
			System.out.println("pid=" + peer.pid + ", #neighbors=" + num);
		}

		System.out.println("exchange table");
		iter.set(pc.iterator());
		// int count = 0;

		while (iter.hasNext())
		{
			Peer peer = iter.next();
			Vector<Integer> adList = getAdvertiseList(sim, peer.pid);

			/*
			 * if (peer.pid == observee) {
			 * System.out.print("advertise list of peer pid=" + peer.pid);
			 * System.out.print("{"); for (int i=0; i<adList.size(); i++)
			 * System.out.print(adList.get(i) + " "); System.out.println("}"); }
			 */

			// System.out.print(adList.size() + " ");
			advertise(sim, peer.pid, adList);
			// count++;
			// System.out.println(count + " ");
			System.out.println("exchange pid=" + peer.pid);
		}
		// System.out.println("");

		/*
		 * System.out.println("Segment Table for peer pid=" + observee);
		 * table.get(observee).print();
		 */

		System.out.println("MySegmentSearch::search() finish");

		iter.set(pc.iterator());
		double[] popularity = sim.getPopularity();
		double dist = 0;
		double latency = 0;
		double hitRate = 0;
		double[] hitRateDistribution = new double[config.numSegment];
		for (int i = 0; i < config.numSegment; i++)
			hitRateDistribution[i] = 0;
		while (iter.hasNext())
		{
			Peer p = iter.next();
			for (int s = 0; s < config.numSegment; s++)
			{
				double d1 = table.get(p.pid).getRow(s).getD1();
				if (p.cached(s))
				{
					d1 = 0;
				}
				else if (d1 == -1)
				{
					d1 = sim.getDistance().d[p.pid][sim.getServerID()];
					latency += popularity[s]
							* sim.getDistance().d[p.pid][sim.getServerID()];
				}
				dist += popularity[s] * d1;
			}

			// hitRate += (double) table.get(p.pid).numNonEmptyRow() /
			// config.numSegment;

			for (int i = 0; i < config.numSegment; i++)
			{
				if (!table.get(p.pid).getRow(i).isEmpty())
				{
					hitRate += popularity[i];
				}
				else
				{
					hitRateDistribution[i]++;
				}
			}
		}
		/*
		 * System.out.println("**************"); System.out.println("#peers = "
		 * + pc.size()); System.out.println("miss count:"); for (int i=0;
		 * i<config.numSegment; i++)
		 * System.out.println(hitRateDistribution[i]+","+popularity[i]);
		 * System.out.println("**************");
		 */
		dist /= pc.size();
		latency /= pc.size();
		hitRate /= pc.size();

		SearchResult sr = new SearchResult();
		sr.segmentDistance = dist;
		sr.latency = latency;
		sr.hitRate = hitRate;

		return sr;
	}

	// ------------

	public void findRandomNeighbor(Simulator sim, int pid)
	{
		PeerCollection pc = sim.getPeerCollection();
		int count = 0;
		while (numRandomNeighbor[pid] < sim.getConfig().numRandomNeighbor)
		{
			int nid = pc.getRandomPeer(sim.getConfig().topologySize);
			if (numRandomNeighbor[nid] < sim.getConfig().numRandomNeighbor)
				setRandomNeighbor(pid, nid);
			count++;
			if (count > sim.getConfig().numRandomNeighbor * 3)
				break;
		}
	}

	/*
	 * // pid: peer ID of leaving peer public void
	 * removeRandomNeighbor(Simulator sim, int pid) { for (int i=0;
	 * i<sim.getConfig().topologySize; i++) { if (randomNeighbor[pid][i]) {
	 * resetRandomNeighbor(pid,i); } } }
	 */

	public boolean setRandomNeighbor(int pid, int nid)
	{
		if (randomNeighbor[pid][nid] || pid == nid)
			return false;
		randomNeighbor[pid][nid] = true;
		randomNeighbor[nid][pid] = true;
		numRandomNeighbor[pid]++;
		numRandomNeighbor[nid]++;
		return true;
	}

	/*
	 * public void resetRandomNeighbor(int pid, int nid) {
	 * randomNeighbor[pid][nid] = false; randomNeighbor[nid][pid] = false; }
	 */

	/*
	 * public int numRandomNeighbor(int pid) { int count = 0; for (int i=0;
	 * i<topologySize; i++) { if (randomNeighbor[pid][i]) { count++; } } return
	 * count; }
	 */

	// pid will advertise segment bitset to the returned list
	public Vector<Integer> getAdvertiseList(Simulator sim, int pid)
	{
		PeerCollection pc = sim.getPeerCollection();
		Peer peer = pc.get(pid);
		int uptime = sim.getTime() - peer.arrtime;
		int level = uptime / sim.getConfig().exchangePeriod;
		// if (level > 15)
		// level = 15;

		/*
		 * if (observee == pid) System.out.println("getAdvertiseList pid=" + pid
		 * + ", time=" + sim.getTime() + ", arrtime=" + peer.arrtime +
		 * ", uptime=" + uptime + ", level=" + level);
		 */

		// BFS with limited level
		// visited = -1 if not visited, n if visited, where n is number of
		// levels
		int[] visited = new int[topologySize];
		for (int i = 0; i < topologySize; i++)
			visited[i] = -1;

		/*
		 * if (pid==observee) { System.out.print("visited before={"); for (int
		 * i=0; i<topologySize; i++) System.out.print(visited[i] + " ");
		 * System.out.println("}"); }
		 */

		visited[pid] = 0;
		Vector<Integer> queue = new Vector<Integer>();
		queue.add(pid);

		while (queue.size() > 0)
		{
			int item = queue.remove(0);
			/*
			 * if (pid == observee) System.out.println("pop " + item);
			 */
			if (visited[item] < level)
			{
				for (int i = 0; i < topologySize; i++)
				{
					if (randomNeighbor[item][i] && visited[i] == -1)
					{
						queue.add(i);
						visited[i] = visited[item] + 1;
					}
				}
			}
		}

		/*
		 * if (pid==observee) { System.out.print("visited after={"); for (int
		 * i=0; i<topologySize; i++) System.out.print(visited[i] + " ");
		 * System.out.println("}"); }
		 */

		Vector<Integer> adList = new Vector<Integer>();
		for (int i = 0; i < topologySize; i++)
			if (visited[i] > 0)
				adList.add(i);
		return adList;
	}

	// advertise pid's segment bitset to nid
	public void advertise(Simulator sim, int pid, Vector<Integer> adList)
	{
		Peer peer = sim.getPeerCollection().get(pid);
		for (int i = 0; i < adList.size(); i++)
		{
			int nid = adList.get(i);
			table.get(nid).updateTable(sim, nid, pid, peer.getSegBitSet());
		}
	}

	// --------------

	/*
	 * public Vector<Integer> getCloseNeighborList(int pid) { Vector<Integer>
	 * list = new Vector<Integer>(); for (int i=0; i<topologySize; i++) { if
	 * (closeNeighbor[pid][i]) { list.add(i); } } return list; }
	 * 
	 * 
	 * 
	 * public int numCloseNeighbor(int pid) { int count = 0; for (int i=0;
	 * i<topologySize; i++) { if (closeNeighbor[pid][i]) { count++; } } return
	 * count; }
	 * 
	 * public int getClosePeer(int pid) {
	 * 
	 * 
	 * /////////
	 * 
	 * 
	 * return 0; }
	 * 
	 * public boolean addCloseNeighbor(int pid, int nid, Distance d, Config
	 * config) { if (closeNeighbor[pid][nid] || pid == nid) { return false; }
	 * int maxnid = -1; int maxD = -1; int numNeigh = 0; for (int i=0;
	 * i<topologySize; i++) { if (closeNeighbor[pid][i]) { numNeigh++; if
	 * (d.d[pid][i] > maxD) { maxD = d.d[pid][i]; maxnid = i; } } } if (numNeigh
	 * >= config.numCloseNeighbor) { removeCloseNeighbor(pid, maxnid); }
	 * closeNeighbor[pid][nid] = true; closeNeighbor[nid][pid] = true; return
	 * true; }
	 * 
	 * public void removeCloseNeighbor(int pid, int nid) {
	 * closeNeighbor[pid][nid] = false; closeNeighbor[nid][pid] = false; }
	 * 
	 * // pid: peer ID of leaving peer public void removeCloseNeighbor(int pid)
	 * { for (int i=0; i<topologySize; i++) { if (closeNeighbor[pid][i]) {
	 * removeCloseNeighbor(pid,i); } } }
	 */

}
