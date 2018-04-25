import java.util.StringTokenizer;
import java.awt.*;

/**
 * Representation of an edge from a BRITE topology.
 */
public class Edge
{
	/**
	 * The source node id.
	 */
	private int sourceID = -1;

	/**
	 * The destination node ID.
	 */
	private int destinationID = -1;

	/**
	 * Does this link connect between ASs?
	 */
	private boolean connector = false;

	/**
	 * Default constructor
	 */
	public Edge(final String str)
	{
		StringTokenizer t = new StringTokenizer(str);
		//discard the ID.
		t.nextToken();
		sourceID = Integer.parseInt(t.nextToken());
		destinationID = Integer.parseInt(t.nextToken());
		t.nextToken();
		t.nextToken();
		t.nextToken();
		int from = Integer.parseInt(t.nextToken());
		int to = Integer.parseInt(t.nextToken());
		connector = !(from == to);
	}

	/**
	 * Draw the edge
	 *
	 * @param g Graphics object.
	 * @param nodes The nodes to draw from and to.
	 */
	public void paint(Graphics g, final Node nodes[])
	{
		if (connector)
		{
			g.setColor(Color.red);
		}
		else
		{
			g.setColor(Color.black);
		}
		g.drawLine((int)nodes[sourceID].getX(),
				   (int)nodes[sourceID].getY(),
				   (int)nodes[destinationID].getX(),
				   (int)nodes[destinationID].getY());
	}
}
