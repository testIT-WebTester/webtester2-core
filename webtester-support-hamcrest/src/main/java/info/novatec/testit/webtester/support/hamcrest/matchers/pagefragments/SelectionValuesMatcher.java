package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectionValuesMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final List<String> values;
    private List<String> actualValues;

    public SelectionValuesMatcher(List<String> values) {
        this.values = new LinkedList<>(values);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with values <" + values + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualValues = item.getSelectionValues();
        return values.equals(actualValues);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualValues + ">");
    }

}
