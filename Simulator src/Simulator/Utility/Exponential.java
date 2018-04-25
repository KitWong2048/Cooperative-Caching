package Simulator.Utility;

import java.util.Random;

public class Exponential
{
	private double lambda;

	public Exponential(double lambda)
	{
		this.lambda = lambda;
	}

	public int generate()
	{
		int num;
		double r = new Random().nextDouble();
		Double d = new Double(-1 * Math.log(r) / lambda);
		num = d.intValue();
		return num;
	}
}
