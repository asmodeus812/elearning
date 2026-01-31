## Locale

The locale represents a specific geographical or political cultural region, an operation that requires a local to
perform its task is called locale-sensitive, and uses the Locale to tailor the information to the user based on the
information in the locale

### Properties

- `language` - the primary language
- `region` - the country or region
- `script` - specific language script
- `variant` - sub-variant or dialect

### Interface

- `Locale({lang})` - construct a locale based on the language alone, that is the primary property of the local, the
  language is a code based string that represent the language - "en", "fr" etc.
- `Local({lang}, {country})` - construct the local based on the country and the language, like for example "fr" "CA",
  the country codes unlike the language codes are usually upper case
- `Locale.Builder` - the local also exposes a builder class that can be used to construct the full components of a
  local including script, variant etc -
  `Locale.Builder().setLanguage("sr").setScript("Latn").setRegion("RS").build();` the builder unlike the locale
  constructor does validate the components of the locale we pass in to set.

    ```plaintext
    - language: 2–3 letters (ISO 639), e.g. "sr", "en", "zh"
    - script: 4 letters (ISO 15924), e.g. "Latn", "Cyrl", "Hans"
    - region: 2 letters (ISO 3166) OR 3 digits (UN M.49), e.g. "RS", "US", "419"
    - variant: 5–8 alphanum OR 4 starting with a digit, e.g. "POSIX", "1901"
    ```

    ```java
    // sample usage of the builder interface for locales, note that the builder exposes quite rich interface
    // that does not only boil down to setting these 4 primary properties, also note that the builder will internally do
    // validation on the format and structure of the locale components we set, see the rules above for reference
    Locale locale = new Locale.Builder()
            .setLanguage("hi")
            .setRegion("IN")
            .setScript("")
            .setVariant("")
            .build();
    ```

- `forLanguagTag({tag})` - parses the language tag, that includes, using things like script, region variant and so
  on, not just the basic format string of a local such as en, en_US, this is the inverse method of the `toLanguageTag`
  that obtains the string representation of a locale
- `toLanguageTag()` - converts the locale to the language tag it represents, this is actually different to the
  regular override of the Object's inherited `toString` method for the locale. Pass it to `forLanguageTag` to parse.
- `toString()` - the override of the to string method from the inherited from Object class just composes the different
  locale components and appends them together with underscores (NOT the same as `toLanguageTag`).
- `get{Language,Region,Country,Script,Variant}()` - get the different components of the locale, each of which can be
  empty, but they have a hierarchical structure, having a Country/Region implies that the language had to have been
  set

`Generally Locale during construction is not hard validated by java that means that we can technically create locales
that do not make sense, but those usages are useful, as we will see below, that is what allows us to construct locale
based on environments instead of just region/language ones.`.

The difference between the output of the string and language tag methods, mainly consists in the delimiters used
between the region and the language.

```plaintext
toString(): sr_RS_POSIX_#Latn_u-ca-gregory-nu-latn
toLanguageTag(): sr-Latn-RS-POSIX-u-ca-gregory-nu-latn
```

## Bundles

Bundles represent a localized resources that contain local specific objects, this is the assistant class to the
Locale, the resource bundles are loaded automatically form the JVM or can also be loaded manually by the developer.
The resource bundle loading logic follows a strict pattern of order. Below is the order in which the resources are
loaded into runtime from the bundles

- language_region_script_variant.properties
- language_region_script.properties
- language_region.properties
- language.properties
- default.properties

`The bundles follow a chain of child-parent relationship, meaninng that we can define just the overriding / differing
properties for your bundle and the rest will be merged and obtained from the parent or the default if no parent is
present. This works well for declaring only specific key-value pairs in the child bundles and leaving the root parent
bundles to control most of the values.`

The paragraph above should actually be pretty telling, but we can also make it obvious, that these properties files are
not only used for localization but they can actively be used to provide configuration properties for your application.
You can imagine that instead of a Locale that represents a region or a language you can have a locale that represents an
environment, like `new Locale("dev", "US"), new Locale("dev", "EU")`, and based on these you can have different
configuration properties for your application, and just read them from properties files, you might have one root file
named `config` and the environment and region specific ones under `config_dev_US, config_dev_EU`

Two types of resource bundles can be declared in Java and read by the runtime, both have their pros and cons, either
as files or classes that extend of off `ResourceBundle`

- `baseName_locale.properties` - values are strings, the keys are strings, everything is defined and declared in properties files
- `baseName_locale.class` - values can be any Object (strings, numbers, arrays, etc.) - `MyMessages_en_US` extends `ListResourceBundle`

## Properties

- `name` - the raw name for this bundle, each bundle must be tied to a unique base + locale name, the base name is
  usually common for the bundles, like "messages" and that name is followed by the "locale" part
- `locale` - the locale to which this bundle is related to, this is usually expressed as part of the name of the bundle
- `parent` - the parent for this bundle or null if no parent is present, by default the runtime will automatically
  traverse the entire hierarchy when looking up key-value up until there is no parent

The `ResourceBundle` class contains a pretty basic key-value pair store, and it can be implemented instead of
providing file resources as bundles. For example the default java runtime implements a lot of `ResourceBundles` for
error messages, currencies, numbers etc.

