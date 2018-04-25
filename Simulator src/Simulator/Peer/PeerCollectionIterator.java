package Simulator.Peer;

import java.util.Iterator;
import java.util.Map;

public class PeerCollectionIterator
{
	private Iterator<Map.Entry<Integer, Peer>> iterator;

	public PeerCollectionIterator(Iterator<Map.Entry<Integer, Peer>> iter)
	{
		iterator = iter;
	}

	public void set(Iterator<Map.Entry<Integer, Peer>> iter)
	{
		iterator = iter;
	}

	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	public Peer next()
	{
		return iterator.next().getValue();
	}
}
