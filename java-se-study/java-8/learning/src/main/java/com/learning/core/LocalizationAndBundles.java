package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationAndBundles {

    private static final String RESOURCE_BUNDLE_NON_NESTED = "com.learning.core.MyCustomResourceBundle";
    private static final String RESOURCE_BUNDLE_NESTED = "com.learning.core.LocalizationAndBundles$MyCustomResourceBundle";

    private static final String ITEM_ONE_BUNDLE_KEY = "item.one";
    private static final String ITEM_TWO_BUNDLE_KEY = "item.two";
    private static final String ITEM_THREE_BUNDLE_KEY = "item.three";

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(LocalizationAndBundles.class);

    public static void main(String[] args) {
        // ensure that the logger is first ocnfigured to print abridged message we do this to ensure that the logger format string deso not
        // pollute the stdout with needless noise whenprinting to stdout.
        InstanceMessageLogger.configureLogger(InstanceMessageLogger.class.getResourceAsStream("/logger.properties"));

        Locale frenchCanadianLocale = new Locale("fr", "CA");
        Locale superHeavyLocale = new Locale.Builder().setLanguage("sr") // Serbian
                        .setScript("Latn") // Latin script (common alternative: "Cyrl")
                        .setRegion("RS") // Serbia
                        .setVariant("POSIX") // a valid variant token
                        .setExtension('u', "ca-gregory-nu-latn") // Unicode locale extension:
                                                                 // calendar=gregory, number system=latn
                        .build();

        LOGGER.logInfo("toString(): " + superHeavyLocale.toString());
        LOGGER.logInfo("toLanguageTag(): " + superHeavyLocale.toLanguageTag());

        try {
            // we expect this to throw actually, there is no bundle with that specific locale present, that is important to
            // note because the bundle resources can not be pulled if it does not exist, the runtime will not try to pull
            // chain/parent resolve a bundle that actually exist
            ResourceBundle.getBundle("ErrorMessages", frenchCanadianLocale);
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }

        // this we expect to work, there is our custom bundle without locale and the one that is from the default locale, which is usually
        // en_US, take a good note at how we access our bundle, we have so specify the full path that includes the package name of our
        // bundle, and since our bundle is a nested class, within another class it has to be declared as public static and be accessible, so
        // the runtime can actually create an instance of it. We use the dollar sign notation to reference the class that is nested
        ResourceBundle bundleRoot = ResourceBundle.getBundle(RESOURCE_BUNDLE_NESTED, Locale.ROOT);
        ResourceBundle bundleEnglish = ResourceBundle.getBundle(RESOURCE_BUNDLE_NESTED, Locale.ENGLISH);
        ResourceBundle bundleEnglishUs = ResourceBundle.getBundle(RESOURCE_BUNDLE_NESTED, new Locale("en", "US"));

        // now take a good note at what we are donig here, we are pulling the keys from the three budnles, each of which has the key one
        // defined in there, and we willl see the value of each one for the specific bundle
        LOGGER.logInfo("Root, (class) getString({one}): " + bundleRoot.getString(ITEM_ONE_BUNDLE_KEY));
        LOGGER.logInfo("English (class) getString({one}): " + bundleEnglish.getString(ITEM_ONE_BUNDLE_KEY));
        LOGGER.logInfo("English-US (class) getString({one}): " + bundleEnglishUs.getString(ITEM_ONE_BUNDLE_KEY));

        // this is here demonstrates, how the keys and values are inherited in the hierarchy, meaning that, even though our bundleEnglishUs
        // does not contain neither the key two or three, they will be pulled form its parent bundles which are the bundleEnglish and the
        // bundleRoot
        LOGGER.logInfo("English-US (class) getString({two}): " + bundleEnglishUs.getString(ITEM_TWO_BUNDLE_KEY));
        LOGGER.logInfo("English-US (class) getString({three}): " + bundleEnglishUs.getString(ITEM_THREE_BUNDLE_KEY));

        // in the resources we have a properties file named MyCustomResourceBundle, that one is exactly under
        // src/main/resources/com/learning/core/MyCustomResourceBundle.properties, we also have one bundle file in the smae location that is
        // named with the nested class notation using the dollar sign notation, that one will be also loaded together with the class based
        // ones it is located under - src/main/resources/com/learning/core/LocalizationAndBundles$MyCustomResourceBundle.properties
        ResourceBundle bundleRootFromFile = ResourceBundle.getBundle(RESOURCE_BUNDLE_NON_NESTED, Locale.ROOT);
        ResourceBundle bundleRootFromFileNested = ResourceBundle.getBundle(RESOURCE_BUNDLE_NESTED, Locale.ROOT);

        // the first print here will actually show that the non-nested properties file even though it shares the same name as our bundle has
        // nothing to do with the bundle itself, it is treated as a completely different bundle resources, the next two log statements show
        // that we can have the nested bundle represented as a file, as well as the classes that we have in this case the class declaration
        // of the keys will shadow the ones in the files, in other words the classes take precedence over the files, when reading values.
        LOGGER.logInfo("Root (file, not nested), getString({one}): " + bundleRootFromFile.getString(ITEM_ONE_BUNDLE_KEY));
        LOGGER.logInfo("Root (file, nested, shadowed), getString({one}): " + bundleRootFromFileNested.getString(ITEM_ONE_BUNDLE_KEY));
        LOGGER.logInfo("Root (file, nested, from-file), getString({one-from-file}): "
                        + bundleRootFromFileNested.getString(ITEM_ONE_BUNDLE_KEY + "-from-file"));
    }

    public static final class MyCustomResourceBundle extends ListResourceBundle {

        @Override
        protected final Object[][] getContents() {
            return new Object[][] {{ITEM_ONE_BUNDLE_KEY, "default1",}, {ITEM_TWO_BUNDLE_KEY, "default2"},
                            {ITEM_THREE_BUNDLE_KEY, "default3"}};
        }
    }

    public static final class MyCustomResourceBundle_en extends ListResourceBundle {

        @Override
        protected final Object[][] getContents() {
            return new Object[][] {{ITEM_ONE_BUNDLE_KEY, "en_value1",}, {ITEM_TWO_BUNDLE_KEY, "en_value2"}};
        }
    }

    public static final class MyCustomResourceBundle_en_US extends ListResourceBundle {

        @Override
        protected final Object[][] getContents() {
            return new Object[][] {{ITEM_ONE_BUNDLE_KEY, "en_US_value1"}};
        }
    }
}