### Interface

```java
public abstract class ResourceBundle {
    // main abstract methods required to be overridden to provide the ability to pull information from the resource
    // bundle, classes like ListResourceBundle extend ResourceBundle and make them more user friendly/ easier to use
    protected abstract Object handleGetObject(String key);
    public abstract Enumeration<String> getKeys();
}
public abstract class ListResourceBundle extends ResourceBundle {
    // declared and exposed by the standard library, it is a helper class that allows us to easily define a
    // resource bundle, by just declaring a table of contents, where each entry is a key value pair, the value
    // can be an object, the key is usually a string. The `ResourceBundle` class exposes a method to extract the
    // value for a key. The value can be extracted as a string, object, string[] and more
}
public final class MyCustomResourceBundle extends ListResourceBundle {

    @Override
    protected final Object[][] getContents() {
        return new Object[][] {{"item.one", "default1"}, {"item.two", "default2"}, {"item.three", "default3"}};
    }
}

public final class MyCustomResourceBundle_en extends ListResourceBundle {

    @Override
    protected final Object[][] getContents() {
        return new Object[][] {{"item.one", "en_value1"}, {"item.two", "en_value2"}};
    }
}

public final class MyCustomResourceBundle_en_US extends ListResourceBundle {

    @Override
    protected final Object[][] getContents() {
        return new Object[][] {{"item.one", "en_US_value1"}};
    }
}

// to extract the bundle provide the fully qualified name to the bundle class, use the different types of locales to
// pull different instances from the runtime, in this case the ROOT means that the base resource bundle will be pulled
// the one without any locale specification.
ResourceBundle.getBundle("com.package.name.MyCustomResourceBundle", Locale.ROOT)

// follow the dollar sign notation to express that this class is a nested one, it has to be however public static one so
// the runtime can create an instance of it, this is useful in case you wish to namespace further the bundles, if the
// package is not enough to express that already
ResourceBundle.getBundle("com.package.name.ParentClassName$MyCustomResourceBundleNested", Locale.ROOT)
```

- `getBundle({baseName})` - to extract a bundle or force the runtime to look for one, provide the base name for the
  bundle, if the runtime found one either declared as a class file or a .properties file it will be returned as an
  instance of `ResourceBundle`. This method has quite a few overloads that allow you F on locale, custom class loader
  can be provided to specify from where to pull the resource.
- `getString({key})` - extract the value from the resource as a string, that is usually the default behavior the
  properties files usually contain strings as values, while the class based ones can contain any object as the base
  value for the key
- `getStringArray({key})` - obtain the value from the resource as a string[] for a given, key, it works similarly to
  the `getString` method allowing us to parse the value directly as a string array when. The array values can be comma
  separated value like `key=1,2,3,4`
- `getObject({key})` - the primary interface of the resource bundle, since every value can be Object, this is the
  primary interface that you might want to use if your resource bundle is based on a class instead.

`Bundle string value encoding for the values for bundle has to be - ISO-8859-1, that is the most basic 8 bit
character encoding it is basically standard ASCII symbols only, if Unicode characters are required then use the \u
prefix to represent Unicode characters, from java 9 onwrads the properties files can contain UTF-8 encoded values,
that means that values are no longer restricted to follow basic ASCII encoding`

## Caveats

- `toString() vs languageTag()` - the to string method uses underscores like `en_US`, while the language tags use
  hyphens to separate the language region and other local properties like `en-US`.
- `bundles encoding` - .properties are encoded with `ISO-8859-1`; non-Latin chars must be written using `\uXXXX` (UTF-8
  supported form java 9 on-wards) escapes, the keys are also case-sensitive and if a key appears twice, the last one
  wins.
- `key lookup` - when a bundle is selected, missing keys fall back through the parent chain, request in bundle "messages"
  for the fr_CA locale, from `Messages_fr_CA` a key named “key” if missing there, it will look in parent `Messages_fr`
  if missing there, then it will look in root `Messages` if missing everywhere we get `MissingResourceException`
- `bundle base name` - the base name of the bundle is the name that does not include the locale information, and the
  local information is specified using an underscore after the end of the base name e.g. - `MessagesBundle_en_US` - that
  base name here is `MessagesBundle`
- `root resource bundle` - the `getBundle` method pulls by default when no local is provided form the default locale, if
  you wish to pull the root of a bundle meaning that this is the non localized bundle resource use Locale.ROOT
- `fully qualified name` - use the full path of the bundle resource, always, that includes the package name along side
  the class name of the bundle if the bundle is actually a nested static public class use
  `com.package.name.ParentClass$ResourceBundleClass`, that is the fully qualified name of the bundle class, and it is
  required
- `mixing class & files` - it is possible to mix the resource files for bundles with class based bundles, but both HAVE
  TO follow the same naming convention otherwise either only the class or properties bundles will be loaded. Therefore
  both have to be located under the same fully qualified path, for classes that is the package name for files that is the
  folder name therefore if we have Bundle resource named Bundle they have to be both present under the same qualified path
  that path is the following: `com.package.Bundle and com/package/Bundle.properties`.
