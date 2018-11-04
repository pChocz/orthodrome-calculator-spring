package chocz.pj.resultswriter;

import chocz.pj.calculationengine.*;

import static chocz.pj.util.Converter.ddToDmString;

public class PrimaryTextCreator {

    public static final String[] PRIMARY_INSTRUCTION_STRING = {
            "\n" +
                    " -------------------Kalkulator ortodromy------------------" + "\n\n" +
                    " -----------------------O PROGRAMIE-----------------------" + "\n\n" +
                    "  Program wyznaczający podstawowe parametry ortodromy" + "\n" +
                    "  mając dane współrzędne geograficzne dwóch punktów A,B:" + "\n\n" +
                    "  - odległość ortodromiczną (w stopniach oraz Mm)," + "\n" +
                    "  - początkowy oraz końcowy kąt drogi," + "\n" +
                    "  - współrzędne wierzchołków ortodromy." + "\n\n" +
                    "  Dodatkowo program oblicza i wypisuje wszystkie parametry " + "\n" +
                    "  wymagane do obliczeń.",
            "\n" +
                    " -------------------Orthodrome calculator-----------------" + "\n\n" +
                    " ---------------------ABOUT APPLICATION-------------------" + "\n\n" +
                    "  Application calculates basic parameters of orthodrome" + "\n" +
                    "  having geographical coordinates of two points A,B:" + "\n\n" +
                    "  - orthodromic distance (given in degrees and NM)," + "\n" +
                    "  - initial and final bearing," + "\n" +
                    "  - vertices of the orthodrome." + "\n\n" +
                    "  Additionally application is calculating all parameters" + "\n" +
                    "  that are needed during the calculation."};

    public static final String[] PRIMARY_INVALID_DATA_INSTRUCTION_STRING = {
            "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ----- niepoprawne dane -----  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                -------- nie liczymy -------  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ----------------------------  ",
            "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                --- not valid input data ---  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ------- incalculable -------  " + "\n" +
                    "                ----------------------------  " + "\n" +
                    "                ----------------------------  "};

    private static final String SEPARATOR_LINE = " ---------------------------------------------------------";

    private static final String[] VALUES_LAT_LONG_STRING = {
            " ----przeliczone szerokości/długości punktów A oraz B-----",
            " ----calculated latitudes/longitudes of points A and B----"};

    private static final String[] VALUES_SPHERICAL_TRIANGLE_STRING = {
            " --------podstawowe długości oraz kąty w trójkącie--------",
            " -----values of sides and angles in spherical triangle----"};

    private static final String[] VALUE_ORTHODROME_STRING = {
            " -----------------odległość ortodromiczna-----------------",
            " -----------orthodromic (great circle) distance-----------"};

    private static final String[] VALUE_LOXODROME_STRING = {
            " -----------------odległość loksodromiczna----------------",
            " -------------------loxodromic distance-------------------"};

    private static final String[] VALUES_BEARING_ANGLES_STRING = {
            " -----------------------kąty drogi------------------------",
            " ----------------------bearing angles----------------------"};

    private static final String[] VALUES_ORTHODROMIC_VERTICES_STRING = {
            " ------------------wierzchołki ortodromy------------------",
            " -----------orthodromic (great circle) vertices-----------"};

    private static final String[] NOT_CALCULABLE_STRING = {
            "nie liczymy",
            "not calculable"};

    private static final String[] FREE_TO_CHOOSE_STRING = {
            "dowolny",
            "free to choose"};

    private static final String[] ANY_POINT_ON_EQUATOR_STRING = {
            "każdy punkt leżący na równiku",
            "any point on the equator"};

    private static final String[] NORTH_POLE_STRING = {
            "biegun północny",
            "north pole"};

    private static final String[] SOUTH_POLE_STRING = {
            "biegun południowy",
            "south pole"};

    private static final String[] LENGTH_UNIT_NM = {
            "Mm",
            "Nm"};

    private static final String LENGTH_UNIT_KM = "km";

    private AllResults allResults;
    private int languageCode;
    private Case caseType;

