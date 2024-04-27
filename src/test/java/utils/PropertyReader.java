package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyReader {

    private static final FileInputStream fis;
    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        try {
            fis = new FileInputStream("src/test/resources/TestDataBundle.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            RESOURCE_BUNDLE = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPropertyFromBundle(String prop) {
        return RESOURCE_BUNDLE.getString(prop);
    }
}