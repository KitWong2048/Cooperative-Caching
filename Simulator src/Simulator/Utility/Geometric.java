package Simulator.Utility;

// P(k) = p^k * (1-p)      for 0<=p<1, k=0,1,2,...
public class Geometric
{
	private double parameter; // 0<=p<1
	private RandomGenerator generator;
	private static final int MAX_K = 100;

	public Geometric(double parameter)
	{
		this.parameter = parameter;
		double p[] = new double[MAX_K];
		for (int k = 0; k < MAX_K; k++)
		{
			p[k] = this.frequency(k);
		}
		generator = new RandomGenerator(p, MAX_K);
	}

	// k=0,1,2,...,MAX_K-1
	public double frequency(int k)
	{
		return Math.pow(parameter, k) * (1 - parameter);
	}

	// generate a number (in {0,1,..., MAX_K-1})
	// follows Geometric distribution
	public int generate()
	{
		return generator.generate();
	}
}
