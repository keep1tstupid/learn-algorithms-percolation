import java.util.Random;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double VARIANCE_COEFF = 1.96;
    private final int expTrials;
    private final double[] results;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateValue(n);
        validateValue(trials);

        expTrials = trials;
        results = new double[expTrials];
        Random rand = new Random();

        for (int i = 0; i < expTrials; i++) {
            Percolation test = new Percolation(n);

            while (!test.percolates()) {
                test.open(rand.nextInt(n) + 1, rand.nextInt(n) + 1);
            }
            results[i] = test.numberOfOpenSites() / (double) (n * n);
        }
    }

    private void validateValue(int val) {
        if (val <= 0) {
            throw new IllegalArgumentException("Value " + val + "is invalid");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double variance = Math.sqrt(StdStats.var(results));
        return mean() - (variance * VARIANCE_COEFF / Math.sqrt(expTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double variance = Math.sqrt(StdStats.var(results));
        return mean() + (variance * VARIANCE_COEFF / Math.sqrt(expTrials));
    }
}