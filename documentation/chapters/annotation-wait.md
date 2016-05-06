[Home](../README.md)

# @Wait
This annotation can be added to `PageFragment` returning methods of `Page` or `PageFragment` subclasses.
When the annotated method is invoked a wait operation is executed until the specified condition is met.

**Constraints:**

- Annotated methods must be identification methods (@IdentifyUsing).
- Annotated methods must return single page fragment, collections are not supported.

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
 
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

## Example for Page Fragment

```java
public interface FooWidget extends PageFragment {
 
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```
