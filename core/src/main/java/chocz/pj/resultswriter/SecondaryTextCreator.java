package chocz.pj.resultswriter;

import chocz.pj.calculationengine.AllResults;
import chocz.pj.calculationengine.Case;
import chocz.pj.calculationengine.Point;
import chocz.pj.calculationengine.SphericalTriangle;

import static chocz.pj.util.Converter.ddToDmString;

public class SecondaryTextCreator {

    public static final String[] SECONDARY_INSTRUCTION_STRING = {
            "\n\n\n" +
                    " -----------------------INSTRUKCJA------------------------" + "\n\n" +
                    "  Akceptowalny zakres danych wejściowych to:" + "\n\n" +
                    "  szerokość od 0°00.0'' do  90°00.0'' " + "\n" +
                    "  długość   od 0°00.0'' do 180°00.0'' " + "\n\n" +
                    "  Kąty podajemy jako liczby całkowite od 0   do 180  " + "\n" +
                    "  Minuty jako liczby dziesiętne       od 0.0 do  59.9",
            "\n\n\n" +
                    " -----------------------INSTRUCTION----------------------" + "\n\n" +
                    "  Acceptable range of input parameters are:" + "\n\n" +
                    "  latitude  from 0°00.0'' to  90°00.0'' " + "\n" +
                    "  longitude from 0°00.0'' to 180°00.0'' " + "\n\n" +
                    "  Degrees are to be given as integers from 0   to 180  " + "\n" +
                    "  Minutes as floating numbers         from 0.0 do  59.9"};



    public static final String[] SECONDARY_INVALID_DATA_INSTRUCTION_STRING = {
            "\n" +
                    " ---------------------danie niepoprawne-------------------" + "\n\n" +
                    "  Proszę o podanie poprawnych danych" + "\n\n" +
                    "  Akceptowalny zakres danych wejściowych to:" + "\n\n" +
                    "  szerokość od 0°00.0'' do  90°00.0'' " + "\n" +
                    "  długość   od 0°00.0'' do 180°00.0'' " + "\n\n" +
                    "  Kąty podajemy jako liczby całkowite od 0   do 180  " + "\n" +
                    "  Minuty jako liczby dziesiętne       od 0.0 do  59.9",
            "\n" +
                    " ----------------------not valid data---------------------" + "\n\n" +
                    "  Please enter proper input data" + "\n\n" +
                    "  Acceptable range of input parameters are:" + "\n\n" +
                    "  latitude  from 0°00.0'' to  90°00.0'' " + "\n" +
                    "  longitude from 0°00.0'' to 180°00.0'' " + "\n\n" +
                    "  Degrees are to be given as integers from 0   to 180  " + "\n" +
                    "  Minutes as floating numbers         from 0.0 do  59.9"};


    private static final String SEPARATOR_DASH = " - ";

    private static final String[] CORRECT_VALUES_STRING = {
            " ------------poprawne dane",
            " -------------correct values"};

    private static final String[] GENERAL_CASE_STRING = {
            "przypadek ogólny-------------",
            "general case---------------"};

    private static final String[] SPECIAL_CASE_STRING = {
            "przypadek szczególny---------",
            "special case---------------"};

    private static final String[] SPECIAL_CASE_OPPOSITE_STRING = {
            " ----------------punkty naprzeciwko siebie----------------",
            " ---------------------opposite points---------------------"};

    private static final String[] SPECIAL_CASE_EQUATOR_SAILING_STRING = {
            " -----------------poruszamy się po równiku----------------",
            " ----------------------equator sailing--------------------"};

    private static final String[] SPECIAL_CASE_MERIDIAN_SAILING_STRING = {
            " ----------------poruszamy się po południku---------------",
            " ----------------------meridian sailing-------------------"};

    private static final String[] INHOMOGENEOUS_ANGLES_STRING = {
            " - Kąty A,B są niejednorodne, więc wysokość leży na \n   zewnątrz trójkąta",
            " - A, B angles are inhomogeneous, so height of the \n   triangle lies outside"};

    private static final String[] HOMOGENEOUS_ANGLES_STRING = {
            " - Kąty A,B są jednorodne, więc wysokość leży wewnątrz \n   trójkąta",
            " - A, B angles are homogeneous, so height of the triangle \n   lies inside"};

    private static final String[] RIGHT_ANGLED_TRIANGLE = {
            " - Jeden z kątów jest prosty, więc jeden bok jest \n   wysokością trójkąta",
            " - One of the angle is right, so one of the sides is \n   height of the triangle"};

    private static final String[] ORTHODROME_GAIN_STRING = {
            " - zysk ortodromiczny wynosi ",
            " - orthodromic gain is equal to "};

