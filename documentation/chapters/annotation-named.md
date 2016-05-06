[Home](../README.md)

# @Named
This annotation can be added to `@IdentifyUsing` annotated `PageFragment` returning methods of `Page` or `PageFragment` 
subclasses in order to override the name of the returned page fragment.

**Constraints:**

- Annotated methods must return single page fragment - no collections or streams are supported

## Example

```java
public interface FooPage extends Page {
 
    @Named("The Foo Widget 42")
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```
