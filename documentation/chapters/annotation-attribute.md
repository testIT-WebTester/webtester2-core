[Home](../README.md)

# @Attribute
This annotation can be used within a `PageFragment` subclass in order to retrieve attributes of the underlying element.
Attribute values (which are returned as Strings from the `WebElement`) will be parsed to the method's return type.

**Constraints:**

- Annotated method must return String, Boolean, Long, Integer, Float, Double or any of those as an Optional<>.
- Annotated method must not have arguments.

## Example

```java
public interface FooFragment extends PageFragment {
 
    @Attribute("value")
    String value();
 
    @Attribute("number")
    Long number();
 
    @Attribute("optional")
    Optional<String> optional();
 
    ...
 
}
```