    private static final String[] LOXODROMIC_BEARING_STRING = {
            " - loksodromiczny kąt drogi wynosi ",
            " - loxodromic bearing is equal to "};

    private static final String[] SPECIAL_CASE_OPPOSITE_INFO_STRING = {
            "\n" +
                    " - Podane punkty są dokładnie po przeciwległej stronie" + "\n" +
                    "   kuli ziemskiej. Do przebycia jest połowa obwodu," + "\n" +
                    "   a do wyboru jest nieskończenie wiele ortodrom" + "\n\n\n\n\n\n\n" +
                    " - A, B oraz h nie są wyznaczane" + "\n\n\n\n\n\n\n" +
                    " - Odległość ortodromiczna to połowa obwodu ziemi" + "\n\n\n\n\n\n" +
                    " - Brak możliwości określenia zysku ortodromicznego" + "\n\n\n\n\n\n" +
                    " - Początkowy kąt drogi może być przyjęty dowolnie" + "\n\n\n\n\n\n\n" +
                    " - Wierzchołki ortodromy mogą być dowolne i zależą od" + "\n" +
                    "   przyjętej ortodromy.",
            "\n" +
                    " - Given points are exactly on opposite sides of the" + "\n" +
                    "   globe.There is exactly half of circumference to go," + "\n" +
                    "   and infinitely many possibilities of orthodromes to" + "\n" +
                    "   choose" + "\n\n\n\n\n\n" +
                    " - A, B and h are not calculated" + "\n\n\n\n\n\n\n" +
                    " - Orthodromic distance is equal to half circumference" + "\n" +
                    "   of the globe" + "\n\n\n\n\n" +
                    " - It's not possible to calculate orthodromic gain" + "\n\n\n\n\n\n" +
                    " - Initial bearing can be chosen freely" + "\n\n\n\n\n\n\n" +
                    " - Orthodromic vertexes are not able to predict as" + "\n" +
                    "   they depends on chosen orthodrome."};

    private static final String[] SPECIAL_CASE_MERIDIAN_SAIL_INFO_STRING_BEGIN = {
            "\n" +
                    " - W przypadku poruszania się po południku trójkąt" + "\n" +
                    "   sferyczny abdABC nie istnieje, więc nie liczymy\n" +
                    "   kątów A, B oraz h",
            "\n" +
                    " - In case of meridian sailing, spherical triangle" + "\n" +
                    "   abdABC doesn't exist, so angles A, B and h are" + "\n" +
                    "   not being calculated"};

    private static final String[] SPECIAL_CASE_MERIDIAN_SAIL_INFO_STRING_ENDING = {
            "\n" +
                    " - Wierzchołkami ortodromy są odpowiednio biegun" + "\n" +
                    "   północny oraz południowy",
            "\n" +
                    " - North Pole and South Pole are orthodrome" + "\n" +
                    "   vertices"};

    private static final String[] SPECIAL_CASE_EQUATOR_SAIL_INFO_STRING_BEGIN = {
            "\n" +
                    " - W przypadku poruszania się po równiku trójkąt" + "\n" +
                    "   sferyczny abdABC posiada dwa kąty proste" + "\n",
            "\n" +
                    " - In case of equator sailing, spherical triangle" + "\n" +
                    "   abdABC has two right angles" + "\n"};

    private static final String[] SPECIAL_CASE_EQUATOR_SAIL_INFO_STRING_ENDING = {
            "\n" +
                    " - Wierzchołkami ortodromy są wszystkie punkty" + "\n" +
                    "   leżące na równiku",
            "\n" +
                    " - All points on the equator are orthodrome vertices"};

    private static final String[] SPECIAL_CASE_SAME_POINT_INFO_STRING = {
            "\n" +
                    " - Podano takie same punkty. Kalkulacja nie jest" + "\n" +
                    "   przeprowadzana. Proszę wprowadzić poprawne dane",
            "\n" +
                    " - Identical points are given. Calculation is not" + "\n" +
                    "   performed. Please type proper data"};

    private static final String[] SAIL_DIRECTION_STRING = {
            " - kierunek drogi to ",
            " - sail direction is "};

    private static final String[] LENGTH_UNIT_NM = {
            "Nm",
            "Mm"};

    private static final String LENGTH_UNIT_KM = "km";

    private AllResults allResults;
    private int languageCode;
    private Case caseType;

    public SecondaryTextCreator(AllResults allResults, int languageCode, Case caseType) {
        this.allResults = allResults;
        this.languageCode = languageCode;
        this.caseType = caseType;
    }

