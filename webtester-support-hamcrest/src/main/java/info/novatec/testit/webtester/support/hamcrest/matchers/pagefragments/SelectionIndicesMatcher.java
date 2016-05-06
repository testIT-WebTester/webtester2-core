package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectionIndicesMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final List<Integer> indices;
    private List<Integer> actualIndices;

    public SelectionIndicesMatcher(List<Integer> indices) {
        this.indices = new LinkedList<>(indices);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with indices <" + indices + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualIndices = item.getSelectionIndices();
        return indices.equals(actualIndices);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualIndices + ">");
    }

}
