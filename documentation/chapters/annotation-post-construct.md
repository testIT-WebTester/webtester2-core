[Home](../README.md)

# @PostConstruct
This annotation can be added to methods of `Page` or `PageFragment` subclasses.
Every annotated method will be invoked after an instance of this subclass was initialized.

These methods are generally used to verify that the correct page is displayed or a fragment has a certain state.

**Constraints:**

- The invocation order of multiple methods is not deterministic.
- Annotated methods must return null and have not arguments.

## Example for Page

```java
public interface FooPage extends Page {
 
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    @PostConstruct
    void assertThatWidgetIsVisible () {
        assertThat(widget).is(visible());
    }
 
    ...
 
}
```

## Example for Page Fragment

```java
public interface FooWidget extends PageFragment {
 
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    @PostConstruct
    void assertThatTextFieldsAreVisible () {
        assertThat(fieldOne).is(visible());
        assertThat(fieldTwo).is(visible());
    }
 
    ...
 
}
```
