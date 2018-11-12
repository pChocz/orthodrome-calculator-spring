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

        calculationModel.setAPoint();
        calculationModel.setBPoint();


        Case caseType = AllResults.verifySpecialCases(calculationModel.getAPoint(), calculationModel.getBPoint());

        SphericalTriangle sphericalTriangle = new SphericalTriangle(calculationModel.getAPoint(), calculationModel.getBPoint(), caseType);
        Orthodrome orthodrome = new Orthodrome(sphericalTriangle, calculationModel.getAPoint(), calculationModel.getBPoint(), caseType);
        BearingAngles bearingAngles = new BearingAngles(sphericalTriangle, calculationModel.getAPoint(), calculationModel.getBPoint(), caseType);
        Loxodrome loxodrome = new Loxodrome(calculationModel.getAPoint(), calculationModel.getBPoint(), orthodrome, caseType);

        AllResults allResults = new AllResults(orthodrome, loxodrome, bearingAngles);

        PrimaryTextCreator primaryText = new PrimaryTextCreator(allResults, 0, caseType);
        SecondaryTextCreator secondaryTextCreator = new SecondaryTextCreator(allResults, 0, caseType);

        calculationModel.setPrimaryResultMessage(primaryText.printResultsValues());
        calculationModel.setSecondaryResultMessage(secondaryTextCreator.printHelpInformation());

        log.info("finished");
        log.info(calculationModel.getPrimaryResultMessage());
        log.info(calculationModel.getSecondaryResultMessage());

        return "home";
    }

}
