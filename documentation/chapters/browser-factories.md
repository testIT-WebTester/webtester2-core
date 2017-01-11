[Home](../README.md)

# Browser Factories

A `BrowserFactory` creates an abstraction over the `Browser` initialization based on project-global settings.
They are intended to allow easy browser initialization and encapsulation of the underlying configuration / initialization
processes. Most projects implement their own factories according to their specific environment.
In case you just want to get started, we provide factories for the most common browsers:

- `ChromeFactory`
- `FirefoxFactory`
- `MarionetteFactory` (new Firefox driver)
- `InternetExplorerFactory`
- `RemoteFactory`

All of these are provided by the `webtester-core` module, but need you to provide the corresponding Selenium `WebDriver` dependencies yourself.

## ProxyConfiguration

In order to configure a proxy you can either configure it manually when initializing the `WebDriver` or
you can implement a `ProxyConfiguration` and provide it to the `BrowserFactory` before creating a `Browser` instance.

```java
ProxyConfiguration pc = createProxyConfiguration();
Browser browser = new FirefoxFactory().withProxyConfiguration(pc).createBrowser();
```


## ChromeFactory

This `BrowserFactory` uses the `selenium-chrome-driver` to create new Chrome `Browser` instances.

### Default Driver Configuration
In order to optimize testing the following properties are set when creating a `WebDriver` using the `ChromeFactory`:

- Native events are disabled -> Selenium does not simulate human typing.
- Untrusted certificates are always accepted.

### Additional Service Executable
The 'ChromeDriver' needs an additional executable to communicate with a Chrome browser.
It can be downloaded [here](https://sites.google.com/a/chromium.org/chromedriver/downloads).

The path to the executable must be declared as a system or environment property named: `webdriver.chrome.driver`

**Additional Information:**
- https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver


## FirefoxFactory and MarionetteFactory

These `BrowserFactory` implementations use the `selenium-firefox-driver` to create new Firefox `Browser` instances.
To drive Firefox browsers up to version 46, the `FirefoxFactory` *can* be used.
In order to drive newer versions (47++), the `MarionetteFactory` *must* be used.
The only real difference between these two factories is the activation of the `marionnette` capability.

### Default Driver Configuration
In order to optimize testing the following properties are set when creating a `WebDriver` using the `FirefoxFactory`:

- Native events are disabled -> Selenium does not simulate human typing.
- Untrusted certificates are always accepted.

### Additional Service Executable

Using the Marionette-activated `WebDriver` will force you to also specify the location of a `GeckoDriver` instance.
This is basically a proxy between Selenium and the actual Firefox (like with the `ChromeDriver`).
it can be downloaded [here](https://github.com/mozilla/geckodriver/releases)

The path to the executable must be declared as a system or environment property named: `webdriver.gecko.driver`

**Additional Information:**
- https://github.com/SeleniumHQ/selenium/wiki/FirefoxDriver
- https://github.com/mozilla/geckodriver
- https://developer.mozilla.org/en-US/docs/Mozilla/QA/Marionette


## InternetExplorerFactory

This `BrowserFactory` uses the `selenium-ie-driver` to create new Internet Explorer `Browser` instances.

### Default Driver Configuration
In order to optimize testing the following properties are set when creating a `WebDriver` using the `InternetExplorerFactory`:

- Native events are disabled -> Selenium does not simulate human typing.
- Untrusted certificates are always accepted.

### Additional Service Executable
The `InternetExplorerDriver` needs an additional executable to communicate with a IE browser.
It can be downloaded [here](http://selenium-release.storage.googleapis.com/index.html).

The path to the executable must be declared as a system or environment property named: `webdriver.ie.driver`

**Additional Information:**
- https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver

## EdgeFactory

This `BrowserFactory` uses the `selenium-edge-driver` to create new Edge `Browser` instances.

### Default Driver Configuration
In order to optimize testing the following properties are set when creating a `WebDriver` using the `EdgeFactory`:

- Native events are disabled -> Selenium does not simulate human typing.
- Untrusted certificates are always accepted.

### Additional Service Executable
The `EdgeDriver` needs an additional executable to communicate with an Edge browser.
It can be downloaded [here](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/).
Please make sure to choose the release version equal to your Windows 10 build.

The path to the executable must be declared as a system or environment property named: `webdriver.edge.driver`


## RemoteFactory

This `BrowserFactory` uses the `RemoteWebDriver` to connect to a
[Selenium Grid](https://github.com/SeleniumHQ/selenium/wiki/Grid2).

### Default Driver Configuration
In order to optimize testing the following properties are set when creating a
`WebDriver` using the `RemoteFactory`:

- Native events are disabled -> Selenium does not simulate human typing.
- Untrusted certificates are always accepted.
- Selenium Grid Host: `localhost:4444`
- Default Browser: `firefox` with Marionette activated

The connection to the Selenium Grid can be configured in two ways:

1. Set properties in configuration file (see [Configuration](configuration.md)
for a full list of properties).
2. Set system properties to override the configuration at runtime
(eg. `-Dremote.browser.name=chrome`).

# Linked Documentation

- [Browser](browser.md)
- [Configuration](configuration.md)