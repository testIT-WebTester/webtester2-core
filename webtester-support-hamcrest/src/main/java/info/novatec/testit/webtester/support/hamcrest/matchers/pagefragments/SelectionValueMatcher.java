package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionValueMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String value;
    private Optional<String> actualValue;

    public SelectionValueMatcher(String value) {
        this.value = value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with value <" + value + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualValue = item.getSelectionValue();
        return actualValue.filter(value::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualValue + ">");
    }

}
