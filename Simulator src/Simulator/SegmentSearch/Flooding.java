package Simulator.SegmentSearch;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Random;

import Simulator.Config;
import Simulator.Simulator;
import Simulator.Distance;
import Simulator.Peer.Peer;
import Simulator.Peer.PeerCollection;
import Simulator.Peer.PeerCollectionIterator;

public class Flooding implements ISegmentSearch
{

	private boolean[][] randomNeighbor; // random neighbor relationship
	private int[] numRandomNeighbor;
	private int topologySize;

	private static final int MIN_NBRS = 4;
	private static final int MAX_NBRS = 30;
	private static final int NUM_ADAPTATION = 10;

	public SearchResult search(Simulator sim)
	{
		System.out.println("Flooding::search()");

		// 1. find neighbor
		Config config = sim.getConfig();
		topologySize = config.topologySize;
		randomNeighbor = new boolean[topologySize][topologySize];
		numRandomNeighbor = new int[topologySize];
		for (int i = 0; i < topologySize; i++)
			numRandomNeighbor[i] = 0;

		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		System.out.println("find neighbors");

		while (iter.hasNext())
		{
			Peer peer = iter.next();
			findRandomNeighbor(sim, peer.pid);
		}

		/*
		 * iter.set(pc.iterator()); while (iter.hasNext()) { Peer peer =
		 * iter.next(); int num = numRandomNeighbor[peer.pid];
		 * System.out.println("pid=" + peer.pid + ", #neighbors=" + num); }
		 */

		for (int i = 0; i < NUM_ADAPTATION; i++)
			adaptation(sim);

		iter.set(pc.iterator());
		while (iter.hasNext())
		{
			Peer peer = iter.next();
			int num = numRandomNeighbor[peer.pid];
			System.out.println("pid=" + peer.pid + ", #neighbors=" + num
					+ ",cap=" + peer.cachingCapacity);
		}

		// 2. flood
		double dist = 0;
		double latency = 0;
		double avgHitRate = 0;
		double[] popularity = sim.getPopularity();
		System.out.println("flood");
		iter.set(pc.iterator());
		while (iter.hasNext())
		{
			Peer peer = iter.next();
			// double hitRate = 0;
			for (int s = 0; s < config.numSegment; s++)
			{
				if (!peer.cached(s))
				{
					FloodList floodList = getFloodList(sim, peer.pid);

					floodList = pruneFloodList(sim, floodList, s);

					if (floodList.size() > 0)
					{
						avgHitRate += popularity[s];
						dist += popularity[s]
								* computeSegmentDistance(sim, peer.pid,
										floodList);
						latency += popularity[s]
								* computeSearchLatency(sim, peer.pid, floodList);
					}
					else
					{
						dist += popularity[s]
								* sim.getDistance().d[peer.pid][sim
										.getServerID()];
						latency += popularity[s]
								* sim.getDistance().d[peer.pid][sim
										.getServerID()] * config.floodingTTL;
					}
				}
				else
				{
					avgHitRate += popularity[s];
				}
			}
			// avgHitRate += hitRate / config.numSegment;

			// System.out.println("Flooding pid=" + peer.pid);
		}

		dist /= pc.size();
		latency /= pc.size();
		avgHitRate /= pc.size();

		SearchResult sr = new SearchResult();
		sr.segmentDistance = dist;
		sr.latency = latency;
		sr.hitRate = avgHitRate;
		return sr;
	}

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

	public boolean unsetRandomNeighbor(int pid, int nid)
	{
		if (!randomNeighbor[pid][nid] || pid == nid)
			return false;
		randomNeighbor[pid][nid] = false;
		randomNeighbor[nid][pid] = false;
		numRandomNeighbor[pid]--;
		numRandomNeighbor[nid]--;
		return true;
	}

	public class FloodList
	{
		public Vector<Integer> list; // list of peers to be flooded
		public int[] pred; // predecessor list

		public FloodList(int topologySize, int pid)
		{
			list = new Vector<Integer>();
			pred = new int[topologySize];
			pred[pid] = pid;
		}

		public void add(int pid, int pred)
		{
			list.add(pid);
			this.pred[pid] = pred;
		}

		public Integer get(int index)
		{
			return list.get(index);
		}

		public Integer remove(int index)
		{
			return list.remove(index);
		}

		public int size()
		{
			return list.size();
		}
	}

	// pid will flood the returned node list
	public FloodList getFloodList(Simulator sim, int pid)
	{

		/*
		 * int level = sim.getConfig().floodingTTL;
		 * 
		 * // BFS with limited level // visited = -1 if not visited, n if
		 * visited, where n is number of levels int [] visited = new
		 * int[topologySize]; for (int i=0; i<topologySize; i++) visited[i] =
		 * -1;
		 * 
		 * visited[pid] = 0; Vector<Integer> queue = new Vector<Integer>();
		 * queue.add(pid); FloodList list = new
		 * FloodList(sim.getConfig().topologySize, pid); while (queue.size() >
		 * 0) { int item = queue.remove(0); if (visited[item] < level) { for
		 * (int i=0; i<topologySize; i++) { if (randomNeighbor[item][i] &&
		 * visited[i] == -1) { queue.add(i); visited[i] = visited[item] + 1;
		 * list.add(i, item); } } } } return list;
		 */

		// Biased random walk with limited level
		// visited = 0 if not visited, 1 if visited

		int[] visited = new int[topologySize];
		for (int i = 0; i < topologySize; i++)
			visited[i] = 0;

		FloodList list = new FloodList(sim.getConfig().topologySize, pid);
		randomWalk(sim, list, pid, visited, -1);
		return list;
	}

