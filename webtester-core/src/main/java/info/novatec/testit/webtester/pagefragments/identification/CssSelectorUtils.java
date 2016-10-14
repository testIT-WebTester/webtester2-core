package info.novatec.testit.webtester.pagefragments.identification;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;


/**
 * This class includes utility methods for working with CSS Selectors.
 *
 * @since 2.0.4
 */
@UtilityClass
public class CssSelectorUtils {

    /** These are all special characters in CSS who need escaping. */
    private static final Character[] SPECIAL_CHARS =
        { '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[',
            '\\', ']', '^', '`', '{', '|', '}', '~' };
    /** See {@link #SPECIAL_CHARS}. */
    private static final Set<Character> SPECIAL_CHARS_SET = Arrays.stream(SPECIAL_CHARS).collect(Collectors.toSet());

    /**
     * Escape the given value by prefixing all {@link #SPECIAL_CHARS} with {@code \}.
     * <p>
     * This is necessary for all values used by CSS Selectors. For example when matching on the value of an attribute.
     *
     * @param value the value to escape
     * @return the escaped value
     * @since 2.0.4
     */
    public static String escape(String value) {
        StringBuilder escapedValue = new StringBuilder(value.length() * 2);
        for (Character c : value.toCharArray()) {
            if (SPECIAL_CHARS_SET.contains(c)) {
                escapedValue.append('\\');
            }
            escapedValue.append(c);
        }
        return escapedValue.toString();
    }

}
