package Simulator.SegmentSearch;

import Simulator.Config;
import Simulator.Simulator;
import Simulator.Peer.Peer;
import Simulator.Peer.PeerCollection;
import Simulator.Peer.PeerCollectionIterator;

public class CentralizedSegmentSearch implements ISegmentSearch
{
	public SearchResult search(Simulator sim)
	{
		System.out.println("CentralizedSegmentSearch::search()");
		PeerCollection pc = sim.getPeerCollection();
		PeerCollectionIterator iter = new PeerCollectionIterator(pc.iterator());
		Config config = sim.getConfig();
		double[] popularity = sim.getPopularity();
		double dist = 0;
		double latency = 0;
		while (iter.hasNext())
		{
			Peer p = iter.next();
			for (int s = 0; s < config.numSegment; s++)
			{
				double d1 = sim.getD1(p.pid, s);
				if (p.cached(s))
					d1 = 0;
				if (d1 == Simulator.BAD_D)
					d1 = sim.getDistance().d[p.pid][sim.getServerID()];
				dist += popularity[s] * d1;
				latency += popularity[s]
						* sim.getDistance().d[p.pid][sim.getServerID()] * 2;
			}
		}
		dist /= pc.size();
		latency /= pc.size();

		SearchResult sr = new SearchResult();

		sr.segmentDistance = dist;
		sr.hitRate = 1;
		sr.latency = latency;

		return sr;
	}

}
