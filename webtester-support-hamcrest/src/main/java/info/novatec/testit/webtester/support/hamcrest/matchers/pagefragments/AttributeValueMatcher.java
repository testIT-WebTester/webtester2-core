package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeValueMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String attributeName;
    private final String value;
    private boolean attributePresent;
    private String actualValue;

    public AttributeValueMatcher(String attributeName, String value) {
        this.attributeName = attributeName;
        this.value = value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute <" + attributeName + "> with value <" + value + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        item.getAttribute(attributeName).ifPresent(value -> {
            attributePresent = true;
            actualValue = value;
        });
        return attributePresent && value.equals(actualValue);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        if(!attributePresent) {
            mismatchDescription.appendText("attribute was not present");
        } else {
            mismatchDescription.appendText("value was <" + actualValue + ">");
        }
    }

}
