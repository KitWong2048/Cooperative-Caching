package Simulator.CachingStrategy;

import Simulator.*;

public class CachingStrategyContext
{
	public static final int MY_CACHING = 1;
	public static final int CENTRALIZED_CACHING = 2;
	public static final int RANDOM_CACHING = 3;
	public static final int SLIDING_WINDOW = 4;

	private ICachingStrategy cachingStrategy;

	public CachingStrategyContext(ICachingStrategy strategy)
	{
		this.cachingStrategy = strategy;
	}

	public void join(Simulator sim, int pid)
	{
		cachingStrategy.join(sim, pid);
	}

	public void cache(Simulator sim)
	{
		cachingStrategy.cache(sim);
	}
}
