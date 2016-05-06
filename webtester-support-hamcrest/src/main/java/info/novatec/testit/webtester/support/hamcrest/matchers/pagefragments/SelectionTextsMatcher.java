package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectionTextsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final List<String> texts;
    private List<String> actualTexts;

    public SelectionTextsMatcher(List<String> texts) {
        this.texts = new LinkedList<>(texts);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with texts <" + texts + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualTexts = item.getSelectionTexts();
        return texts.equals(actualTexts);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualTexts + ">");
    }

}
