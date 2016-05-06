package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is 'read-only'.
 * I.g. read-only elements are input elements which are locked against value change but can still be edited.
 * <p>
 * This condition is true in case the 'readonly' attribute is either 'true' (HTML) or 'readonly' (XHTML).
 *
 * @see PageFragment#getAttribute(String)
 * @since 2.0
 */
public class ReadOnly implements Predicate<PageFragment> {

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getAttribute("readonly")
            .filter(value -> isHtmlReadOnly(value) || isXhtmlReadOnly(value))
            .isPresent();
    }

    private boolean isHtmlReadOnly(String value) {
        return "true".equals(value);
    }

    private boolean isXhtmlReadOnly(String value) {
        return "readonly".equals(value);
    }

    @Override
    public String toString() {
        return "read-only";
    }

}