    public PrimaryTextCreator(AllResults allResults, int languageCode, Case caseType) {
        this.allResults = allResults;
        this.languageCode = languageCode;
        this.caseType = caseType;
    }

    public String printResultsValues() {
        if (caseType == Case.SAME_POINT) {
            return "\n" +
                    VALUES_LAT_LONG_STRING[languageCode] + "\n" +
                    printPhiAndLambdaValues(allResults.aPoint, allResults.bPoint) +
                    SEPARATOR_LINE;

        } else {
            return "\n" +
                    VALUES_LAT_LONG_STRING[languageCode] + "\n" +
                    printPhiAndLambdaValues(allResults.aPoint, allResults.bPoint) +
                    SEPARATOR_LINE + "\n\n" +

                    VALUES_SPHERICAL_TRIANGLE_STRING[languageCode] + "\n" +
                    printSphericalTriangleValues(allResults.getSphericalTriangle(), allResults.getOrthodrome()) +
                    SEPARATOR_LINE + "\n\n" +

                    VALUE_ORTHODROME_STRING[languageCode] + "\n" +
                    printOrthodromeValue(allResults.getOrthodrome()) +
                    SEPARATOR_LINE + "\n\n" +

                    VALUE_LOXODROME_STRING[languageCode] + "\n" +
                    printLoxodromeValue(allResults.loxodrome) +
                    SEPARATOR_LINE + "\n\n" +

                    VALUES_BEARING_ANGLES_STRING[languageCode] + "\n" +
                    printBearingAngles(allResults.getBearingAngles()) +
                    SEPARATOR_LINE + "\n\n" +

                    VALUES_ORTHODROMIC_VERTICES_STRING[languageCode] + "\n" +
                    printOrthodromeVertices(allResults.getFirstOrthodromeVertex(), allResults.secondOrthodromeVertex) +
                    SEPARATOR_LINE;
        }
    }

    private String printPhiAndLambdaValues(Point aPoint, Point bPoint) {
        return "\n" +
                "  φ_A =" + ddToDmString("phi", aPoint.getPhi()) + "\t\t" +
                "  λ_A =" + ddToDmString("lambda", aPoint.getLambda()) +
                "\n" +
                "  φ_B =" + ddToDmString("phi", bPoint.getPhi()) + "\t\t" +
                "  λ_B =" + ddToDmString("lambda", bPoint.getLambda()) +
                "\n\n";
    }

    private String printSphericalTriangleValues(SphericalTriangle sphericalTriangle, Orthodrome orthodrome) {
        if (caseType == Case.GENERAL) {
            return "\n" +
                    "  a = " + ddToDmString("long", sphericalTriangle.a) +
                    "\n" +
                    "  b = " + ddToDmString("long", sphericalTriangle.b) +
                    "\n\n" +
                    "  C = " + ddToDmString("long", sphericalTriangle.C) +
                    "\n" +
                    "  A = " + ddToDmString("long", sphericalTriangle.A) + "\t" +
                    "  h = " + ddToDmString("long", orthodrome.getHeight1()) + "  v " + ddToDmString("long", orthodrome.getHeight2()) +
                    "\n" +
                    "  B = " + ddToDmString("long", sphericalTriangle.B) +
                    "\n\n";

        } else if (caseType == Case.MERIDIAN_SAIL || caseType == Case.OPPOSITE_POINTS) {
            return "\n" +
                    "  a = " + ddToDmString("long", sphericalTriangle.a) +
                    "\n" +
                    "  b = " + ddToDmString("long", sphericalTriangle.b) +
                    "\n\n" +
                    "  C = " + ddToDmString("long", sphericalTriangle.C) +
                    "\n" +
                    "  A =  " + NOT_CALCULABLE_STRING[languageCode] + "\t" +
                    "  h =  " + NOT_CALCULABLE_STRING[languageCode] +
                    "\n" +
                    "  B =  " + NOT_CALCULABLE_STRING[languageCode] +
                    "\n\n";

        } else if (caseType == Case.EQUATOR_SAIL) {
            return "\n" +
                    "  a = " + ddToDmString("long", sphericalTriangle.a) +
                    "\n" +
                    "  b = " + ddToDmString("long", sphericalTriangle.b) +
                    "\n\n" +
                    "  C = " + ddToDmString("long", sphericalTriangle.C) +
                    "\n" +
                    "  A = " + ddToDmString("long", sphericalTriangle.A) + "\t" +
                    "  h = " + ddToDmString("long", orthodrome.getHeight1()) +
                    "\n" +
                    "  B = " + ddToDmString("long", sphericalTriangle.B) +
                    "\n\n";

        } else {
            return "ERROR";

        }
    }

