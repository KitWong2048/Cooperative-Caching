package Simulator.Utility;

public class Zipf
{
	private int population;
	private int parameter;
	private RandomGenerator generator;

	public Zipf(int population, int parameter)
	{
		this.population = population;
		this.parameter = parameter;
		double p[] = new double[population];
		for (int rank = 1; rank <= population; rank++)
		{
			p[rank - 1] = this.frequency(rank);
		}
		generator = new RandomGenerator(p, population);
	}

	public double frequency(int rank)
	{
		double numerator = 1 / Math.pow(rank, parameter);
		double harmonic = 0;
		for (int n = 1; n <= population; n++)
		{
			harmonic += 1 / Math.pow(n, parameter);
		}
		return numerator / harmonic;
	}

	// generate a number (in {0,1,..., population-1})
	// follows Zipf distribution
	public int generate()
	{
		return generator.generate();
	}
}
