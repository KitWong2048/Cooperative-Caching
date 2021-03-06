package Simulator.CachingStrategy;

import java.util.Random;

import Simulator.*;
import Simulator.Peer.*;

public class RandomCaching implements ICachingStrategy
{
	public RandomCaching()
	{
		System.out.println("Random Caching");
	}

	/**
	 * upon joining the network, each peer cache segments 
	 * randomly until their cache size is fulfilled 
	 */
	public void join(Simulator sim, int pid)
	{
		PeerCollection pc = sim.getPeerCollection();
		Peer p = pc.get(pid);
		Random r = new Random();
		int num_segments = sim.getConfig().numSegment;
		while (p.residualCachingCapacity() > 0
				&& p.numCachedSegments() < num_segments)
		{
			int num = r.nextInt(num_segments);
			if (!p.cached(num))
			{
				p.cacheSegment(num);
			}
		}
	}

	public void cache(Simulator sim)
	{
		// nothing
	}
}
