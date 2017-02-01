[Home](../README.md)

# WebTester's JUnit 5 Extensions

The support module `webtester-support-junit5` provides a set of JUnit 5 extensions:

- **ManagedBrowserExtension:**
	- Initialization of static and instance `Browser` fields.
	- Automatic opening and closing of managed `Browser` depending on field scope.
- **EntryPointExtension:**
    - Automatic navigation to 'entry point' URL.
    - Support variables which are resolved against a `Configuration`.
- **RegisteredEventListenerExtension:**
    - Initialization of instance `EventListener` fields.
    - Automatic registration and unregistration of `EventListener` to managed `Browser`.
- **PageInitializerExtension:**
	- Initialization of `Page` fields before each test.
	- Supports multiple `Browser` instances.
- **ConfigurationValueExtension:**
	- Injection of configuration values into instance fields of the following types:
		- `String`
		- `Integer`
		- `Long`
		- `Float`
		- `Double`
		- `Boolean`
	- Custom field types are supported via extensions

## ManagedBrowserExtension

By annotating a `Browser` field with `@Managed` the extension is triggered and will manage this field's life cycle:
- For `static` fields the initialization is done before the first `@BeforeAll` annotated method is invoked and the browser will be closed after the last `@AfterAll` annotated method was invoked.
- For instance fields the initialization is done before the first `@BeforeEach` annotated method is invoked and the browser will be closed after the last `@AfterEach` annotated method was invoked.

In case more than one `Browser` is used, each has to have a unique name.
The name can be provided by setting the `value` property of the `@Managed` annotation.

In order for WebTester to know what kind of Browser should be created for each field there are two annotations:
- `@CreateBrowsersUsing` can be used to annotate a test class and set the `BrowserFactory` to be used for the whole class.
- `@CreateUsing` can be used to annotate a `Browser` field directly in order to set the `BrowserFactory` for this field specifically. This will override any global definition!

### Example

```java
@EnableWebTesterExtensions
@CreateBrowsersUsing(FooFactory.class)
public class ExampleUiTest {

    @Managed("browser-1")
    static Browser staticBrowser;

    @Managed("browser-2")
    Browser instanceBrowser;

    @Managed("browser-3")
    @CreateUsing(BarFactory.class)
    Browser differentFactory;

    ...

}
```

## EntryPointExtension

By annotating any `@Managed` `Browser` field with `@EntryPoint` you can specify an URL which will be navigated to before each test execution.
The URL can be static or contain variables.
These variables are resolved against the annotated `Browser's` `Configuration` as `String` values.
In case a variable could not be resolved an `UnknownConfigurationKeyException` is thrown before the first test.

### Example

```java
@EnableWebTesterExtensions
@CreateBrowsersUsing(FooFactory.class)
public class ExampleUiTest {

    @Managed("browser-1")
    @EntryPoint("http://www.example.com")
    Browser staticUrl;

    @Managed("browser-2")
    @EntryPoint("${properties.url}")
    Browser variableUrl;

    @Managed("browser-3")
    @EntryPoint("http://${host}:${port}/index.html")
    Browser staticMixedWithVariableUrl;

    ...

}
```

## RegisteredEventListenerExtension

By annotating any instantiable `EventListener` field with `@Registered` you can specify a browser to which the `EventListener` has to be registered and unregistered automatically.

The extension will initialize the field if it's not pre-initialized and register the `EventListener` before the first @BeforeEach annotated method is invoked. The unregistration will be done after the last @AfterEach annotated method was invoked. 

In case more than one `Browser` is used, the target browsers must be specified explicitly.

### Examples

```java
   @EnableWebTesterExtensions
   @CreateBrowsersUsing(FooFactory.class)
   public class ExampleUiTest {
   
       @Managed
       Browser browser;
   
       @Registered
       MyEventListener created; // will have new instance
       
       @Registered
       EventListener preInitialized = new MyEventListener(); // this instance will be used
   
       ...
   
   }
```

```java
   @EnableWebTesterExtensions
   @CreateBrowsersUsing(FooFactory.class)
   public class ExampleUiTest {
   
       @Managed("browser-1")
       Browser browser1;
   
       @Managed("browser-2")
       Browser browser2;
       
       @Managed("browser-3")
       Browser browser3;
   
       @Registered(targets = { "browser-1", "browser-2" })
       CustomEventListener listener;
   
       ...
   
   }
```
   

## PageInitializerExtension

By annotating any `Page` field with `@Initialized` it will be initialized with a new instance of that `Page` class before the first `@BeforeEach` annotated method is invoked.
In case the test class has multiple `@Managed` `Browser` instances the `source` property of the annotation needs to specify which browser should be used to initialize the `Page`.

**Note:** This extension does not support static fields!

### Example

```java
@EnableWebTesterExtensions
@CreateBrowsersUsing(FooFactory.class)
public class ExampleUiTest {

    @Managed("browser-1")
    Browser browser1;

    @Managed("browser-2")
    Browser browser2;

    @Initialized(source = "browser-1")
    FooPage page1;

    @Initialized(source = "browser-2")
    BarPage page2;

    ...

}
```

## ConfigurationValueExtension

By annotating any field with `@ConfigurationValue` and providing a key by setting the `value` property the specified value will be retrieved from the `Configuration` and injected into the field.
This is done before the first `@BeforeEach` annotated method is executed.

Currently the primitive object types `String`, `Integer`, `Long`, `Float`, `Double` and `Boolean` are supported out of the box.
Custom types can be used when providing a matching `ConfigurationUnmarshaller` implementation / class reference as the annotation's `using` property

As with `@Initialize` for `Page` fields, a `source` can be specified in cases where multiple browsers are managed for a single test.

**Note:** This extension does not support static fields!

### Example

```java
@EnableWebTesterExtensions
@CreateBrowsersUsing(FooFactory.class)
public class ExampleUiTest {

    @Managed
    Browser browser;

    @ConfigurationValue("stringValue")
    String stringValue;

    @ConfigurationValue("integerVaue")
    Integer integerVaue;

    @ConfigurationValue("longValue")
    Long longValue;

    @ConfigurationValue("floatValue")
    Float floatValue;

    @ConfigurationValue("doubleValue")
    Double doubleValue;

    @ConfigurationValue("booleanValue")
    Boolean booleanValue;

    @ConfigurationValue(value = "fooValue", using = FooTypeUnmarshaller.class)
    FooType fooValue;

    ...

}
```

# Linked Documentation

- [Browser](browser.md)
- [Configuration](configuration.md)
