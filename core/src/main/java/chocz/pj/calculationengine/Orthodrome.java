package chocz.pj.calculationengine;

import static chocz.pj.util.Converter.toDegrees;
import static chocz.pj.util.Converter.toRadians;

public class Orthodrome {

    private Point aPoint;
    private Point bPoint;
    private Case caseType;
    public double distanceAngles;
    public double distanceNm;
    public double distanceKm;
    public double height1;
    public double height2;
    private Point firstOrthodromeVertex;
    private Point secondOrthodromeVertex;

    public Orthodrome(SphericalTriangle sphericalTriangle, Point aPoint, Point bPoint, Case caseType) {
        this.setSphericalTriangle();
        this.setaPoint(aPoint);
        this.setbPoint(bPoint);
        this.setCaseType(caseType);

        this.setDistanceAngles(sphericalTriangle.d);
        this.setDistanceNm(this.getDistanceAngles()*60);
        this.setDistanceKm(this.getDistanceNm()*1.852);

        this.setFirstOrthodromeVertex(calculateFirstOrthodromeVertex());
        this.setSecondOrthodromeVertex(calculateSecondOrthodromeVertex());
    }

    public Point calculateFirstOrthodromeVertex() {
        double numerator = Math.tan(getaPoint().getPhiRadians())*Math.sin(getbPoint().getLambdaRadians()) - Math.tan(getbPoint().getPhiRadians())*Math.sin(getaPoint().getLambdaRadians());

        double denominator = Math.tan(getaPoint().getPhiRadians())*Math.cos(getbPoint().getLambdaRadians()) - Math.tan(getbPoint().getPhiRadians())*Math.cos(getaPoint().getLambdaRadians());

        double lambdaR = toDegrees(Math.atan(numerator/denominator));
        double lambdaVertex = 90 + lambdaR;

        double phiVertex;
        if (getbPoint().getPhi() == 0) {
            phiVertex = toDegrees(Math.atan(Math.tan(getaPoint().getPhiRadians())/Math.sin(toRadians(getaPoint().getLambda() - lambdaR))));
        } else {
            phiVertex = toDegrees(Math.atan(Math.tan(getbPoint().getPhiRadians())/Math.sin(toRadians(getbPoint().getLambda() - lambdaR))));
        }

        this.setHeight1(90 - Math.abs(phiVertex));
        this.setHeight2(180 - this.getHeight1());

        Point firstOrthodromeVertex = new Point(phiVertex, lambdaVertex);

        if (!getCaseType().equals(Case.GENERAL)) {
            verifySpecialCasesHeight();
            firstOrthodromeVertex = verifySpecialCasesFirstOrthodromeVertex();
        }

        return firstOrthodromeVertex;
    }

    private void verifySpecialCasesHeight() {
        if (getCaseType() == Case.EQUATOR_SAIL) {
            this.setHeight1(90);
            this.setHeight2(999);

        } else {
            this.setHeight1(999);
            this.setHeight2(999);

        }
    }

    private Point verifySpecialCasesFirstOrthodromeVertex() {
        if (getCaseType() == Case.EQUATOR_SAIL) {
            return new Point(0, 999);

        } else if (getCaseType() == Case.MERIDIAN_SAIL) {
            return new Point(90, 999);

        } else {
            return new Point(999, 999);

        }
    }

    private Point calculateSecondOrthodromeVertex() {
        return new Point(this.getFirstOrthodromeVertex().getPhi()*(-1), this.getFirstOrthodromeVertex().getLambda() - 180);
    }

    private void setSphericalTriangle() {
        //input
    }

    private Point getaPoint() {
        return aPoint;
    }

    private void setaPoint(Point aPoint) {
        this.aPoint = aPoint;
    }

    private Point getbPoint() {
        return bPoint;
    }

    private void setbPoint(Point bPoint) {
        this.bPoint = bPoint;
    }

    private Case getCaseType() {
        return caseType;
    }

    private void setCaseType(Case caseType) {
        this.caseType = caseType;
    }

    private double getDistanceAngles() {
        return distanceAngles;
    }

    private void setDistanceAngles(double distanceAngles) {
        this.distanceAngles = distanceAngles;
    }

    private double getDistanceNm() {
        return distanceNm;
    }

    private void setDistanceNm(double distanceNm) {
        this.distanceNm = distanceNm;
    }

    private void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    private double getHeight1() {
        return height1;
    }

    private void setHeight1(double height1) {
        this.height1 = height1;
    }

    private void setHeight2(double height2) {
        this.height2 = height2;
    }

    Point getFirstOrthodromeVertex() {
        return firstOrthodromeVertex;
    }

    private void setFirstOrthodromeVertex(Point firstOrthodromeVertex) {
        this.firstOrthodromeVertex = firstOrthodromeVertex;
    }

    Point getSecondOrthodromeVertex() {
        return secondOrthodromeVertex;
    }

    private void setSecondOrthodromeVertex(Point secondOrthodromeVertex) {
        this.secondOrthodromeVertex = secondOrthodromeVertex;
    }

}