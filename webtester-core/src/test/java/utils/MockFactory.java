package utils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.mockito.Mockito;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class MockFactory {

    public static WebDriver webDriver() {

        WebDriver webDriver = mock(WebDriver.class);
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
        WebDriver.Options options = mock(WebDriver.Options.class);
        WebDriver.Window window = mock(WebDriver.Window.class);

        doReturn(window).when(options).window();

        doReturn(targetLocator).when(webDriver).switchTo();
        doReturn(navigation).when(webDriver).navigate();
        doReturn(options).when(webDriver).manage();

        return webDriver;

    }

    public static Browser browser(WebDriver webDriver) {
        Browser browser = browser();
        doReturn(webDriver).when(browser).webDriver();
        return browser;
    }

    public static Browser browser() {
        return Mockito.mock(Browser.class);
    }

    public static PageFragmentMockBuilder fragment() {
        return new PageFragmentMockBuilder();
    }

    public static SingleSelectMockBuilder singleSelect() {
        return new SingleSelectMockBuilder();
    }

    public static MultiSelectMockBuilder multiSelect() {
        return new MultiSelectMockBuilder();
    }

    public static Selectable selectable(boolean selected) {
        Selectable selectable = mock(Selectable.class);
        doReturn(selected).when(selectable).isSelected();
        return selectable;
    }

    public static SelectableMockBuilder selectable() {
        return new SelectableMockBuilder();
    }

    public static class SelectableMockBuilder {

        private boolean selected;

        public SelectableMockBuilder isSelected() {
            this.selected = true;
            return this;
        }

        public SelectableMockBuilder isNotSelected() {
            this.selected = false;
            return this;
        }

        public Selectable build() {
            Selectable selectable = mock(Selectable.class);
            doReturn(selected).when(selectable).isSelected();
            doReturn(!selected).when(selectable).isNotSelected();
            return selectable;
        }

    }

    public static class PageFragmentMockBuilder {

        private Map<String, String> attributes = new HashMap<>();
        private String tagName = "div";
        private String visibleText = "";
        private boolean isEnabled = true;
        private boolean isVisible = true;
        private boolean isReadOnly;
        private boolean isPresent = true;
        private Throwable getWebElementException;
        private Optional<String> name = Optional.empty();

        public PageFragmentMockBuilder withName(String name) {
            this.name = Optional.ofNullable(name);
            return this;
        }

        public PageFragmentMockBuilder withoutName() {
            this.name = Optional.empty();
            return this;
        }

        public PageFragmentMockBuilder withTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public PageFragmentMockBuilder withoutTagName() {
            this.tagName = null;
            return this;
        }

        public PageFragmentMockBuilder withAttribute(String attribute) {
            return withAttribute(attribute, "");
        }

        public PageFragmentMockBuilder withAttribute(String attribute, String value) {
            attributes.put(attribute, value);
            return this;
        }

        public PageFragmentMockBuilder withoutAttribute(String attribute) {
            attributes.remove(attribute);
            return this;
        }

        public PageFragmentMockBuilder withVisibleText(String text) {
            visibleText = text;
            return this;
        }

        public PageFragmentMockBuilder editable() {
            return enabled().visible().writable();
        }

        public PageFragmentMockBuilder enabled() {
            isEnabled = true;
            return this;
        }

        public PageFragmentMockBuilder disabled() {
            isEnabled = false;
            return this;
        }

        public PageFragmentMockBuilder visible() {
            isVisible = true;
            return this;
        }

        public PageFragmentMockBuilder invisible() {
            isVisible = false;
            return this;
        }

        public PageFragmentMockBuilder readOnly() {
            isReadOnly = true;
            return this;
        }

        public PageFragmentMockBuilder writable() {
            isReadOnly = false;
            return this;
        }

        public PageFragmentMockBuilder present() {
            isPresent = true;
            return this;
        }

        public PageFragmentMockBuilder notPresent() {
            isPresent = false;
            return this;
        }

        public PageFragmentMockBuilder throwsNoSuchElementException() {
            getWebElementException = new NoSuchElementException("test exception");
            return this;
        }

        public PageFragment build() {

            PageFragment fragment = mock(PageFragment.class);

            doReturn(name).when(fragment).getName();
            doReturn(tagName).when(fragment).getTagName();

            doReturn(visibleText).when(fragment).getVisibleText();

            doReturn(isEnabled).when(fragment).isEnabled();
            doReturn(!isEnabled).when(fragment).isDisabled();
            doReturn(isVisible).when(fragment).isVisible();
            doReturn(!isVisible).when(fragment).isInvisible();
            doReturn(isPresent).when(fragment).isPresent();
            doReturn(!isPresent).when(fragment).isNotPresent();

            // stub attributes
            doReturn(Optional.empty()).when(fragment).getAttribute(anyString());
            doReturn(Optional.of(String.valueOf(isReadOnly))).when(fragment).getAttribute("readonly");
            for (Entry<String, String> attribute : attributes.entrySet()) {
                doReturn(Optional.of(attribute.getValue())).when(fragment).getAttribute(attribute.getKey());
            }

            // stub exceptions
            if (getWebElementException != null) {
                doThrow(getWebElementException).when(fragment).webElement();
            }

            return fragment;

        }

    }

    public static class SingleSelectMockBuilder {

        private Optional<Integer> selectedIndex = Optional.empty();
        private Optional<String> selectedValue = Optional.empty();
        private Optional<String> selectedText = Optional.empty();
        private List<String> allTexts = new LinkedList<>();
        private List<String> allValues = new LinkedList<>();
        private int numberOfOptions;

        public SingleSelectMockBuilder withTexts(String... texts) {
            allTexts = Arrays.asList(texts);
            return this;
        }

        public SingleSelectMockBuilder withValues(String... values) {
            allValues = Arrays.asList(values);
            return this;
        }

        public SingleSelectMockBuilder withNumberOfOptions(int count) {
            numberOfOptions = count;
            return this;
        }

        public SingleSelectMockBuilder withSelectedIndex(Integer index) {
            selectedIndex = Optional.of(index);
            return this;
        }

        public SingleSelectMockBuilder withoutSelectedIndex() {
            selectedIndex = Optional.empty();
            return this;
        }

        public SingleSelectMockBuilder withSelectedValue(String value) {
            selectedValue = Optional.of(value);
            return this;
        }

        public SingleSelectMockBuilder withoutSelectedValue() {
            selectedValue = Optional.empty();
            return this;
        }

        public SingleSelectMockBuilder withSelectedText(String text) {
            selectedText = Optional.of(text);
            return this;
        }

        public SingleSelectMockBuilder withoutSelectedText() {
            selectedText = Optional.empty();
            return this;
        }

        public SingleSelect build() {

            SingleSelect select = mock(SingleSelect.class);

            doReturn(new LinkedList<>(allTexts)).when(select).getOptionTexts();
            doReturn(new LinkedList<>(allValues)).when(select).getOptionValues();
            doReturn(selectedIndex).when(select).getSelectionIndex();
            doReturn(selectedValue).when(select).getSelectionValue();
            doReturn(selectedText).when(select).getSelectionText();
            doReturn(numberOfOptions).when(select).getOptionCount();

            return select;

        }

    }

    public static class MultiSelectMockBuilder {

        private List<Integer> selectedIndices = new LinkedList<>();
        private List<String> selectedValues = new LinkedList<>();
        private List<String> selectedTexts = new LinkedList<>();
        private List<String> allTexts = new LinkedList<>();
        private List<String> allValues = new LinkedList<>();
        private int numberOfOptions;
        private int numberOfSelectedOptions;

        public MultiSelectMockBuilder withOptionTexts(String... texts) {
            allTexts = Arrays.asList(texts);
            return this;
        }

        public MultiSelectMockBuilder withOptionValues(String... values) {
            allValues = Arrays.asList(values);
            return this;
        }

        public MultiSelectMockBuilder withNumberOfOptions(int count) {
            numberOfOptions = count;
            return this;
        }

        public MultiSelectMockBuilder withNumberOfSelectedOptions(int count) {
            numberOfSelectedOptions = count;
            return this;
        }

        public MultiSelectMockBuilder withSelectedIndices(Integer... indices) {
            selectedIndices.addAll(Arrays.asList(indices));
            return this;
        }

        public MultiSelectMockBuilder withoutSelectedIndices() {
            selectedIndices.clear();
            return this;
        }

        public MultiSelectMockBuilder withSelectedValues(String... values) {
            selectedValues.addAll(Arrays.asList(values));
            return this;
        }

        public MultiSelectMockBuilder withoutSelectedValues() {
            selectedValues.clear();
            return this;
        }

        public MultiSelectMockBuilder withSelectedTexts(String... texts) {
            selectedTexts.addAll(Arrays.asList(texts));
            return this;
        }

        public MultiSelectMockBuilder withoutSelectedTexts() {
            selectedTexts.clear();
            return this;
        }

        public MultiSelect build() {

            MultiSelect select = mock(MultiSelect.class);

            doReturn(new LinkedList<>(allTexts)).when(select).getOptionTexts();
            doReturn(new LinkedList<>(allValues)).when(select).getOptionValues();

            doReturn(selectedIndices).when(select).getSelectionIndices();
            doReturn(selectedIndices.stream()).when(select).streamSelectionIndices();

            doReturn(selectedValues).when(select).getSelectionValues();
            doReturn(selectedValues.stream()).when(select).streamSelectionValues();

            doReturn(selectedTexts).when(select).getSelectionTexts();
            doReturn(selectedTexts.stream()).when(select).streamSelectionTexts();

            doReturn(numberOfOptions).when(select).getOptionCount();
            doReturn(numberOfSelectedOptions).when(select).getSelectionCount();

            return select;

        }

    }

}
