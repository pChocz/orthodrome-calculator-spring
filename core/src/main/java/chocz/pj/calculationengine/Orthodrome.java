package chocz.pj.calculationengine;

import lombok.Getter;
import lombok.Setter;

import static chocz.pj.util.Converter.toDegrees;
import static chocz.pj.util.Converter.toRadians;

@Getter
@Setter
public class Orthodrome {

    private Point aPoint;
    private Point bPoint;
    private Case caseType;
    public double distanceAngles;
    private double distanceNm;
    public double distanceKm;
    private double height1;
    private double height2;
    private Point firstOrthodromeVertex;
    private Point secondOrthodromeVertex;

    public Orthodrome(SphericalTriangle sphericalTriangle, Point aPoint, Point bPoint, Case caseType) {
        this.setSphericalTriangle();
        this.setAPoint(aPoint);
        this.setBPoint(bPoint);
        this.setCaseType(caseType);

        this.setDistanceAngles(sphericalTriangle.d);
        this.setDistanceNm(this.getDistanceAngles()*60);
        this.setDistanceKm(this.getDistanceNm()*1.852);

        this.setFirstOrthodromeVertex(calculateFirstOrthodromeVertex());
        this.setSecondOrthodromeVertex(calculateSecondOrthodromeVertex());
    }

    public Point calculateFirstOrthodromeVertex() {
        double numerator = Math.tan(getAPoint().getPhiRadians())*Math.sin(getBPoint().getLambdaRadians()) - Math.tan(getBPoint().getPhiRadians())*Math.sin(getAPoint().getLambdaRadians());

        double denominator = Math.tan(getAPoint().getPhiRadians())*Math.cos(getBPoint().getLambdaRadians()) - Math.tan(getBPoint().getPhiRadians())*Math.cos(getAPoint().getLambdaRadians());

        double lambdaR = toDegrees(Math.atan(numerator/denominator));
        double lambdaVertex = 90 + lambdaR;

        double phiVertex;
        if (getBPoint().getPhi() == 0) {
            phiVertex = toDegrees(Math.atan(Math.tan(getAPoint().getPhiRadians())/Math.sin(toRadians(getAPoint().getLambda() - lambdaR))));
        } else {
            phiVertex = toDegrees(Math.atan(Math.tan(getBPoint().getPhiRadians())/Math.sin(toRadians(getBPoint().getLambda() - lambdaR))));
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


}