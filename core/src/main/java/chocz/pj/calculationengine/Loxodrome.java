package chocz.pj.calculationengine;

import static chocz.pj.util.Converter.toDegrees;

public class Loxodrome {

    //input
    private Point aPoint;
    private Point bPoint;
    private Orthodrome orthodrome;
    private Case caseType;

    //output
    public double lengthKm;
    public double lengthNm;
    public double orthodromeGainKm;
    public double orthodromeGainNm;
    public double bearing;

    public Loxodrome(Point aPoint, Point bPoint, Orthodrome orthodrome, Case caseType) {
        this.aPoint = aPoint;
        this.bPoint = bPoint;
        this.orthodrome = orthodrome;
        this.caseType = caseType;

        this.lengthNm = calculateLoxodrome();
        this.lengthKm = this.lengthNm*1.852;

        this.orthodromeGainNm = this.lengthNm - orthodrome.distanceNm;
        this.orthodromeGainKm = this.orthodromeGainNm*1.852;

        this.bearing = calculateBearing();
    }

    private double calculateLoxodrome() {
        double deltaPhiRadians = bPoint.phiRadians - aPoint.phiRadians;
        double deltaLambdaRadians = bPoint.lambdaRadians - aPoint.lambdaRadians;

        double deltaPsiRadians = Math.log(Math.tan(Math.PI/4 + bPoint.phiRadians/2)/Math.tan(Math.PI/4 + aPoint.phiRadians/2));
        double q = Math.abs(deltaPsiRadians) > 10e-12 ? deltaPhiRadians/deltaPsiRadians : Math.cos(aPoint.phiRadians);

        if (Math.abs(deltaLambdaRadians) > Math.PI) {
            deltaLambdaRadians = deltaLambdaRadians > 0 ? -(2*Math.PI - deltaLambdaRadians) : (2*Math.PI + deltaLambdaRadians);
        }

        if (caseType == Case.GENERAL) {
            return Math.sqrt(deltaPhiRadians*deltaPhiRadians + q*q*deltaLambdaRadians*deltaLambdaRadians)*3440;
        } else {
            return orthodrome.distanceNm;
        }
    }

    private double calculateBearing() {
        double bearing;
        double deltaPsiRadians = Math.log(Math.tan(Math.PI/4 + bPoint.phiRadians/2)/Math.tan(Math.PI/4 + aPoint.phiRadians/2));
        double deltaLambdaRadians = bPoint.lambdaRadians - aPoint.lambdaRadians;

        if (Math.abs(deltaLambdaRadians) > Math.PI) {
            deltaLambdaRadians = deltaLambdaRadians > 0 ? -(2*Math.PI - deltaLambdaRadians) : (2*Math.PI + deltaLambdaRadians);
        }
        bearing = toDegrees(Math.atan2(deltaLambdaRadians, deltaPsiRadians));
        bearing = (bearing < 0) ? (360 + bearing) : bearing;

        return bearing;
    }

}