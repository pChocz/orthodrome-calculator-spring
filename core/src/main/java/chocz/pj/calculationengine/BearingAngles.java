package chocz.pj.calculationengine;

public class BearingAngles {

    //input
    private SphericalTriangle sphericalTriangle;
    private Point aPoint;
    private Point bPoint;
    private Case caseType;

    //output
    public double initialBearing;
    public double finalBearing;
    public String direction;


    public BearingAngles(SphericalTriangle sphericalTriangle, Point aPoint, Point bPoint, Case caseType) {
        this.setSphericalTriangle(sphericalTriangle);
        this.setaPoint(aPoint);
        this.setbPoint(bPoint);
        this.setCaseType(caseType);

        calculateBearingAngles();
    }

    private void calculateBearingAngles() {
        double sumPhi = this.getbPoint().getPhi() + this.getaPoint().getPhi();
        double deltaLambda = this.getbPoint().getLambda() - this.getaPoint().getLambda();

        if ((deltaLambda > 0 && deltaLambda < 180) || (deltaLambda < -180 && deltaLambda > -360)) {
            this.setDirection("W->E");
        } else if ((deltaLambda < 0 && deltaLambda > -180) || (deltaLambda > 180 && deltaLambda < 360)) {
            this.setDirection("E->W");
        } else if ((deltaLambda == 180 || deltaLambda == -180) && (sumPhi == 0)) {
            this.setDirection("neutral");
        } else {
            this.setDirection("WRONG");
        }

        if (getDirection().equals("W->E")) {
            this.setInitialBearing(this.getSphericalTriangle().A);
            this.setFinalBearing(180 - this.getSphericalTriangle().B);
        } else {
            this.setInitialBearing(360 - this.getSphericalTriangle().A);
            this.setFinalBearing(180 + this.getSphericalTriangle().B);
        }

        verifySpecialCasesBearingAngles();
    }

    private void verifySpecialCasesBearingAngles() {
        if (getCaseType() == Case.EQUATOR_SAIL) {
            if (getaPoint().getLambda() > getbPoint().getLambda()) {
                this.setInitialBearing(270);
                this.setFinalBearing(270);

            } else {
                this.setInitialBearing(90);
                this.setFinalBearing(90);
            }

        } else if (getCaseType() == Case.MERIDIAN_SAIL) {
            if (getaPoint().getPhi() > getbPoint().getPhi()) {
                this.setInitialBearing(180);
                this.setFinalBearing(180);

            } else {
                this.setInitialBearing(0);
                this.setFinalBearing(0);
            }

        } else if (getCaseType() == Case.OPPOSITE_POINTS) {
            this.setInitialBearing(999);
            this.setFinalBearing(999);
        }
    }

    SphericalTriangle getSphericalTriangle() {
        return sphericalTriangle;
    }

    private void setSphericalTriangle(SphericalTriangle sphericalTriangle) {
        this.sphericalTriangle = sphericalTriangle;
    }

    Point getaPoint() {
        return aPoint;
    }

    private void setaPoint(Point aPoint) {
        this.aPoint = aPoint;
    }

    Point getbPoint() {
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

    private void setInitialBearing(double initialBearing) {
        this.initialBearing = initialBearing;
    }

    private void setFinalBearing(double finalBearing) {
        this.finalBearing = finalBearing;
    }

    private String getDirection() {
        return direction;
    }

    private void setDirection(String direction) {
        this.direction = direction;
    }

}