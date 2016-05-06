[Home](../README.md)

# @Must
This annotation can be added to `PageFragment` returning methods of `Page` or `PageFragment` subclasses.
Every annotated method will be invoked after an instance of this subclass was initialized and the condition provided by 
the annotation will be checked.

**Constraints:**

- The invocation order of multiple methods is not deterministic.
- Annotated methods must be identification methods (@IdentifyUsing).

## Available Conditions

- `VISIBLE`
- `PRESENT`
- `PRESENT_AND_VISIBLE`
- `ENABLED`
- `EDITABLE`
- `INTERACTABLE`

## Example for Page

```java
public interface FooPage extends Page {
 
    @Must(Be.VISIBLE)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

## Example for Page Fragment

```java
public interface FooWidget extends PageFragment {
 
    @Must(Be.VISIBLE)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @Must(Be.VISIBLE)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```
