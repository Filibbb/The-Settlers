package ch.zhaw.hexboard;

/**
 * This class defines a label composed of two characters.
 *
 * @author tebe
 */
public final class Label {
    public static final char DEFAULT_CHARACTER = ' ';
    public static final int FIRST_DECIMAL = 0;
    public static final int SECOND_DECIMAL = 1;
    private final char first;
    private final char second;

    /**
     * Creates a label from two characters.
     *
     * @param firstChar  first character
     * @param secondChar second character
     */
    public Label(char firstChar, char secondChar) {
        first = firstChar;
        second = secondChar;
    }

    /**
     * Creates a label using the default character {@link #DEFAULT_CHARACTER}.
     */
    public Label() {
        first = ' ';
        second = ' ';
    }

    /**
     * Converts a field value to a representation label
     *
     * @param fieldValue the related value assigned to a field
     * @return a label including the fieldvalue representation.
     * @author weberph5
     */
    public static Label convertToFieldValueToLabel(Integer fieldValue) {
        if (fieldValue == null) {
            return new Label();
        } else if (fieldValue < 10) {
            return new Label(DEFAULT_CHARACTER, fieldValue.toString().charAt(FIRST_DECIMAL));
        } else {
            return new Label(fieldValue.toString().charAt(FIRST_DECIMAL), fieldValue.toString().charAt(SECOND_DECIMAL));
        }
    }

    public char getFirst() {
        return first;
    }

    public char getSecond() {
        return second;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "" + first + second;
    }
}
