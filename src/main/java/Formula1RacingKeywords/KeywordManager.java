package Formula1RacingKeywords;

public class KeywordManager {

    private static KeywordManager instance = null;
    private final DriverInformation driverInformation = DriverInformation.getInstance();
    private final CircuitInformation circuitInformation = CircuitInformation.getInstance();

    private KeywordManager() {
    }

    public static KeywordManager getInstance() {
        if (instance == null) {
            instance = new KeywordManager();
        }
        return instance;
    }

    public DriverInformation driverInformationKeywords() {
        return driverInformation;
    }

    public CircuitInformation circuitInformationKeywords() {
        return circuitInformation;
    }
}
