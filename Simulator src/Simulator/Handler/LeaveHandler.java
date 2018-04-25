package Simulator.Handler;

import Simulator.Simulator;
import Simulator.Peer.PeerCollection;

public class LeaveHandler extends Handler
{
	public LeaveHandler()
	{
		this.eventType = LEAVE;
	}

	/**
	 * handle leaving event with peer specified by pid
	 */
	protected void handle(Simulator sim, int pid)
	{
		// System.out.println("LeaveHandler");
		PeerCollection pc = sim.getPeerCollection();
		pc.remove(pid);
	}
}
