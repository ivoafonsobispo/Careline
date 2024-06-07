package pt.ipleiria.careline.helpers;

import pt.ipleiria.careline.domain.enums.Severity;

public class HeartbeatSeverity {
    private static final int GOOD_MIN = 60;
    private static final int GOOD_MAX = 100;
    private static final int MEDIUM_MIN = 40;
    private static final int MEDIUM_MAX = 59;
    private static final int MEDIUM_MAX_UPPER = 120;

    public Severity getSeverityCategory(int heartbeat) {
        if (heartbeat >= GOOD_MIN && heartbeat <= GOOD_MAX) {
            return Severity.GOOD;
        } else if ((heartbeat >= MEDIUM_MIN && heartbeat <= MEDIUM_MAX) || (heartbeat >= GOOD_MAX + 1 && heartbeat <= MEDIUM_MAX_UPPER)) {
            return Severity.MEDIUM;
        } else {
            return Severity.CRITICAL;
        }
    }
}
