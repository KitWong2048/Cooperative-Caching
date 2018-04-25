package Simulator.Handler;

import Simulator.Peer.Peer;
import Simulator.Peer.PeerCollection;
import Simulator.Utility.*;
import Simulator.*;

public class JoinHandler extends Handler
{
	public JoinHandler()
	{
		this.eventType = JOIN;
	}

	/**
	 * handles the joint event of a peer specified by pid
	 */
	protected void handle(Simulator sim, int pid)
	{
		// System.out.println("JoinHandler");
		PeerCollection pc = sim.getPeerCollection();
		Config config = sim.getConfig();
		double lambda = 1 / config.tau;
		int lifetime = new Exponential(lambda).generate();

		int cap = 1;
		if (config.cachingCapacityScheme == Config.ZIPF_CAPACITY)
		{
			int population = new Double(config.cachingCapacity).intValue();
			Zipf zipf = new Zipf(population, 1);
			cap = zipf.generate() + 1;
		}
		else if (config.cachingCapacityScheme == Config.UNIFORM_CAPACITY)
		{
			cap = new Double(config.cachingCapacity).intValue();
		}
		else if (config.cachingCapacityScheme == Config.GEOMETRIC_CAPACITY)
		{
			Geometric geo = new Geometric(config.cachingCapacity);
			cap = geo.generate();
		}
		else
		{
			System.out.println("invalid cachingCapacityScheme, exit program!");
			System.exit(1);
		}

		Peer p = new Peer(pid, sim.getTime(), lifetime, cap);
		pc.put(p);
		sim.getCachingStrategyContext().join(sim, pid);
		// System.out.println("JoinHandler end");

	}
}
