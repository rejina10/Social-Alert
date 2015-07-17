package com.android.socialalert.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TranslationSettings {

    public Map<String, String> translations = new HashMap<String, String>();

    /*
     * public void MapJsonSettings(String translationJSON){
     * 
     * //translations.put(key, value);
     * translations.put("and_lbl_registerAccount", "Geregistreerde account");
     * translations.put("and_lbl_contactInformation", "Contact informatie");
     * 
     * 
     * translations.put("and_lbl_password", "Wachtwoord");
     * translations.put("and_lbl_linkRegistered",
     * "Klik hier als niet geregistreerd");
     * translations.put("and_lbl_forgotPassword", "Klik hier als wachtwoord");
     * translations.put("and_lbl_name", "Naam");
     * translations.put("and_lbl_confirmPassword", "Herhaal wachtwoord");
     * translations.put("and_lbl_phone", "Telefoon");
     * translations.put("and_lbl_back", "Terug");
     * 
     * translations.put("and_lbl_alert", "Alarm");
     * translations.put("and_lbl_sentAlerts", "Verzonden waarschuwingen");
     * translations.put("and_lbl_receivedAlerts", "Ontvangen waarschuwingen");
     * translations.put("and_lbl_contacts", "Contacten");
     * 
     * }
     */

    public String GetTranslation(String key, String defaultValue) {
        try {
            String value = translations.get(key);
            return (value != null) ? value.trim() : defaultValue.trim();
        } catch (Exception ex) {

        } catch (Error er) {

        }
        return defaultValue;
    }

    public ArrayList<String> GetMenuDrawerList() {

        ArrayList<String> menuList = new ArrayList<String>();
        menuList.add(GetTranslation("and_lbl_alert", "Alert"));
        menuList.add(GetTranslation("and_lbl_sentAlerts", "Sent alerts"));
        menuList.add(GetTranslation("and_lbl_receivedAlerts", "Received alerts"));
        menuList.add(GetTranslation("and_lbl_contacts", "Contacts"));
        /*
         * menuList.add(GetTranslation("and_lbl_chgPassword",
         * "Change password"));
         * menuList.add(GetTranslation("and_lbl_chgLanguage",
         * "Change language"));
         */
        menuList.add(GetTranslation("and_lbl_setting", "Settings"));
        menuList.add(GetTranslation("and_lbl_logOut", "Log out"));

        return menuList;

    }

    public ArrayList<String> GetSettingMenuList() {

        ArrayList<String> settingMenuList = new ArrayList<String>();
        settingMenuList.add(GetTranslation("and_lbl_editPersonalInfo", "Edit personal information"));
        settingMenuList.add(GetTranslation("and_lbl_changePassword", "Change password"));
        settingMenuList.add(GetTranslation("and_lbl_changeLanguage", "Change language"));
        settingMenuList.add(GetTranslation("and_lbl_shakeControl", "Shake control"));

        return settingMenuList;
    }

    public ArrayList<String> GetLanguageList() {

        ArrayList<String> languageList = new ArrayList<String>();
        languageList.add(GetTranslation("and_lbl_english", "English"));
        languageList.add(GetTranslation("and_lbl_dutch", "Dutch"));
        return languageList;
    }

}
