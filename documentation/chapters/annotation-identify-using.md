[Home](../README.md)

# @IdentifyUsing
This annotation is used to tell the framework how the fragment(s) should be resolved when a page fragment returning method
is invoked.
The annotation provides all necessary information to identify the corresponding element(s) in the DOM of the displayed page.

**Properties:**

- `value` - A String containing identification data for the method to use the format depends on the used `ByProducer` 
defined with the `how` property.
- `how` - The by producer being used to identify the object in the DOM. This is a class reference to any `ByProducer` 
implementing class.

**Constraints:**

- Annotated method must return either `PageFragment`, `List<PageFragment>`, `Set<PageFragment>` or `Stream<PageFragment>` 
(subclasses of `PageFragment` are possible).

## Example on Page

```java
public interface FooPage extends Page {

    // using the default CSS Selector and an ID
    @IdentifyUsing("#foo")
    TextField fooField();
     
    // using an explicit ID selector
    @IdentifyUsing(value = "#bar", how = Id.class)
    TextField barField();
    
    // all text fields as a stream identified by their common class 'text-field'
    @IdentifyUsing(".text-field")
    Stream<TextField> allTextFields();

}
```

## Example as Part of Page Fragment

```java
public interface SearchWidget extends PageFragment {

    @IdentifyUsing("#query")
    SearchField query();
     
    @IdentifyUsing("#submit")
    Button submit();

}
```
