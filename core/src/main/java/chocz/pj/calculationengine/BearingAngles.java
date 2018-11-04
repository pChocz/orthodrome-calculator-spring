package chocz.pj.calculationengine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BearingAngles {

    //input
    private SphericalTriangle sphericalTriangle;
    private Point aPoint;
    private Point bPoint;
    private Case caseType;

    //output
    private double initialBearing;
    private double finalBearing;
    public String direction;


    public BearingAngles(SphericalTriangle sphericalTriangle, Point aPoint, Point bPoint, Case caseType) {
        this.setSphericalTriangle(sphericalTriangle);
        this.setAPoint(aPoint);
        this.setBPoint(bPoint);
        this.setCaseType(caseType);

        calculateBearingAngles();
    }

    private void calculateBearingAngles() {
        double sumPhi = this.getBPoint().getPhi() + this.getAPoint().getPhi();
        double deltaLambda = this.getBPoint().getLambda() - this.getAPoint().getLambda();

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
            if (getAPoint().getLambda() > getBPoint().getLambda()) {
                this.setInitialBearing(270);
                this.setFinalBearing(270);

            } else {
                this.setInitialBearing(90);
                this.setFinalBearing(90);
            }

        } else if (getCaseType() == Case.MERIDIAN_SAIL) {
            if (getAPoint().getPhi() > getBPoint().getPhi()) {
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

}