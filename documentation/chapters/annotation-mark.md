[Home](../README.md)

# @Mark
This annotation can be added to methods of `PageFragment` subclasses in order to mark the page fragment in case the method
is invoked.

The marking feature can be activated / deactivated by setting the `markings.enabled` property.

The following marking types are available:

- USED - the state of the fragment was changed
- READ - the state of the fragment was read

The color of each of these types can be configured by changing any of the following properties:

- `markings.used.background`
- `markings.used.outline`
- `markings.read.background`
- `markings.read.outline`

Colors are specified as HEX RGB color codes, i.e. `#ffaa99`.

## Example

```java
public interface FooFragment extends PageFragment {
 
    @Mark(As.USED)
    default void doSomething() {
        ...
    }
 
}
```
