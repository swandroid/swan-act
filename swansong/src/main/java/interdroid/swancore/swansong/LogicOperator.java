package interdroid.swancore.swansong;

public interface LogicOperator {

    int convert();

    LogicOperator convertInt(final int val);

    /**
     * Parses and returns a UnaryLogicOperator.
     *
     * @param val the string to parse
     * @return the corresponding UnaryLogicOperator
     */
    LogicOperator parseString(final String val);

    TriState operate(TriState first, TriState last);

}
