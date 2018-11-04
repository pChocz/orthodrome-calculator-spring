package chocz.pj.calculationengine;

import lombok.Getter;
import lombok.Setter;

import static chocz.pj.util.Converter.toRadians;

@Setter
@Getter
public class Point {

    private String latSide;
    public double latDeg;
    public double latMin;
    private String longSide;
    public double longDeg;
    public double longMin;
    private double latCalculated;
    private double longCalculated;
    private double phi;
    private double lambda;
    double phiRadians;
    double lambdaRadians;

    public Point(String latSide, double latDeg, double latMin, String longSide, double longDeg, double longMin) {
        this.setLatSide(latSide);
        this.setLatDeg(latDeg);
        this.setLatMin(latMin);

        this.setLongSide(longSide);
        this.setLongDeg(longDeg);
        this.setLongMin(longMin);

        this.setLatCalculated(latDeg + latMin/60);
        this.setLongCalculated(longDeg + longMin/60);

        this.setPhi(latDeg + latMin/60);
        if (latSide.equals("S")) {
            this.setPhi(this.getPhi()*-1);
        }
        setPhiRadians(toRadians(this.getPhi()));

        this.setLambda(longDeg + longMin/60);
        if (longSide.equals("W")) {
            this.setLambda(this.getLambda()*-1);
        }
        setLambdaRadians(toRadians(this.getLambda()));
    }

    public Point(double phi, double lambda) {
        this.setPhi(phi);
        this.setPhiRadians(toRadians(phi));
        this.setLatCalculated(Math.abs(phi));

        this.setLambda(lambda);
        this.setLambdaRadians(toRadians(lambda));
        this.setLongCalculated(Math.abs(lambda));

        if (phi < 0) {
            this.setLatSide("S");
        } else if (phi > 0) {
            this.setLatSide("N");
        } else {
            this.setLatSide("");
        }

        if (lambda < 0) {
            this.setLongSide("W");
        } else if (lambda > 0) {
            this.setLongSide("E");
        } else {
            this.setLongSide("");
        }
    }

}