    public String printHelpInformation() {
        if (caseType == Case .OPPOSITE_POINTS) {
            return "\n" +
                    CORRECT_VALUES_STRING[languageCode] + SEPARATOR_DASH + SPECIAL_CASE_STRING[languageCode] + "\n" +
                    SPECIAL_CASE_OPPOSITE_STRING[languageCode] + "\n\n" +
                    SPECIAL_CASE_OPPOSITE_INFO_STRING[languageCode];

        } else if (caseType == Case .MERIDIAN_SAIL) {
            return "\n" +
                    CORRECT_VALUES_STRING[languageCode] + SEPARATOR_DASH + SPECIAL_CASE_STRING[languageCode] + "\n" +
                    SPECIAL_CASE_MERIDIAN_SAILING_STRING[languageCode] + "\n\n\n\n\n\n\n\n\n\n\n" +
                    SPECIAL_CASE_MERIDIAN_SAIL_INFO_STRING_BEGIN[languageCode] + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    meridianSailBearingAnglesInfo(allResults.aPoint, allResults.bPoint)[languageCode] + "\n\n\n\n\n\n" +
                    SPECIAL_CASE_MERIDIAN_SAIL_INFO_STRING_ENDING[languageCode];

        } else if (caseType == Case .EQUATOR_SAIL) {
            return "\n" +
                    CORRECT_VALUES_STRING[languageCode] + SEPARATOR_DASH + SPECIAL_CASE_STRING[languageCode] + "\n" +
                    SPECIAL_CASE_EQUATOR_SAILING_STRING[languageCode] + "\n\n\n\n\n\n\n\n\n\n\n" +
                    SPECIAL_CASE_EQUATOR_SAIL_INFO_STRING_BEGIN[languageCode] + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    equatorSailBearingAnglesInfo(allResults.aPoint, allResults.bPoint)[languageCode] + "\n\n\n\n\n\n" +
                    SPECIAL_CASE_EQUATOR_SAIL_INFO_STRING_ENDING[languageCode];

        } else if (caseType == Case .SAME_POINT) {
            return "\n\n" +
                    SPECIAL_CASE_SAME_POINT_INFO_STRING[languageCode];

        } else {
            return "\n" +
                    CORRECT_VALUES_STRING[languageCode] + SEPARATOR_DASH + GENERAL_CASE_STRING[languageCode] + "\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    checkHomogeneousAngles(allResults.sphericalTriangle) + "\n\n\n\n\n\n\n\n\n\n\n" +
                    ORTHODROME_GAIN_STRING[languageCode] +
                    String.valueOf(String.format("%.2f", allResults.loxodrome.orthodromeGainNm)) + " " + LENGTH_UNIT_NM[languageCode] +
                    " (" +  String.valueOf(String.format("%.2f", allResults.loxodrome.orthodromeGainKm)) + " " + LENGTH_UNIT_KM + ")." + "\n\n" +
                    LOXODROMIC_BEARING_STRING[languageCode] + ddToDmString("", allResults.loxodrome.bearing) + "\n\n\n\n\n" +
                    SAIL_DIRECTION_STRING[languageCode] + allResults.bearingAngles.direction;
        }
    }

    private String[] meridianSailBearingAnglesInfo(Point aPoint, Point bPoint) {
        if (aPoint.phi > bPoint.phi) {
            return new String[] {
                    " - Kąt drogi wynosi stale 180°00.0' (prosto na południe)",
                    " - Angle of the bearing is 180°00.0' (straight south)"};
        } else {
            return new String[] {
                    " - Kąt drogi wynosi stale   0°00.0' (prosto na północ)",
                    " - Angle of the bearing is   0°00.0' (straight north)"};
        }
    }

    private String[] equatorSailBearingAnglesInfo(Point aPoint, Point bPoint) {
        if (aPoint.lambda > bPoint.lambda) {
            return new String[] {
                    " - Kąt drogi wynosi stale 270°00.0' (prosto na zachód)",
                    " - Angle of the bearing is 270°00.0' (straight west)"};
        } else {
            return new String[] {
                    " - Kąt drogi wynosi stale  90°00.0' (prosto na wschód)",
                    " - Angle of the bearing is  90°00.0' (straight east)"};
        }
    }

    private String checkHomogeneousAngles(SphericalTriangle sphericalTriangle) {
        if ((sphericalTriangle.A > 90 && sphericalTriangle.B < 90) || (sphericalTriangle.B > 90 && sphericalTriangle.A < 90)) {
            return INHOMOGENEOUS_ANGLES_STRING[languageCode];

        } else if ((sphericalTriangle.A < 90 && sphericalTriangle.B < 90) || (sphericalTriangle.B > 90 && sphericalTriangle.A > 90)) {
            return HOMOGENEOUS_ANGLES_STRING[languageCode];

        } else {
            return RIGHT_ANGLED_TRIANGLE[languageCode];
        }
    }

}