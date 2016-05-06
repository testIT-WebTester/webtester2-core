[Home](../README.md)

# @Cached
This annotation can be added to `PageFragment` returning methods of `Page` or `PageFragment` subclasses.
It will override the configured default caching behavior of the returned page fragment.

> Caching will remember resolved `WebElement` instances for up to 5 seconds.

**Constraints:**

- Annotated methods must be identification methods (@IdentifyUsing).
- Annotated methods must not return collection of page fragments.

## Example for Page

```java
public interface FooPage extends Page {
 
    @Cached(true)
    @IdentifyUsing("#foo")
    FooWidget cachedWidget();
 
    @Cached(false)
    @IdentifyUsing("#foo")
    FooWidget uncachedWidget();
 
    ...
 
}
```

## Example for Page Fragment

```java
public interface FooWidget extends PageFragment {
 
    @Cached(true)
    @IdentifyUsing("#field")
    TextField cachedField();
 
    @Cached(false)
    @IdentifyUsing("#field")
    TextField uncachedField();
 
    ...
 
}
```
