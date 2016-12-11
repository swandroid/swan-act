package interdroid.swancore.swansong;


public interface Expression extends Parseable<Expression> {

    /**
     * Separator used by the SWAN framework in internal ids. This should not be
     * used in expression ids.
     */
    String SEPARATOR = "~REMOTE~";
    String WEAR_SEPARATOR = "~WEAR~";

    String LEFT_SUFFIX = ".left";
    String RIGHT_SUFFIX = ".right";

    String[] RESERVED_SUFFIXES = {LEFT_SUFFIX,
            RIGHT_SUFFIX};

    // There are two special locations: local (on the device itself) and
    // independent (doesn't matter where)
    String LOCATION_SELF = "self";
    String LOCATION_INDEPENDENT = "independent";
    String LOCATION_INFER = "infer";
    String LOCATION_NEARBY = "NEARBY";
    String LOCATION_WEAR   = "wear";
    String LOCATION_CLOUD   = "cloud";
    String REGID_PREFIX = "regid:";

    void setInferredLocation(String location);

    String getLocation();

}
