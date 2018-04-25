package Simulator.Utility;

import java.util.Random;

/**
 * generate a series of random numbers based on
 * my biased coin :)
 * 
 * @author Philip
 *
 */
public class RandomGenerator
{
	double[] p;
	int length;

	/**
	 * store a series of random numbers  
	 * 
	 * @param p			the random number series
	 * @param length	number of elements in the series
	 */
	public RandomGenerator(double[] p, int length)
	{
		set(p, length);
	}

	/**
	 * store two "random" numbers: bias, 1-bias
	 * 
	 * @param bias	value of the first number
	 */
	public RandomGenerator(double bias)
	{
		set(bias);
	}

	public void set(double[] p, int length)
	{
		this.p = new double[length];
		for (int i = 0; i < length; i++)
		{
			this.p[i] = p[i];
		}
		this.length = length;
	}

	public void set(double bias)
	{
		p = new double[2];
		p[0] = 1 - bias;
		p[1] = bias;
		length = 2;
	}

	/**
	 * select an index based on the distribution
	 * 
	 * @return	the index corresponding to the distribution
	 */
	public int generate()
	{
		double r = new Random().nextDouble();
		int i;
		for (i = 0; i < length; i++)
		{
			r -= p[i];
			if (r <= 0)
			{
				break;
			}
		}
		return i;
	}
}
