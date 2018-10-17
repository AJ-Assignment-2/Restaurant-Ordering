package application.utilities;

public class Validation {

    public static boolean isAlpha(String s) {
        char[] charArr = s.toCharArray();

        for (char c : charArr) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String s) {
        char[] charArr = s.toCharArray();

        for (char c : charArr) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
