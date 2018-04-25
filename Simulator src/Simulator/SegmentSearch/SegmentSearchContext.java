package Simulator.SegmentSearch;

import Simulator.Simulator;

public class SegmentSearchContext
{
	public static final int MY_SEARCH = 1;
	public static final int CENTRALIZED_SEARCH = 2;
	public static final int DHT = 3;
	public static final int FLOODING = 4;

	private ISegmentSearch searchStrategy;

	public SegmentSearchContext(ISegmentSearch strategy)
	{
		this.searchStrategy = strategy;
	}

	public SearchResult search(Simulator sim)
	{
		return searchStrategy.search(sim);
	}
}