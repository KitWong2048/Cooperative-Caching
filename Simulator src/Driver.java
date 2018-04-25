import Simulator.*;
import Simulator.CachingStrategy.*;
import Simulator.CachingStrategy.CentralizedCaching.*;
import Simulator.Utility.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class Driver
{
	public static void main(String[] args)
	{

		// Convert brite topology (e.g. 3072.brite) to distance matrix file
		// (e.g. 3072.d). The distance matrix file is the input of the
		// simulation. You may run this for the first time. It may take
		// several minutes or up to hours to run the algorithm depending on
		// the size of the brite topology . After this, you can comment this
		// line and use the distance matrix file as the simulation input
		// directly.
		//readBrite();

		// Run a simulation by varying a certain parameters (e.g. number of
		// peers,
		// number of segments, etc.). You may need to change the code to choose
		// which parameter to be varied.
		//
		// Config file: config.txt
		//
		// Result output: result.txt (Please read SimulatorObserver to
		// understand
		// the format of result.txt)
		//
		// Log file: log.txt
		simulate();

		/*
		 * // Convert result to MatLab vectors. You may need to change the code
		 * to // correctly convert different results to MatLab format. // input:
		 * result.txt // output: result_parsed.txt
		 * 
		 * plot();
		 */

		// The following function calls are just for testing
		// just leave the code here
		/*
		 * //randomGeneratorTest(); //zipfTest(); //vecTest();
		 * //centralizedCachingTest(); //stringTest(); //test();
		 * //geometricTest();
		 */
	}

	// the main body of simulation logic
	public static void simulate()
	{
		try
		{
			// load the configuration for the test
			Config config = new Config();
			config.read(new String("config.txt"));
			Distance d = new Distance();
			d.readDistance(config.distanceFile, config.topologySize);
			PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true));
			Simulator s = new Simulator();

			// display the network topology setting
			/*
			 * double avgDelay = 0; for (int i=0; i<config.topologySize; i++) {
			 * double delay = 0; for (int j=0; j<config.topologySize; j++) { if
			 * (i != j) delay += d.d[i][j]; } delay /= config.topologySize - 1;
			 * avgDelay += delay; } avgDelay /= config.topologySize;
			 * System.out.println("avg delay = " + avgDelay);
			 */

			// setup parameters
			double[] lambda = new double[] { 0.0088, 0.0177, 0.0355, 0.0711,
					0.1427, 0.2844 };
			int[] numSeg = new int[] { 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
			int[] cachingScheme = new int[] { 1, 2, 3, 4 };
			int[] searchScheme = new int[] { 1, 2, 3, 4 };
			int[] normW = new int[] { 0, 5, 10, 15, 20, 25 };
			double[] epsilon = new double[] { 0.1, 0.001 };
			int[] numNeighbor = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10 };
			int[] exchangePeriod = new int[] { 20, 40, 60, 80, 100, 120 };
			double[] geometricParam = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5,
					0.6, 0.7, 0.8, 0.9 };
			int[] floodingTTL = new int[] { 10, 10, 10, 10, 10, 10, 10, 10 };

			pw.println(timeString() + ": begin simulation");

			// x-axis: number of peers
			// comparision: (different cachingScheme - MySearch)
			pw.println(timeString() + ": run simulation set 1");
			for (int i = 0; i < cachingScheme.length; i++)
			{
				for (int j = 0; j < lambda.length; j++)
				{
					pw.println(timeString() + ": " + i + "," + j);
					pw.flush();
					config.set(cachingScheme[i], 1, 3, 0.4, 10000, 3072,
							"3072.d", 128, lambda[j], 7200, 60, 4, 4, 5, 5,
							0.1, 10, floodingTTL[i]);
					s.run(config, d);
				}
			}

			/*
			 * // x-axis: number of segments // comparision: (different
			 * cachingScheme - MySearch)
			 * 
			 * pw.println(timeString() + ": run simulation set 2"); for (int
			 * i=0; i<cachingScheme.length; i++) { for (int j=0;
			 * j<numSeg.length; j++) { pw.println(timeString() + ": " + i + ","
			 * + j); pw.flush(); config.set(cachingScheme[i], 1, 3, 0.4, 10000,
			 * 3072, "3072.d", numSeg[j], 0.1427, 7200, 60, 4, 4, 5, 5, 0.001,
			 * 10, floodingTTL[i]); s.run(config, d); } }
			 */

			/*
			 * // x-axis: number of peers // comparision: (MyCaching - different
			 * search scheme) pw.println(timeString() +
			 * ": run simulation set 3"); for (int i=0; i<searchScheme.length;
			 * i++) { for (int j=0; j<lambda.length; j++) {
			 * pw.println(timeString() + ": " + i + "," + j); pw.flush();
			 * config.set(1,searchScheme[i], 3, 0.4, 10000, 3072, "3072.d", 128,
			 * lambda[j], 7200, 60, 4, 4, 5, 5, 0.001, 10, floodingTTL[i]);
			 * s.run(config, d); } }
			 */

			// x-axis: number of segments
			// comparision: (MyCaching - different search scheme)
			/*
			 * pw.println(timeString() + ": run simulation set 4"); for (int
			 * i=0; i<searchScheme.length; i++) { //int i=3; for (int j=0;
			 * j<numSeg.length; j++) { pw.println(timeString() + ": " + i + ","
			 * + j); pw.flush(); config.set(1, searchScheme[i], 3, 0.4, 10000,
			 * 3072, "3072.d", numSeg[j], 0.1427, 7200, 60, 4, 4, 5, 5, 0.001,
			 * 10, floodingTTL[i]); s.run(config, d); } }
			 */

			/*
			 * pw.println(timeString() + ": varying norm W"); for (int i=0;
			 * i<normW.length; i++) { config.set(1, 1, 3, 0.4, 10000, 3072,
			 * "3072.d", 128, 0.1427, 7200, 60, 4, 4, 5, 5, 0.001, normW[i], 5);
			 * s.run(config, d); }
			 * 
			 * pw.println(timeString() + ": varying epsilon"); for (int i=0;
			 * i<epsilon.length; i++) { config.set(1, 1, 3, 0.4, 10000, 3072,
			 * "3072.d", 128, 0.1427, 7200, 60, 4, 4, 5, 5, epsilon[i], 10, 5);
			 * s.run(config, d); }
			 */

			/*
			 * pw.println(timeString() + " varying #iterations"); pw.flush();
			 * config.set(1, 1, 3, 0.4, 10000, 3072, "3072.d", 128, 0.1427,
			 * 7200, 60, 4, 4, 5, 5, 0.001, 10, 4); s.run(config, d);
			 */

			/*
			 * pw.println(timeString() + ": varying num neighbor"); for (int
			 * i=0; i<numNeighbor.length; i++) { config.set(1, 1, 2, 1, 10000,
			 * 3072, "3072.d", 128, 0.1427, 7200, 60, 4, numNeighbor[i], 5, 5,
			 * 0.001, 10, 5); s.run(config, d); }
			 */

			/*
			 * pw.println(timeString() + ": varying exchange period"); for (int
			 * i=0; i<exchangePeriod.length; i++) { config.set(1, 1, 2, 1,
			 * 10000, 3072, "3072.d", 128, 0.1427, 7200, exchangePeriod[i], 4,
			 * 4, 5, 5, 0.001, 10, 5); s.run(config, d); }
			 */

			/*
			 * pw.println(timeString() +
			 * ": varying geometric capacity's parameter"); for (int i=0;
			 * i<geometricParam.length; i++) { config.set(1, 1, 3,
			 * geometricParam[i], 10000, 3072, "3072.d", 128, 0.1427, 7200, 60,
			 * 4, 4, 5, 5, 0.001, 10, 4); s.run(config, d); }
			 */

			pw.println(timeString() + ": finish simulation!");

			pw.close();
			System.out.println("Finish simulation!");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void plot()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("result.txt"));
			String line = new String();
			PrintWriter pw = new PrintWriter(
					new FileWriter("result_parsed.txt"));
			int discrepancyIndex = 19;
			int segmentDistanceIndex = 20;
			int searchLatencyIndex = 21;
			int hitRateIndex = 22;
			int numCachingScheme = 4;
			int numSearchScheme = 4;
			int numPeerPt = 6;
			int numSegPt = 8;

			// depending on the x-axis, change to numPeerPt or numSegPt
			// int numX = numSegPt;
			int numX = numPeerPt;

			double[][] discrepancy = new double[numCachingScheme][numX];
			double[][] segmentDistance = new double[numCachingScheme][numX];
			double[][] searchLatency = new double[numCachingScheme][numX];
			double[][] hitRate = new double[numCachingScheme][numX];

			DecimalFormat myformat = new DecimalFormat("0.00000000");

			// ------------
			// set 1
			// ------------

			// num peers
			for (int i = 0; i < numCachingScheme; i++)
			{
				for (int j = 0; j < numX; j++)
				{
					line = br.readLine();
					String[] splitResult = line.split(",");
					discrepancy[i][j] = Double
							.parseDouble(splitResult[discrepancyIndex]);
					segmentDistance[i][j] = Double
							.parseDouble(splitResult[segmentDistanceIndex]);
					searchLatency[i][j] = Double
							.parseDouble(splitResult[searchLatencyIndex]);
					hitRate[i][j] = Double
							.parseDouble(splitResult[hitRateIndex]);
				}
			}

			for (int i = 0; i < numCachingScheme; i++)
			{
				switch (i + 1)
				{
				case 1:
					pw.print("discrepancy_n_MyCaching = [");
					break;
				case 2:
					pw.print("discrepancy_n_CentralizedCaching = [");
					break;
				case 3:
					pw.print("discrepancy_n_RandomCaching = [");
					break;
				case 4:
					pw.print("dicrepancy_n_SlidingWindow = [");
					break;
				}

				for (int j = 0; j < numX; j++)
				{
					pw.print(myformat.format(discrepancy[i][j]) + " ");
				}
				pw.println("];");
			}
			/*
			 * for (int i=0; i<numCachingScheme; i++) { switch (i+1) { case 1:
			 * pw.print("segmentDistance_n_MyCaching = ["); break; case 2:
			 * pw.print("segmentDistance_n_CentralizedCaching = ["); break; case
			 * 3: pw.print("segmentDistance_n_RandomCaching = ["); break; case
			 * 4: pw.print("segmentDistance_n_SlidingWindow = ["); break; } for
			 * (int j=0; j<numX; j++) {
			 * pw.print(myformat.format(segmentDistance[i][j]) + " "); }
			 * pw.println("];"); }
			 * 
			 * 
			 * //----------- // Set 2 //-----------
			 * 
			 * for (int i=0; i<numCachingScheme; i++) { for (int j=0; j<numX;
			 * j++) { line = br.readLine(); String [] splitResult =
			 * line.split(","); discrepancy[i][j] =
			 * Double.parseDouble(splitResult[discrepancyIndex]);
			 * segmentDistance[i][j] =
			 * Double.parseDouble(splitResult[segmentDistanceIndex]);
			 * searchLatency[i][j] =
			 * Double.parseDouble(splitResult[searchLatencyIndex]);
			 * hitRate[i][j] = Double.parseDouble(splitResult[hitRateIndex]); }
			 * }
			 * 
			 * for (int i=0; i<numCachingScheme; i++) { switch (i+1) { case 1:
			 * pw.print("discrepancy_s_MyCaching = ["); break; case 2:
			 * pw.print("discrepancy_s_CentralizedCaching = ["); break; case 3:
			 * pw.print("discrepancy_s_RandomCaching = ["); break; case 4:
			 * pw.print("dicrepancy_s_SlidingWindow = ["); break; }
			 * 
			 * for (int j=0; j<numX; j++) {
			 * pw.print(myformat.format(discrepancy[i][j]) + " "); }
			 * pw.println("];"); }
			 * 
			 * for (int i=0; i<numCachingScheme; i++) { switch (i+1) { case 1:
			 * pw.print("segmentDistance_s_MyCaching = ["); break; case 2:
			 * pw.print("segmentDistance_s_CentralizedCaching = ["); break; case
			 * 3: pw.print("segmentDistance_s_RandomCaching = ["); break; case
			 * 4: pw.print("segmentDistance_s_SlidingWindow = ["); break; } for
			 * (int j=0; j<numX; j++) {
			 * pw.print(myformat.format(segmentDistance[i][j]) + " "); }
			 * pw.println("];"); }
			 * 
			 * 
			 * //---------- // Set 3 //----------
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { for (int j=0; j<numX;
			 * j++) { line = br.readLine(); String [] splitResult =
			 * line.split(","); discrepancy[i][j] =
			 * Double.parseDouble(splitResult[discrepancyIndex]);
			 * segmentDistance[i][j] =
			 * Double.parseDouble(splitResult[segmentDistanceIndex]);
			 * searchLatency[i][j] =
			 * Double.parseDouble(splitResult[searchLatencyIndex]);
			 * hitRate[i][j] = Double.parseDouble(splitResult[hitRateIndex]); }
			 * }
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { switch (i+1) { case 1:
			 * pw.print("segmentDistance_n_MySearch = ["); break; case 2:
			 * pw.print("segmentDistance_n_CentralizedSearch = ["); break; case
			 * 3: pw.print("segmentDistance_n_DHT = ["); break; case 4:
			 * pw.print("segmentDistance_n_Flooding = ["); break; } for (int
			 * j=0; j<numX; j++) {
			 * pw.print(myformat.format(segmentDistance[i][j]) + " "); }
			 * pw.println("];"); }
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { switch (i+1) { case 1:
			 * pw.print("searchLatency_n_MySearch = ["); break; case 2:
			 * pw.print("searchLatency_n_CentralizedSearch = ["); break; case 3:
			 * pw.print("searchLatency_n_DHT = ["); break; case 4:
			 * pw.print("searchLatency_n_Flooding = ["); break; } for (int j=0;
			 * j<numX; j++) { pw.print(myformat.format(searchLatency[i][j]) +
			 * " "); } pw.println("];"); } for (int i=0; i<numSearchScheme; i++)
			 * { switch (i+1) { case 1: pw.print("hitRate_n_MySearch = [");
			 * break; case 2: pw.print("hitRate_n_CentralizedSearch = [");
			 * break; case 3: pw.print("hitRate_n_DHT = ["); break; case 4:
			 * pw.print("hitRate_n_Flooding = ["); break; } for (int j=0;
			 * j<numX; j++) { pw.print(myformat.format(hitRate[i][j]) + " "); }
			 * pw.println("];"); }
			 */
			/*
			 * //---------- // Set 4 //----------
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { for (int j=0; j<numX;
			 * j++) { line = br.readLine(); String [] splitResult =
			 * line.split(","); discrepancy[i][j] =
			 * Double.parseDouble(splitResult[discrepancyIndex]);
			 * segmentDistance[i][j] =
			 * Double.parseDouble(splitResult[segmentDistanceIndex]);
			 * searchLatency[i][j] =
			 * Double.parseDouble(splitResult[searchLatencyIndex]);
			 * hitRate[i][j] = Double.parseDouble(splitResult[hitRateIndex]); }
			 * }
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { switch (i+1) { case 1:
			 * pw.print("segmentDistance_s_MySearch = ["); break; case 2:
			 * pw.print("segmentDistance_s_CentralizedSearch = ["); break; case
			 * 3: pw.print("segmentDistance_s_DHT = ["); break; case 4:
			 * pw.print("segmentDistance_s_Flooding = ["); break; } for (int
			 * j=0; j<numX; j++) {
			 * pw.print(myformat.format(segmentDistance[i][j]) + " "); }
			 * pw.println("];"); }
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { switch (i+1) { case 1:
			 * pw.print("searchLatency_s_MySearch = ["); break; case 2:
			 * pw.print("searchLatency_s_CentralizedSearch = ["); break; case 3:
			 * pw.print("searchLatency_s_DHT = ["); break; case 4:
			 * pw.print("searchLatency_s_Flooding = ["); break; } for (int j=0;
			 * j<numX; j++) { pw.print(myformat.format(searchLatency[i][j]) +
			 * " "); } pw.println("];"); }
			 * 
			 * for (int i=0; i<numSearchScheme; i++) { switch (i+1) { case 1:
			 * pw.print("hitRate_s_MySearch = ["); break; case 2:
			 * pw.print("hitRate_s_CentralizedSearch = ["); break; case 3:
			 * pw.print("hitRate_s_DHT = ["); break; case 4:
			 * pw.print("hitRate_s_Flooding = ["); break; } for (int j=0;
			 * j<numX; j++) { pw.print(myformat.format(hitRate[i][j]) + " "); }
			 * pw.println("];"); }
			 */
			br.close();
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String timeString()
	{
		Calendar currentTime = Calendar.getInstance();
		String timeStr = new String();
		timeStr = currentTime.get(Calendar.HOUR_OF_DAY) + ":";
		timeStr += currentTime.get(Calendar.MINUTE) + ":";
		timeStr += currentTime.get(Calendar.SECOND);
		return timeStr;
	}

	public static void readBrite()
	{
		Distance dist = new Distance();
		dist.readBrite("../3072.brite", "3072.d");
	}

	public static void randomGeneratorTest()
	{
		double[] p = new double[5];
		p[0] = 0.5;
		p[1] = 0.2;
		p[2] = 0.15;
		p[3] = 0.1;
		p[4] = 0.05;
		RandomGenerator rg = new RandomGenerator(p, 5);

		int[] count = new int[5];
		for (int i = 0; i < 5; i++)
			count[i] = 0;
		for (int i = 0; i < 10000; i++)
		{
			int num = rg.generate();
			count[num]++;
		}
		for (int i = 0; i < 5; i++)
		{
			System.out.println("#" + i + " = " + count[i]);
		}
	}

	public static void zipfTest()
	{
		Zipf zipf = new Zipf(10, 1);
		int[] count = new int[10];
		for (int i = 0; i < 10; i++)
			count[i] = 0;
		for (int i = 0; i < 10000; i++)
		{
			int num = zipf.generate();
			count[num]++;
		}
		double total = 0;
		for (int i = 0; i < 10; i++)
		{
			System.out.println("#" + i + " = " + count[i]);
			total += count[i];
		}
		double[] freq = new double[10];
		double sum = 0;
		for (int i = 0; i < 10; i++)
		{
			Double dCount = new Double(count[i]);
			freq[i] = dCount / total;
			sum += freq[i];
		}
		for (int i = 0; i < 10; i++)
		{
			System.out.println("freq[" + i + "] = " + freq[i]);
		}

		System.out.println("sum freq = " + sum);

	}

	public static void vecTest()
	{
		Vector<Integer> vec = new Vector<Integer>();
		vec.add(1);
		vec.add(2);
		vec.add(3);
		vec.add(4);
		vec.add(5);
		vec = changeVec(vec);
		for (int i = 0; i < vec.size(); i++)
		{
			System.out.print(vec.elementAt(i) + " ");
		}
	}

	public static Vector<Integer> changeVec(Vector<Integer> vec)
	{
		// vec.remove(new Integer(3));
		vec.remove(new Integer(3));
		vec.add(10);
		return vec;
	}

	public static void centralizedCachingTest()
	{
		// public Vector<OpPair> getAllOp(TreeSet<Integer> F, TreeSet<Integer>
		// S) {

		TreeSet<Integer> F = new TreeSet<Integer>();
		/*
		 * for (int i=0; i<1000; i++) { F.add(i); }
		 */
		F.add(10);
		F.add(8);
		F.add(4);
		F.add(6);
		F.add(2);
		TreeSet<Integer> S = new TreeSet<Integer>();
		S.add(8);
		S.add(4);
		/*
		 * for (int i=100; i<200; i++) { S.add(i); }
		 */

		CentralizedCaching cc = new CentralizedCaching();
		Vector<OpPair> vec = cc.getAllOp(F, S);

		for (int i = 0; i < vec.size(); i++)
		{
			System.out.println(vec.get(i).first + "," + vec.get(i).second);
		}

		cc.op(S, vec.get(4));

		System.out.println("S after op:");
		Iterator<Integer> SIter = S.iterator();
		while (SIter.hasNext())
		{
			System.out.println(SIter.next());
		}

		boolean ad = cc.admissible(100, 91);
		System.out.println(ad);

		// public double distanceToFacility(int pid, TreeSet<Integer> S,
		// Distance dist) {
		Config config = new Config();
		config.read(new String("config.txt"));

		Distance d = new Distance();
		d.readDistance(config.distanceFile, config.topologySize);

		S.add(22);
		S.add(14);
		S.add(20);
		System.out.println("S before dist to fac:");
		SIter = S.iterator();
		while (SIter.hasNext())
		{
			System.out.println(SIter.next());
		}
		double distToFac = cc.distanceToFacility(0, S, d);
		System.out.println("Distance to Fac: " + distToFac);

	}

	public static void stringTest()
	{
		String str = new String();
		int num = 21;
		DecimalFormat myformat = new DecimalFormat("0.0000");

		double dou = 1.238394202390492308490823940;
		String str1 = new String("hello");
		str += num + ",";
		str += myformat.format(dou) + ",";
		str += str1;

		System.out.println(str);
	}

	public static void test()
	{

		int numSeg = 16;
		int numBit = new Double(Math.log(numSeg) / Math.log(2)).intValue();
		// System.out.println("numBit = " + numBit);

		int sid = 14;

		// int objectId = 11;

		for (int objectId = 0; objectId < numSeg; objectId++)
		{

			int diff = (objectId - sid < 0) ? (objectId - sid + numSeg)
					: (objectId - sid);
			int k = new Double(Math.log(diff) / Math.log(2)).intValue() + 1;

			int fingerStart = new Double(sid + Math.pow(2, k - 1)).intValue()
					% numSeg;
			int fingerEnd = new Double(sid + Math.pow(2, k)).intValue()
					% numSeg;

			System.out.println("object = " + objectId + ", k = " + k
					+ ", int = [" + fingerStart + "," + fingerEnd + ")");

			int fingerIndex = fingerStart;
			while (true)
			{
				System.out.print(fingerIndex + " ");
				fingerIndex = (fingerIndex + 1) % numSeg;
				if (fingerIndex == fingerEnd)
					break;

			}
			System.out.println("");

		}
		/*
		 * double frac; int num = 3; int den = 2; frac = (double)num / den;
		 * System.out.println("frac=" + frac); int time = 3000; int arrtime =
		 * 2951; int uptime = time - arrtime; int exchangePeriod = 10; int level
		 * = uptime / exchangePeriod; System.out.println("level = " + level);
		 */
	}

	static void geometricTest()
	{
		Geometric geo = new Geometric(0.6);
		int[] trial = new int[10000];
		for (int i = 0; i < 10000; i++)
		{
			trial[i] = geo.generate();
			System.out.print(trial[i] + " ");
			if (i % 100 == 0)
				System.out.println("");
		}
		double mean = 0;
		for (int i = 0; i < 10000; i++)
			mean += trial[i] / 10000.0;
		System.out.println("mean = " + mean);

	}
}
