package Simulator.CachingStrategy;

import Simulator.Simulator;
import Simulator.Config;
import Simulator.Peer.Peer;
import Simulator.Peer.PeerCollection;
import Simulator.Utility.RandomGenerator;

public class SlidingWindowCaching implements ICachingStrategy
{
	public SlidingWindowCaching()
	{
		System.out.println("Sliding Window Caching");
	}

	/**
	 * caching upon joining the system
	 */
	public void join(Simulator sim, int pid)
	{
		PeerCollection pc = sim.getPeerCollection();
		Peer p = pc.get(pid);
		double[] popularity = sim.getPopularity();
		int num_segments = sim.getConfig().numSegment;
		
		// generate a random sliding window ending index
		RandomGenerator rg = new RandomGenerator(popularity, num_segments);
		int index = rg.generate();
		while (p.residualCachingCapacity() > 0
				&& p.numCachedSegments() < num_segments)
		{
			p.cacheSegment(index);
			index--;
			if (index < 0)
			{
				// wrap up from the back
				index += num_segments;
			}
		}
	}

	/**
	 * this scheme does not update the cache
	 */
	public void cache(Simulator sim)
	{
		// nothing
	}
}
