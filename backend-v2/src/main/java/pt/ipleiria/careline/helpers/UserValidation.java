package pt.ipleiria.careline.helpers;

public class UserValidation {
    public static boolean isNusValid(String nus) {
        return nus != null && nus.length() == 9;
    }
}
