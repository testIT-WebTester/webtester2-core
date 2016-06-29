[Home](../README.md)

# WebTester's JUnit Runner
The support module `webtester-support-junit4` provides a custom JUnit Runner. This runner includes the following features:

- Life cycle management of class and test level `Browser` instances.
- Automatic navigation to an entry point to the application under test before each test.
- Injection of custom configuration properties into the following basic types: `String`, `Integer`, `Long`, `Float`, `Double` and `Boolean`

## Test Runner Life Cycle
The `WebTesterJUnitRunner` is based on the default `BlockJUnit4ClassRunner` and extends its life cycle at certain points.
The following shows the workflow of a test class with two test methods.

- static rules' `before()` methods 
- static `Browser` creation and injection
- injection of configuration properties into static fields annotated with `@ConfigurationValue`
- static methods annotated with `@BeforeClass`
- instance rules' `before()` methods
- instance `Browser` creation and injection
- injection of configuration properties into instance fields annotated with `@ConfigurationValue`
- instance methods annotated with `@Before`
- test method 1
- instance methods annotated with `@After`
- instance `Browsers` are closed
- instance rules' `after()` methods
- instance rules' `before()` methods
- instance `Browser` creation and injection
- injection of configuration properties into instance fields annotated with `@ConfigurationValue`
- instance methods annotated with `@Before`
- test method 2
- instance methods annotated with `@After`
- instance `Browsers` are closed
- instance rules' `after()` methods
- static methods annotated with `@AfterClass`
- static `Browsers` are closed
- static rules' `after()` methods

## Browser Life Cycle Management
All `Browser's` life cycle is managed on class as well as test level (static / non static fields).
To use this feature simply declare a `Browser` field in your test class and annotate it with `@Resource`.
Every uninitialized (null) field annotated in that way will be injected with a new `Browser` instance.
Fields which are already initialized with a `Browser` will be included in the life cycle handling as well,
but no new instances are created by the runner.

### Example
```java
@RunWith ( WebTesterJUnitRunner.class )
public class DifferentBrowserFieldModifiersTest {
 
    // A pre-initialized Browser which will not be initialized with a new
    // browser but the instance will be handled as part of the life cycle.
    @Resource
    static Browser preInitializedBrowser = new Browser(new FirefoxDriver());
 
    // A static Browser which will be initialized with a new instance before
    // the first and closed after the last test is executed.
    @Resource
    @CreateUsing ( ... )
    static Browser classScopedBrowser;
 
    // An instance Browser which will be initialized with a new instance
    // before and closed after each test is executed.
    @Resource
    @CreateUsing ( ... )
    Browser testScopedBrowser;
 
    // An instance Browser field which will be ignored by the runner since
    // the @Resource annotation is missing.
    Browser notAManagedBrowser;
 
    ...
 
}
```

## @CreateUsing
In order to configure the `BrowserFactory` used to create the `Browser` instances, the `@CreateUsing` annotation must be used.

### Example
```java
@RunWith ( WebTesterJUnitRunner.class )
public class DifferentBrowserFactoriesTest {
 
    // Uses the FirefoxFactory to create Firefox instances.
    @Resource
    @CreateUsing ( FirefoxFactory.class )
    static Browser firefox;
 
    // Uses the InternetExplorerFactoryto create IE instances.
    @Resource
    @CreateUsing ( InternetExplorerFactory.class )
    Browser internetExplorer;
 
    ...
 
}
```

## @EntryPoint
In order for a `Browser` to be automatically navigated to an applications entry point before each test,
the `@EntryPoint` annotation can used. The navigation at the beginning of each test is done whether or not a `Browser` is static!

### Example
```java
@RunWith ( WebTesterJUnitRunner.class )
public class EntryPointsTest {
 
    // Will begin each test on Google.
    @Resource
    @CreateUsing ( ... )
    @EntryPoint ( "http://www.google.com" )
    static Browser classScopedBrowser;
 
    // Will begin each test on Bing.
    @Resource
    @CreateUsing ( ... )
    @EntryPoint ( "http://www.bing.com" )
    Browser testScopedBrowser;
 
    ...
 
}
```

## Configuration Property Injection
All custom configuration properties can be injected into the following base field types: 
`String`, `Integer`, `Long`, `Float`, `Double` and `Boolean`.

The injection is done for all fields which are annotated with `@ConfigurationValue`.

### Example
```java
@RunWith ( WebTesterJUnitRunner.class )
public class ConfigurationValuesTest {
 
    // Injects the integer value of "customer.integer"
    @ConfigurationValue ( "custom.integer" )
    static Integer customInteger;
 
    // Injects the string value of "customer.string"
    @ConfigurationValue ( "custom.string" )
    String customString;
 
    ...
 
}
```

## Multiple Browser Instances and Configuration Property Injection
Since every `Browser` has it's own `Configuration` instance a "primary" Browser has to be declared when using multiple
`Browser` instances and the `Configuration` property injection feature. The primary browser will be the source for the
configuration properties injected by the `WebTesterJUnitRunner`. If only one browser is managed it is automatically used
as the primary browser!

**Note:** In case you want to inject property values into a static field your primary browser has to be static as well!

### Example
```java
@RunWith ( WebTesterJUnitRunner.class )
public class MultiBrowserConfigurationValuesTest {
 
    @Primary
    @Resource
    @CreateUsing ( ... )
    static Browser primaryBrowser;
 
    @Resource
    @CreateUsing ( ... )
    Browser anotherBrowser;
 
    @ConfigurationValue ( "custom.integer" )
    static Integer customInteger;
 
    @ConfigurationValue ( "custom.string" )
    String customString;
 
    ...
 
}
```

# Linked Documentation

- [Browser](browser.md)
- [Configuration](configuration.md)
