package chocz.pj.controller;

import chocz.pj.calculationengine.*;
import chocz.pj.resultswriter.PrimaryTextCreator;
import chocz.pj.resultswriter.SecondaryTextCreator;
import chocz.pj.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

import static chocz.pj.resultswriter.PrimaryTextCreator.PRIMARY_INVALID_DATA_INSTRUCTION_STRING;
import static chocz.pj.resultswriter.SecondaryTextCreator.SECONDARY_INVALID_DATA_INSTRUCTION_STRING;

@org.springframework.stereotype.Controller
@Slf4j
public class Controller {

    @GetMapping(Mappings.HOME)
    public String initialize(Model model) {
        model.addAttribute("calculationModel", new CalculationModel());
        log.info("I'm in initialize method, hello!");

        return "home";
    }

    @PostMapping(Mappings.HOME)
    public String calculate(@ModelAttribute CalculationModel calculationModel) {

        log.info("I'm in calculate method, hello!");

        fillEmptyWithZeros(calculationModel);

        log.info("Input data are:");

        log.info("A: ( "
                + calculationModel.getALatDegree() + "° "
                + calculationModel.getALatMinute() + "' "
                + calculationModel.getALatSide() +
                " , " 
                + calculationModel.getALongDegree() + "° "
                + calculationModel.getALongMinute() + "' "
                + calculationModel.getALongSide() + 
                " )");

        log.info("B: ( "
                + calculationModel.getBLatDegree() + "° "
                + calculationModel.getBLatMinute() + "' "
                + calculationModel.getBLatSide() +
                " , "
                + calculationModel.getBLongDegree() + "° "
                + calculationModel.getBLongMinute() + "' "
                + calculationModel.getBLongSide() +
                " )");

        if (!validateInputData(calculationModel)) {
            calculationModel.setPrimaryResultMessage(PRIMARY_INVALID_DATA_INSTRUCTION_STRING[1]);
            calculationModel.setSecondaryResultMessage(SECONDARY_INVALID_DATA_INSTRUCTION_STRING[1]);

            log.info("finished");
            log.info(calculationModel.getPrimaryResultMessage());
            log.info(calculationModel.getSecondaryResultMessage());

            return "home";
        }

        calculationModel.setAPoint();
        calculationModel.setBPoint();
        Point aPoint = calculationModel.getAPoint();
        Point bPoint = calculationModel.getBPoint();

        if (!validateInputValues(aPoint, bPoint)) {
            calculationModel.setPrimaryResultMessage(PRIMARY_INVALID_DATA_INSTRUCTION_STRING[1]);
            calculationModel.setSecondaryResultMessage(SECONDARY_INVALID_DATA_INSTRUCTION_STRING[1]);

            log.info("finished");
            log.info(calculationModel.getPrimaryResultMessage());
            log.info(calculationModel.getSecondaryResultMessage());

            return "home";
        }


        Case caseType = AllResults.verifySpecialCases(aPoint, bPoint);

        SphericalTriangle sphericalTriangle = new SphericalTriangle(aPoint, bPoint, caseType);
        Orthodrome orthodrome = new Orthodrome(sphericalTriangle, aPoint, bPoint, caseType);
        BearingAngles bearingAngles = new BearingAngles(sphericalTriangle, aPoint, bPoint, caseType);
        Loxodrome loxodrome = new Loxodrome(aPoint, bPoint, orthodrome, caseType);

        AllResults allResults = new AllResults(orthodrome, loxodrome, bearingAngles);

        PrimaryTextCreator primaryText = new PrimaryTextCreator(allResults,1, caseType);
        SecondaryTextCreator secondaryTextCreator = new SecondaryTextCreator(allResults, 1, caseType);

        calculationModel.setPrimaryResultMessage(primaryText.printResultsValues());
        calculationModel.setSecondaryResultMessage(secondaryTextCreator.printHelpInformation());

        log.info("finished");
        log.info(calculationModel.getPrimaryResultMessage());
        log.info(calculationModel.getSecondaryResultMessage());

        return "home";
    }

    private void fillEmptyWithZeros(CalculationModel calculationModel) {
        if (calculationModel.getALatDegree().equals("")) {
            calculationModel.setALatDegree("0");
        }

        if (calculationModel.getALatMinute().equals("")) {
            calculationModel.setALatMinute("0");
        }

        if (calculationModel.getALongDegree().equals("")) {
            calculationModel.setALongDegree("0");
        }

        if (calculationModel.getALongMinute().equals("")) {
            calculationModel.setALongMinute("0");
        }

        if (calculationModel.getBLatDegree().equals("")) {
            calculationModel.setBLatDegree("0");
        }

        if (calculationModel.getBLatMinute().equals("")) {
            calculationModel.setBLatMinute("0");
        }

        if (calculationModel.getBLongDegree().equals("")) {
            calculationModel.setBLongDegree("0");
        }

        if (calculationModel.getBLongMinute().equals("")) {
            calculationModel.setBLongMinute("0");
        }
        
    }

    private boolean validateInputData(CalculationModel calculationModel) {
        List<String> numbersList = new ArrayList<>();
        numbersList.add(calculationModel.getALatDegree());
        numbersList.add(calculationModel.getALatMinute());
        numbersList.add(calculationModel.getALongDegree());
        numbersList.add(calculationModel.getALongMinute());
        numbersList.add(calculationModel.getBLatDegree());
        numbersList.add(calculationModel.getBLatMinute());
        numbersList.add(calculationModel.getBLongDegree());
        numbersList.add(calculationModel.getBLongMinute());

        for (String value : numbersList) {
            if (!value.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                return false;
            }
        }
        return true;
    }

    private boolean validateInputValues(Point aPoint, Point bPoint) {
        Point[] points = {aPoint, bPoint};
        for (Point point : points) {
            if (!validateLatitude(point) || !validateLongitude(point)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateLatitude(Point point) {
        if ((point.getLatCalculated() > 90) || (point.getLatMin() < 0 || point.getLatMin() >= 60) 
                || (point.getLatDeg() < 0 || point.getLatDeg() > 90)) {
            return false;
        }
        return true;
    }

    private boolean validateLongitude(Point point) {
        if ((point.getLongCalculated() > 180) || (point.getLongMin() < 0 || point.getLongMin() >= 60) 
                || (point.getLongDeg() < 0 || point.getLongDeg() > 180)) {
            return false;
        }
        return true;
    }

}
