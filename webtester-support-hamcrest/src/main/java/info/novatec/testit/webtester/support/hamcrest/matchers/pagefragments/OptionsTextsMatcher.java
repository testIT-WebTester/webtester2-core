package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class OptionsTextsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final List<String> texts;
    private List<String> actualTexts;

    public OptionsTextsMatcher(List<String> texts) {
        this.texts = new LinkedList<>(texts);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("options with texts <" + texts + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualTexts = item.getOptionTexts();
        return texts.equals(actualTexts);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualTexts + ">");
    }

}
