package Simulator.Peer;

import java.util.*;

public class PeerCollection
{
	private TreeMap<Integer, Peer> tm; // set of peers

	public PeerCollection(int topologySize)
	{
		tm = new TreeMap<Integer, Peer>();
	}

	public void put(Peer peer)
	{
		tm.put(peer.pid, peer);
	}

	public Peer get(int pid)
	{
		return tm.get(pid);
	}

	public Peer remove(int pid)
	{
		return tm.remove(pid);
	}

	public int size()
	{
		return tm.size();
	}

	public boolean exist(int pid)
	{
		if (this.get(pid) != null)
		{
			return true;
		}
		return false;
	}

	public Iterator<Map.Entry<Integer, Peer>> iterator()
	{
		return tm.entrySet().iterator();
	}

	public int getRandomPeer(int topologySize)
	{
		Random r = new Random();
		int n = r.nextInt(topologySize);
		while (!exist(n))
		{
			n = r.nextInt(topologySize);
		}
		return n;
	}
}
