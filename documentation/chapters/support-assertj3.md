[Home](../README.md)

# AssertJ Assertions
The support module `webtester-support-assertj3` provides AssertJ 3 assertion implementations for many properties of page 
fragments:

- `ButtonAssert`
- `GenericTextFieldAssert`
- `MultiSelectAssert`
- `PageFragmentAssert`
- `SelectableAssert`
- `SingleSelectAssert`

All of these can be accessed through a single utility class named `WebTesterAssertions`. Which extends AssertJ's Assertions 
class and therefore provides all of AssertJ's default assertions as well.

## Example
```java
TextField username = ...;
WebTesterAssertions.assertThat(username).hasText("fooUser");
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
