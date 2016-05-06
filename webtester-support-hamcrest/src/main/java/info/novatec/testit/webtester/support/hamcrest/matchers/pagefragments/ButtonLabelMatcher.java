package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.Button;


public class ButtonLabelMatcher<T extends Button> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String label;
    private String actualLabel;

    public ButtonLabelMatcher(String label) {
        this.label = label;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("label <" + label + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualLabel = item.getLabel();
        return label.equals(actualLabel);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualLabel + ">");
    }

}
