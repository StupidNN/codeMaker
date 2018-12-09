package com;

import java.io.Serializable;

public class CommonConfig implements Serializable {
    public static String entityUrl;
    public static String daoUrl;
    public static boolean entityCheckBox;
    public static boolean daoCheckBox;
    public static boolean managerCheckBox;
    public static boolean serviceCheckBox;
    public static boolean dateCheckBox;
    public static String codeSaveUrl;

    public String getEntityUrl() {
        return entityUrl;
    }

    public String getDaoUrl() {
        return daoUrl;
    }

    public boolean isEntityCheckBox() {
        return entityCheckBox;
    }

    public boolean isDaoCheckBox() {
        return daoCheckBox;
    }

    public boolean isManagerCheckBox() {
        return managerCheckBox;
    }

    public boolean isServiceCheckBox() {
        return serviceCheckBox;
    }

    public boolean isDateCheckBox() {
        return dateCheckBox;
    }

    public String getCodeSaveUrl() {
        return codeSaveUrl;
    }
}
