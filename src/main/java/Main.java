public class Main {
    public static void main(String[] args) {
        PercolationStats experiments = new PercolationStats(40, 2);
        System.out.println("Mean: " + experiments.mean());
        System.out.println("Deviation: " + experiments.stddev());
        System.out.println("Confidence Low: " + experiments.confidenceLo());
        System.out.println("Confidence High: " + experiments.confidenceHi());
    }
}
