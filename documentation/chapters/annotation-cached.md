[Home](../README.md)

# @Cached
This annotation can be added to `@IdentifyUsing` annotated methods of `Page` or `PageFragment` subclasses.
It will *override* the configured default caching behavior (property: `caches.enabled`) of the returned page fragment.

> If caching is enabled, resolved `WebElement` instances will be remembered for up to `5` seconds.
This mechanism is intended to reduce lookup time when executing multiple operations on the same web element in a short 
period of time where it is unlikely for the element to change. Please note, that enabling caching in AJAX heavy 
applications might make your tests unstable and increase the occurrence of `StaleElementReferenceException`.

**Cacheable Return Types:**

- Single `PageFragment` instances
- Lists, Sets and Streams of `PageFragment` instances

**Example for page:**
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

**Example for page fragment:**
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

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
