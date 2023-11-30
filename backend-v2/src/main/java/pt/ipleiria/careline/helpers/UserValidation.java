package pt.ipleiria.careline.helpers;

public class UserValidation {
    public static boolean isNusValid(String nus) {
        return nus != null && nus.length() == 9;
    }

    public static void isSameUser(Long pathId, Long userId) {
        if ((pathId == null || userId == null) && pathId != userId) {
            throw new IllegalArgumentException("Invalid user id");
        }
    }
}
