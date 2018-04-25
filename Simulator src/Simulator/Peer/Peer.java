package Simulator.Peer;

import java.util.BitSet;

public class Peer
{
	public static final int BAD_PID = -1;
	public int pid;
	public int arrtime;
	public int lifetime;
	public int cachingCapacity;
	private BitSet segBitSet;

	public Peer(int pid, int arrtime, int lifetime, int cachingCapacity)
	{
		this.pid = pid;
		this.arrtime = arrtime;
		this.lifetime = lifetime;
		this.cachingCapacity = cachingCapacity;
		segBitSet = new BitSet();
	}

	public void cacheSegment(int sid)
	{
		segBitSet.set(sid);
	}

	public void removeSegment(int sid)
	{
		segBitSet.clear(sid);
	}

	public void replaceSegment(int oldSid, int newSid)
	{
		removeSegment(oldSid);
		cacheSegment(newSid);
	}

	public boolean cached(int sid)
	{
		return segBitSet.get(sid);
	}

	public int numCachedSegments()
	{
		return segBitSet.cardinality();
	}

	public int residualCachingCapacity()
	{
		return cachingCapacity - numCachedSegments();
	}

	public BitSet getSegBitSet()
	{
		return segBitSet;
	}

	public String getBitSetString()
	{
		return segBitSet.toString();
	}
}
