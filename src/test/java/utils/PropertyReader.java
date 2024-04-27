package utils;

import java.util.ResourceBundle;

public class PropertyReader {


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("LicensesBundle");

    public static String getTestDataFromBundle(String prop) {
        return resourceBundle.getString(prop);
    }
}