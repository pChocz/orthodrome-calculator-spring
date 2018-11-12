package chocz.pj.controller;

import chocz.pj.calculationengine.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculationModel {

    private String aLatDegree;
    private String aLatMinute;
    private String aLatSide;
    private String aLongDegree;
    private String aLongMinute;
    private String aLongSide;

    private String bLatDegree;
    private String bLatMinute;
    private String bLatSide;
    private String bLongDegree;
    private String bLongMinute;
    private String bLongSide;

    private Point aPoint;
    private Point bPoint;

    private String primaryResultMessage;
    private String secondaryResultMessage;

    void setAPoint() {
        this.aPoint = new Point(
                this.aLatSide, Double.parseDouble(this.aLatDegree), Double.parseDouble(this.aLatMinute),
                this.aLongSide, Double.parseDouble(this.aLongDegree), Double.parseDouble(this.aLongMinute));
    }
    
    void setBPoint() {
        this.bPoint = new Point(
                this.bLatSide, Double.parseDouble(this.bLatDegree), Double.parseDouble(this.bLatMinute),
                this.bLongSide, Double.parseDouble(this.bLongDegree), Double.parseDouble(this.bLongMinute));
    }

    @Override
    public String toString() {
        return "A: ( " + aLatDegree + "° " + aLatMinute + "' " + aLatSide +
                " , " + aLongDegree + "° " + aLongMinute + "' " + aLongSide +  " )\n" +
                "B: ( " + bLatDegree + "° " + bLatMinute + "' " + bLatSide +
                " , " + bLongDegree + "° " + bLongMinute + "' " + bLongSide +  " )";
    }

}
