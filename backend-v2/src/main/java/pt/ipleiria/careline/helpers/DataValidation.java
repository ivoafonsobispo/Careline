package pt.ipleiria.careline.helpers;

public class DataValidation {
    public static boolean isHeartbeatValid(Integer heartbeat) {
        return heartbeat != null && heartbeat >= 0 && heartbeat <= 220;
    }

    public static boolean isTemperatureValid(Float temperature) {
        return temperature != null && temperature >= 25 && temperature <= 50;
    }

}
