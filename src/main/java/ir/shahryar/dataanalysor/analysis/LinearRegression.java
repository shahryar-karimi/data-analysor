package ir.shahryar.dataanalysor.analysis;

import java.util.Arrays;

public class LinearRegression implements Analyzer{
    private final double intercept, slope;

    public LinearRegression(double[] x, double[] y) {
        if (x.length != y.length) throw new IllegalArgumentException("array lengths are not equal");
        if (x.length == 0) throw new IllegalArgumentException("array lengths is zero");
        double xMean = getMean(x);
        double yMean = getMean(y);
        double xVar = getCovariance(x, x);
        double xyCov = getCovariance(x, y);
        slope = xyCov / xVar;
        intercept = yMean - slope * xMean;
    }

    private static double getMean(double[] values) {
        return Arrays.stream(values).sum() / values.length;
    }

    private static double getMean(double[] values1, double[] values2) {
        double[] xy = new double[values1.length];
        for (int i = 0; i < xy.length; i++) {
            xy[i] = values1[i] * values2[i];
        }
        return getMean(xy);
    }

    private static double getCovariance(double[] values1, double[] values2) {
        return getMean(values1, values2) - getMean(values1) * getMean(values2);
    }

    @Override
    public double predict(double x) {
        return slope * x + intercept;
    }

    @Override
    public String toString() {
        return "LinearRegression{" +
                "intercept=" + intercept +
                ", slope=" + slope +
                '}';
    }
}
