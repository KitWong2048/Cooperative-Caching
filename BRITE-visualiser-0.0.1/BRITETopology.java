import java.util.StringTokenizer;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * Creates a 2D representation of a BRITE topology.
 *
 * @author Steven Daniel Webb - steven.webb@postgrad.curtin.edu.au
 * @version 0.1 - initial version.
 */
public class BRITETopology extends Frame implements WindowListener
{
	/**
	 * The number of nodes to be represented by veritices.
	 */
	private int numNodes = 0;

	/**
	 * The number of links to be represented by edges.
	 */
	private int numEdges = 0;

	/**
	 * Array of nodes.
	 */
	private Node nodes[] = null;

	/**
	 * Array of eges.
	 */
	private Edge edges[] = null;

	/**
	 * The canvas to draw on.
	 */
	private World world;


	/**
	 * Main method to run the visualization tool.
	 *
	 * @param args The file name to open.
	 */
	public static void main(String args[])
	{
		if (args.length != 1)
		{
			System.out.println("Error! Incorrect number of command line arguments.");
			usage();
		}
		else if ((args[0].equals("-help")) || (args[0].equals("--help")))
		{
			usage();
		}
		else
		{
			try
			{
				//try to open the file.
				BufferedReader file = new BufferedReader(new FileReader(args[0]));
				//build the visualization
				BRITETopology t = new BRITETopology(file);
				//close the file
				file.close();
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Error! could not open file: " + args[0]);
				System.out.println(e);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Displays a usage message.
	 */
	public static void usage()
	{
		System.out.println("Usage:");
		System.out.println("	java Main <BRITE topology>");
	}

	public BRITETopology(BufferedReader file) throws IOException
	{
		//discard the first 4 linel
		for (int i=0; i<4; i++)
		{
			file.readLine();
		}

		//get the number of nodes
		StringTokenizer t = new StringTokenizer(file.readLine(), "()");
		t.nextToken();
		numNodes = Integer.parseInt(t.nextToken());
		System.out.println("numNodes: " + numNodes);
		nodes = new Node[numNodes];

		//for the number of nodes
		for (int i=0; i<nodes.length; i++)
		{
			//construct each node
			nodes[i] = new Node(file.readLine());
		}

		//discard the blank line
		file.readLine();

		//get the number of edges
		t = new StringTokenizer(file.readLine(), "()");
		t.nextToken();
		numEdges= Integer.parseInt(t.nextToken());
		System.out.println("numEdges: " + numEdges);
		edges = new Edge[numEdges];

		//for the number of edges
		for (int i=0; i<edges.length; i++)
		{
			//construct each edge
			edges[i] = new Edge(file.readLine());
		}

		//construct the canvas to draw on.
		//TODO, should read world size from file
		world = new World(1000, 1000, nodes);
		setLayout(new BorderLayout());
		setSize(new Dimension(1000, 1000));
		add(world);
		addWindowListener(this);
		show();
	}

	/**
	 * Method required for the window listener.
	 */
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	/**
	 * The world canvas to draw onto.
	 */
	private class World extends Canvas
	{
		/**
		 * Image that is used for double buffering.
		 */
		private final BufferedImage bi;
		
		/**
		 * The nodes to draw.
		 */
		private Node nodes[] = null;

		/**
		 * The width of the canvas.
		 */
		private int width = 0;
		
		/**
		 * The height of the canvas.
		 */
		private int height = 0;
		
		/**
		 * Default constructor.
		 *
		 * @param width The width of the canvas.
		 * @param height The height of the canvas.
		 * @param nodes The nodes.
		 */
		public World(final int width,
					 final int height,
					 final Node nodes[])
		{
			this.width = width;
			this.height = height;
			this.nodes = nodes;
			
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffer();
		}
		
		/**
		 * Re-paints the buffer.
		 */
		public void buffer()
		{
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.white);
			g.clearRect(0, 0, width, height);

			//draw the edges
			for (int i = 0;i < edges.length;i++)
			{
				edges[i].paint(g, nodes);
			}
			
			//draw the nodes
			for (int i = 0;i < nodes.length;i++)
			{
				nodes[i].paint(g);
			}
			
			g.dispose();
			super.repaint();
		}
		
		/**
		 * Paint method to re-draw the image.
		 *
		 * @param g The graphics object to draw with.
		 */
		public void paint(Graphics g)
		{
			g.drawImage(bi, 0, 0, this);
		}
	}
}
