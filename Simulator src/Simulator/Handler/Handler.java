package Simulator.Handler;

import Simulator.*;

public abstract class Handler
{
	// event types
	public static int LEAVE = 0;
	public static int JOIN = 1;

	protected Handler successorHandler;
	protected int eventType;

	public Handler setSuccessor(Handler successor)
	{
		successorHandler = successor;
		return this;
	}

	// just a loop
	public void handle(int event, Simulator sim, int pid)
	{
		Handler h = this;
		while (h.eventType != event)
		{
			h = h.successorHandler;
		}
		if (h != null)
		{
			h.handle(sim, pid);
		}
	}

	abstract protected void handle(Simulator sim, int pid);
}
