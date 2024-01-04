package pt.ipleiria.careline.helpers;

import pt.ipleiria.careline.domain.enums.Severity;

public class TemperatureSeverity {
    private static final double GOOD_MIN = 36.5;
    private static final double GOOD_MAX = 37.3;
    private static final double MEDIUM_MIN = 35;
    private static final double MEDIUM_MAX = 36.4;
    private static final double MEDIUM_MAX_UPPER = 39.9;

    public Severity getSeverityCategory(double temperature) {
        if (temperature >= GOOD_MIN && temperature <= GOOD_MAX) {
            return Severity.GOOD;
        } else if ((temperature >= MEDIUM_MIN && temperature <= MEDIUM_MAX) || (temperature >= GOOD_MAX + 1 && temperature <= MEDIUM_MAX_UPPER)) {
            return Severity.MEDIUM;
        } else {
            return Severity.CRITICAL;
        }
    }
}
