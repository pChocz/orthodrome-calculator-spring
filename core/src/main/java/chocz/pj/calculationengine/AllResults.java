package chocz.pj.calculationengine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllResults {

    public Point aPoint;
    public Point bPoint;
    private Orthodrome orthodrome;
    public Loxodrome loxodrome;
    private SphericalTriangle sphericalTriangle;
    private BearingAngles bearingAngles;
    private Point firstOrthodromeVertex;
    public Point secondOrthodromeVertex;
    private Case caseType;

    /**
     * It summarizes all results needed for printing out
     * the results for easier access by other methods
     *
     */
    public AllResults(Orthodrome orthodrome, Loxodrome loxodrome, BearingAngles bearingAngles) {
        this.setOrthodrome(orthodrome);
        this.setLoxodrome(loxodrome);
        this.setBearingAngles(bearingAngles);
        this.setFirstOrthodromeVertex(orthodrome.getFirstOrthodromeVertex());
        this.setSecondOrthodromeVertex(orthodrome.getSecondOrthodromeVertex());
        this.setAPoint(bearingAngles.getAPoint());
        this.setBPoint(bearingAngles.getBPoint());
        this.setSphericalTriangle(bearingAngles.getSphericalTriangle());
        this.setCaseType(verifySpecialCases(getAPoint(), getBPoint()));
    }

    public AllResults(Point aPoint, Point bPoint) {
        this.setCaseType(verifySpecialCases(aPoint, bPoint));
        this.setSphericalTriangle(new SphericalTriangle(aPoint, bPoint, getCaseType()));
        this.setOrthodrome(new Orthodrome(getSphericalTriangle(), aPoint, bPoint, getCaseType()));
        this.setFirstOrthodromeVertex(this.getOrthodrome().getFirstOrthodromeVertex());
        this.setSecondOrthodromeVertex(this.getOrthodrome().getSecondOrthodromeVertex());
        this.setBearingAngles(new BearingAngles(getSphericalTriangle(), aPoint, bPoint, getCaseType()));
        this.setLoxodrome(new Loxodrome(aPoint, bPoint, getOrthodrome(), getCaseType()));
    }

    static public Case verifySpecialCases(Point aPoint, Point bPoint) {
        double difLambda = Math.abs(aPoint.getLambda() - bPoint.getLambda());
        double sumPhi = aPoint.getPhi() + bPoint.getPhi();

        if (((aPoint.getLambda() == bPoint.getLambda()) && (aPoint.getPhi() == bPoint.getPhi())) || (aPoint.getPhi()*bPoint.getPhi() == 8100)) {
            return Case.SAME_POINT;

        } else if (((difLambda == 180) && (sumPhi == 0)) || ((aPoint.getLambda() == 0) && (bPoint.getLambda() == 0) && (sumPhi == 0))) {
            return Case.OPPOSITE_POINTS;

        } else if ((aPoint.getPhi() == 0) && (bPoint.getPhi() == 0)) {
            return Case.EQUATOR_SAIL;

        } else if ((aPoint.getLambda() == bPoint.getLambda()) || (difLambda == 180) || (aPoint.getPhi()*bPoint.getPhi() == -8100) || (Math.abs(aPoint.getPhi()) == 90 || Math.abs(bPoint.getPhi()) == 90)) {
            return Case.MERIDIAN_SAIL;

        } else {
            return Case.GENERAL;

        }
    }

}