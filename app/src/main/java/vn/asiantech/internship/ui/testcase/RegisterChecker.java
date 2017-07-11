package vn.asiantech.internship.ui.testcase;

/**
 * Created by Thanh Thien on 7/10/2017.
 * RegisterChecker
 */
public class RegisterChecker {
    private static char[] mNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static boolean checkMaxLength(String s, int maxLength) {
        return s.length() <= maxLength;
    }

    public static boolean checkMinLength(String s, int minLength) {
        return s.length() >= minLength;
    }

    public static boolean checkSpace(String s) {
        return s.indexOf(" ") == -1;
    }

    public static boolean isOnlyAlpha(String s) {
        return s.matches("[a-zA-Z0-9]+");
    }

    public static boolean hasNumber(String s) {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            for (char num : mNumbers) {
                if (s.charAt(i) == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasSymbol(String s) {
        String specialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        String str2[] = s.split("");
        for (int i = 0; i < str2.length; i++) {
            if (specialCharacters.contains(str2[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasUpChar(String s) {
        String specialCharacters = "ZXCVBNMLKJHGFDSAQWERTYUIOP";
        String str2[] = s.split("");
        for (int i = 0; i < str2.length; i++) {
            if (specialCharacters.contains(str2[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasUser(String password, String user) {
        return !password.toLowerCase().contains(user.toLowerCase());
    }
}
