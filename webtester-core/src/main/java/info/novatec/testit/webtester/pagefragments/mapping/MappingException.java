package info.novatec.testit.webtester.pagefragments.mapping;

import info.novatec.testit.webtester.WebTesterException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;


/**
 * This exception is thrown in case a {@link PageFragment} instance does not comply with the defined {@link Mapping}.
 *
 * @see Mapping
 * @see Mappings
 * @since 2.0
 */
public class MappingException extends WebTesterException {

    public MappingException(String message) {
        super(message);
    }

}