	void randomWalk(Simulator sim, FloodList list, int pid, int[] visited,
			int count)
	{
		visited[pid] = 1;
		count++;
		if (count > sim.getConfig().floodingTTL)
			return;
		if (numRandomNeighbor[pid] == 0)
		{
			System.out.println("#neighbors = 0 ?");
			System.exit(1);
		}
		int maxDeg = 0;
		int maxDegIndex = 0;
		for (int i = 0; i < topologySize; i++)
		{
			if (randomNeighbor[pid][i] && visited[i] == 0
					&& numRandomNeighbor[i] > maxDeg)
			{
				maxDeg = numRandomNeighbor[i];
				maxDegIndex = i;
			}
		}
		if (maxDeg == 0)
		{
			return;
		}
		else
		{
			list.add(maxDegIndex, pid);
			randomWalk(sim, list, maxDegIndex, visited, count);
		}
	}

	public FloodList pruneFloodList(Simulator sim, FloodList list, int objectId)
	{
		PeerCollection pc = sim.getPeerCollection();
		for (int i = 0; i < list.size(); i++)
		{
			int pid = list.get(i);
			Peer peer = pc.get(pid);
			boolean ok = false; // objectId found
			if (peer.cached(objectId))
				ok = true;
			for (int j = 0; j < topologySize; j++)
			{ // one hop replication
				if (randomNeighbor[pid][j])
				{
					Peer nbr = pc.get(j);
					if (nbr.cached(objectId))
					{
						ok = true;
						break;
					}
				}
			}
			if (!ok)
			{
				list.remove(i);
				i--;
			}
			/*
			 * if (!peer.cached(objectId)) { list.remove(i); i--; }
			 */
		}
		/*
		 * for (int i=0; i<list.size(); i++) { int pid = list.get(i); Peer peer
		 * = pc.get(pid); if (!peer.cached(objectId)) {
		 * System.out.println("invalid prune, exit program!"); System.exit(1); }
		 * }
		 */
		// Random r = new Random();
		while (list.size() > sim.getConfig().tableRowSize)
		{
			// int index = r.nextInt(list.size());
			int index = list.size() - 1;
			list.remove(index);
		}
		return list;
	}

	public double computeSegmentDistance(Simulator sim, int pid, FloodList list)
	{
		double minDist = 9999;
		Distance distance = sim.getDistance();
		for (int i = 0; i < list.size(); i++)
		{
			if (distance.d[pid][list.get(i)] < minDist)
				minDist = distance.d[pid][list.get(i)];
		}
		return minDist;
	}

	public double computeSearchLatency(Simulator sim, int pid, FloodList list)
	{
		double maxLatency = -1;
		Distance distance = sim.getDistance();
		for (int i = 0; i < list.size(); i++)
		{
			double latency = 0;
			int cur = list.get(i);
			while (cur != pid)
			{
				int pred = list.pred[cur];
				latency += distance.d[cur][pred];
				cur = pred;
			}
			if (latency > maxLatency)
				maxLatency = latency;
		}
		return maxLatency;
	}

	public void adaptation(Simulator sim)
	{
		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		while (iter.hasNext())
		{
			Peer peer = iter.next();
			int self = peer.pid;
			if (satisfactionLevel(sim, self) < 1.0)
			{
				int added = 0;
				TreeSet<Integer> peerSet = new TreeSet<Integer>();
				while (added < 5)
				{
					int nid = pc.getRandomPeer(sim.getConfig().topologySize);
					if (nid != self && !randomNeighbor[self][nid])
					{
						peerSet.add(nid);
						added++;
					}
				}
				Iterator<Integer> it = peerSet.iterator();
				while (it.hasNext())
				{
					pickNeighborToDrop(sim, self, it.next());
				}
			}
		}
	}

	public void pickNeighborToDrop(Simulator sim, int xpid, int ypid)
	{
		PeerCollection pc = sim.getPeerCollection();
		if (numRandomNeighbor[xpid] + 1 <= MAX_NBRS)
		{
			setRandomNeighbor(xpid, ypid);
			return;
		}
		TreeSet<Integer> subset = new TreeSet<Integer>();
		int capY = pc.get(ypid).cachingCapacity;
		for (int i = 0; i < topologySize; i++)
		{
			if (randomNeighbor[xpid][i])
			{
				int capI = pc.get(i).cachingCapacity;
				if (capI <= capY)
					subset.add(i);
			}
		}
		if (subset.isEmpty())
			return;
		int zpid = subset.first();
		int maxDeg = numRandomNeighbor[zpid];
		for (int i = 1; i < subset.size(); i++)
		{
			if (numRandomNeighbor[i] > maxDeg)
			{
				maxDeg = numRandomNeighbor[i];
				zpid = i;
			}
		}
		int H = 1;
		if (numRandomNeighbor[zpid] > numRandomNeighbor[ypid] + H)
		{
			unsetRandomNeighbor(xpid, zpid);
			setRandomNeighbor(xpid, ypid);
		}
	}

	public double satisfactionLevel(Simulator sim, int pid)
	{
		if (numRandomNeighbor[pid] < MIN_NBRS)
			return 0.0;
		double total = 0.0;
		PeerCollection pc = sim.getPeerCollection();
		for (int i = 0; i < topologySize; i++)
		{
			if (randomNeighbor[pid][i])
			{
				double capI = pc.get(i).cachingCapacity;
				double numNeighborI = numRandomNeighbor[i];
				total += capI / numNeighborI;
			}
		}
		double capX = pc.get(pid).cachingCapacity;
		double S = total / capX;
		if (S > 1.0 || numRandomNeighbor[pid] > MAX_NBRS)
			S = 1.0;
		return S;
	}
}
