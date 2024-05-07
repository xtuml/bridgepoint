package lib;

/**
 * Provides implementations for standard string manipulation functions.
 */
public class STRING {

    /**
     * Convert an integer to a string.
     *
     * @param i the integer to convert
     * @return the string representation of the input integer
     */
    public static String itoa(final int i) {
    	return Integer.toString(i);
    }

    /**
     * Convert a string to an integer.
     *
     * @param s the string to parse
     * @return the integer representation of the input string
     * @throws NumberFormatException if the string cannot be parsed to an integer.
     */
    public static int atoi(final String s) {
    	return Integer.parseInt(s);
    }

    /**
     * Get a substring of an input string from {@code begin} (inclusive) to
     * {@code end} (exclusive). If {@code begin < 0}, the result is a substring
     * starting at the beginning of {@code s}.  If {@code begin >} the length
     * of {@code s - 1}, the result is an empty string.  If {@code end < 0} or
     * {@code >} the length of {@code s}, the result is a substring starting at
     * {@code begin} to the end of {@code s}.  If {@code end <= begin}, the
     * result is an empty string.
     *
     * @param s the input string
     * @param begin the start index of the substring (inclusive)
     * @param end the end index of the substring (exclusive)
     * @return the substring of {@code s} from {@code begin} (inclusive) to
     * {@code end} (exclusive)
     */
	public static String substr(final String s, final int begin, final int end) {
		int b = begin;
		int e = end;
		if ((b > s.length() - 1) || (e >= 0 && e <= b))
			return "";
		if (b < 0)
			b = 0;
		if (e < 0 || e > s.length()) {
			return s.substring(b);
		} else {
			return s.substring(b, e);
		}
	}

    /**
     * Get the length of a string
     *
     * @param s the input string
     * @return the length of the input string
     */
    public static int strlen(final String s) {
        return s.length();
    }

    /**
     * Gets the index of the first occurance of {@code needle} in {@code
     * haystack}.  Returns -1 if {@code needle} is not contained in {@code
     * haystack}.
     *
     * @param haystack the string to search
     * @param needle the string pattern to search for
     * @return the index of the first occurance of {@code needle} in {@code
     * haystack}
     */
    public static int indexof(final String haystack, final String needle) {
        return haystack.indexOf(needle);
    }

    /**
     * Trim whitespace from the start and end of a string.
     *
     * @param s the input string
     * @return the input string with all leading and trailing whitespace
     * removed.
     */
    public static String trim(final String s) {
    	return s.trim();
    }

    /**
     * Return a literal double quote.
     *
     * @return a literal double quote
     */
    public static String quote() {
    	return "\"";
    }

    /**
     * Replace all single quotes with two single quotes for escaping SQL insert
     * statements.
     *
     * @param s the input string
     * @return the input string with all single quotes replaced with two single
     * quotes
     */
    public static String escapetics(final String s) {
        return s.replaceAll("'", "''");
    }

    /**
     * Replace all occurances of two adjacent single quotes with one single
     * quote for unescaping SQL insert statements.
     *
     * @param s the input string
     * @return the input string with all occurances of two adjacent single
     * quotes replaced with one single quote
     */
    public static String unescapetics(final String s) {
        return s.replaceAll("''", "'");
    }

    /**
     * Replace all occurances of {@code pattern} with one {@code replacement}
     * quote for unescaping SQL insert statements.
     *
     * @param s the input string
     * @param the regex pattern string to find and replace
     * @param replacement the string to insert
     * @return the input string with all occurances of two adjacent single
     * quotes replaced with one single quote
     */
    public static String replaceall(final String s, final String pattern, final String replacement) {
        return s.replaceAll(pattern, replacement);
    }

}
