package chocz.pj.calculationengine;

public class AllResults {

    public Point aPoint;
    public Point bPoint;
    public Orthodrome orthodrome;
    public Loxodrome loxodrome;
    public SphericalTriangle sphericalTriangle;
    public BearingAngles bearingAngles;
    public Point firstOrthodromeVertex;
    public Point secondOrthodromeVertex;
    public Case caseType;

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
        this.setaPoint(bearingAngles.getaPoint());
        this.setbPoint(bearingAngles.getbPoint());
        this.setSphericalTriangle(bearingAngles.getSphericalTriangle());
        this.setCaseType(verifySpecialCases(getaPoint(), getbPoint()));
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

    protected Case verifySpecialCases(Point aPoint, Point bPoint) {
        double difLambda = Math.abs(aPoint.lambda - bPoint.lambda);
        double sumPhi = aPoint.phi + bPoint.phi;

        if (((aPoint.lambda == bPoint.lambda) && (aPoint.phi == bPoint.phi)) || (aPoint.phi*bPoint.phi == 8100)) {
            return Case.SAME_POINT;

        } else if (((difLambda == 180) && (sumPhi == 0)) || ((aPoint.lambda == 0) && (bPoint.lambda == 0) && (sumPhi == 0))) {
            return Case.OPPOSITE_POINTS;

        } else if ((aPoint.phi == 0) && (bPoint.phi == 0)) {
            return Case.EQUATOR_SAIL;

        } else if ((aPoint.lambda == bPoint.lambda) || (difLambda == 180) || (aPoint.phi*bPoint.phi == -8100) || (Math.abs(aPoint.phi) == 90 || Math.abs(bPoint.phi) == 90)) {
            return Case.MERIDIAN_SAIL;

        } else {
            return Case.GENERAL;

        }
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

    private Orthodrome getOrthodrome() {
        return orthodrome;
    }

    private void setOrthodrome(Orthodrome orthodrome) {
        this.orthodrome = orthodrome;
    }

    private void setLoxodrome(Loxodrome loxodrome) {
        this.loxodrome = loxodrome;
    }

    private SphericalTriangle getSphericalTriangle() {
        return sphericalTriangle;
    }

    private void setSphericalTriangle(SphericalTriangle sphericalTriangle) {
        this.sphericalTriangle = sphericalTriangle;
    }

    private void setBearingAngles(BearingAngles bearingAngles) {
        this.bearingAngles = bearingAngles;
    }

    private void setFirstOrthodromeVertex(Point firstOrthodromeVertex) {
        this.firstOrthodromeVertex = firstOrthodromeVertex;
    }

    private void setSecondOrthodromeVertex(Point secondOrthodromeVertex) {
        this.secondOrthodromeVertex = secondOrthodromeVertex;
    }

    private Case getCaseType() {
        return caseType;
    }

    private void setCaseType(Case caseType) {
        this.caseType = caseType;
    }

}