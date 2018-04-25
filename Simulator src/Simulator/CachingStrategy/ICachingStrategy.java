package Simulator.CachingStrategy;

import Simulator.*;

/**
 * Caching Strategy Interface
 * 
 * @author Philip
 * 
 */
public interface ICachingStrategy
{
	void join(Simulator sim, int pid);

	void cache(Simulator sim);
}
