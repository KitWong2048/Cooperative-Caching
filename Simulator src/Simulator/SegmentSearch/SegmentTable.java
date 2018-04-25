package Simulator.SegmentSearch;

import java.util.*;

import Simulator.*;

public class SegmentTable
{

	public class Row
	{
		private TreeMap<Integer, Integer> entry; // <distance, node id>

		Row()
		{
			entry = new TreeMap<Integer, Integer>();
		}

		public boolean insert(int maxRowSize, int dist, int pid)
		{
			int oldRowSize = entry.size();
			entry.put(dist, pid);
			int newRowSize = entry.size();
			if (newRowSize == oldRowSize)
				return false;
			if (newRowSize > maxRowSize)
			{
				int key = entry.lastKey();
				entry.remove(key);
			}
			return true;
		}

		public String toString()
		{
			String str = new String();
			Iterator<Map.Entry<Integer, Integer>> iter = entry.entrySet()
					.iterator();
			while (iter.hasNext())
			{
				int pid = iter.next().getValue();
				str += pid + " ";
			}
			return str;
		}

		public int getD1()
		{
			try
			{
				return entry.firstKey();
			}
			catch (NoSuchElementException e)
			{
				// e.printStackTrace();
				return -1;
			}
		}

		public boolean isEmpty()
		{
			return entry.isEmpty();
		}
		/*
		 * public Iterator<Map.Entry<Integer,Integer>> iterator() { return
		 * entry.entrySet().iterator(); }
		 */
	}

	/*
	 * public class RowIterator { Iterator<Map.Entry<Integer, Integer>>
	 * iterator; RowIterator(Iterator<Map.Entry<Integer, Integer>> iter) {
	 * iterator = iter; } public void set(Iterator<Map.Entry<Integer, Integer>>
	 * iter) { iterator = iter; } public boolean hasNext() { return
	 * iterator.hasNext(); } public Map.Entry<Integer, Integer> next() { return
	 * iterator.next(); } }
	 */

	Vector<Row> row;

	public SegmentTable(int numSeg)
	{
		row = new Vector<Row>();
		for (int i = 0; i < numSeg; i++)
		{
			row.add(new Row());
		}
	}

	// update table of peer pid
	// seg: segment bitmap of peer nid
	public void updateTable(Simulator sim, int pid, int nid, BitSet seg)
	{
		if (pid == nid)
			return;
		Distance dist = sim.getDistance();
		for (int s = 0; s < sim.getConfig().numSegment; s++)
		{
			if (seg.get(s))
			{
				row.get(s).insert(sim.getConfig().tableRowSize,
						dist.d[pid][nid], nid);
			}
		}
	}

	public void print()
	{
		for (int s = 0; s < row.size(); s++)
		{
			System.out.println("seg #" + s + "{" + row.get(s).toString() + "}");
		}
	}

	public Row getRow(int index)
	{
		return row.get(index);
	}

	public int numNonEmptyRow()
	{
		int num = 0;
		for (int s = 0; s < row.size(); s++)
		{
			if (!row.get(s).isEmpty())
				num++;
		}
		return num;
	}
}