    private String printOrthodromeValue(Orthodrome orthodrome) {
        return "\n" +
                "  d = " + ddToDmString("long", orthodrome.distanceAngles) +
                " = " + String.valueOf(String.format("%.2f", orthodrome.getDistanceNm())) + " " + LENGTH_UNIT_NM[languageCode] +
                " = " + String.valueOf(String.format("%.2f", orthodrome.distanceKm)) + " " + LENGTH_UNIT_KM +
                "\n\n";
    }

    private String printLoxodromeValue(Loxodrome loxodrome) {
        return "\n" +
                "  s =           " +
                "   " + String.valueOf(String.format("%.2f", loxodrome.lengthNm)) + " " + LENGTH_UNIT_NM[languageCode] +
                " = " + String.valueOf(String.format("%.2f", loxodrome.lengthKm)) + " " + LENGTH_UNIT_KM +
                "\n\n";
    }

    private String printBearingAngles(BearingAngles bearingAngles) {
        if (caseType == Case.OPPOSITE_POINTS) {
            return "\n" +
                    "  α =  " + FREE_TO_CHOOSE_STRING[languageCode] +
                    "\n" +
                    "  β =  180° - α" +
                    "\n\n";
        } else {
            return "\n" +
                    "  α = " + ddToDmString("long", bearingAngles.getInitialBearing()) +
                    "\n" +
                    "  β = " + ddToDmString("long", bearingAngles.getFinalBearing()) +
                    "\n\n";

        }
    }

    private String printOrthodromeVertices(Point firstOrthodromeVertex, Point secondOrthodromeVertex) {
        if (caseType == Case.EQUATOR_SAIL) {
            return "\n" +
                    "  W1  (" + ddToDmString("lat", firstOrthodromeVertex.getLatCalculated(), firstOrthodromeVertex.getLatSide()) +
                    "  ; xxx°xx,x' E/W )" +
                    "\n" +
                    "     " + ANY_POINT_ON_EQUATOR_STRING[languageCode] +
                    "\n\n";

        } else if (caseType == Case.MERIDIAN_SAIL) {
            return "\n" +
                    "  W1  (" + ddToDmString("lat", firstOrthodromeVertex.getLatCalculated(), firstOrthodromeVertex.getLatSide()) +
                    " ) - " + NORTH_POLE_STRING[languageCode] +
                    "\n" +
                    "  W2  (" + ddToDmString("lat", firstOrthodromeVertex.getLatCalculated(), firstOrthodromeVertex.getLatSide()) +
                    " ) - " + SOUTH_POLE_STRING[languageCode] +
                    "\n\n";

        } else if (caseType == Case.OPPOSITE_POINTS) {
            return "\n" +
                    "  W1  ( xx°xx,x' N/S ; xxx°xx.x' E/W )" +
                    "\n" +
                    "  W2  ( xx°xx,x' N/S ; xxx°xx.x' E/W )" +
                    "\n\n";

        } else {
            return "\n" +
                    "  W1  (" + ddToDmString("lat", firstOrthodromeVertex.getLatCalculated(), firstOrthodromeVertex.getLatSide()) +
                    "  ," + ddToDmString("long", firstOrthodromeVertex.getLongCalculated(), firstOrthodromeVertex.getLongSide()) + "  )" +
                    "\n" +
                    "  W2  (" + ddToDmString("lat", secondOrthodromeVertex.getLatCalculated(), secondOrthodromeVertex.getLatSide()) +
                    "  ," + ddToDmString("long", secondOrthodromeVertex.getLongCalculated(), secondOrthodromeVertex.getLongSide()) + "  )" +
                    "\n\n";
        }
    }

}