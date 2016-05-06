[Home](../README.md)

# Hamcrest Matchers
The support module `webtester-support-hamcrest` provides Hamcrest `Matcher` implementations for many properties of page 
fragments:

- `AttributeMatcher`
- `AttributeValueMatcher`
- `ButtonLabelMatcher`
- `DisabledMatcher`
- `EnabledMatcher`
- `InvisibleMatcher`
- `NoOptionsMatcher`
- `NoSelectedOptionsMatcher`
- `NumberOfOptionsMatcher`
- `NumberOfSelectedOptionsMatcher`
- `OptionsMatcher`
- `OptionsTextsMatcher`
- `OptionsValuesMatcher`
- `PresentMatcher`
- `SelectedMatcher`
- `SelectedOptionsMatcher`
- `SelectionIndexMatcher`
- `SelectionIndicesMatcher`
- `SelectionTextMatcher`
- `SelectionTextsMatcher`
- `SelectionValueMatcher`
- `SelectionValuesMatcher`
- `TagMatcher`
- `TextContainingMatcher`
- `TextMatcher`
- `VisibleMatcher`
- `VisibleTextContainingMatcher`
- `VisibleTextMatcher`

All of these can be accessed through a single utility class named `WebTesterMatchers`. Which extends Hamcres's Matchers 
class and therefore provides all of Hamcrest's default matchers as well.

## Example
```java
TextField username = ...;
// without static imports
WebTesterMatchers.assertThat(username, WebTesterMatchers.has(WebTesterMatchers.text("fooUser")));
// with static imports
assertThat(username, has(text("fooUser")));
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
