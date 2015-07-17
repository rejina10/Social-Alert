package com.android.socialalert;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;

public class FragmentSelectLanguage extends SherlockDialogFragment {

    public static int LANGUAGE_ENGLISH = 1043;
    public static int LANGUAGE_DUTCH = 1045;
    int selectedLangauge;
    String editLangaugeResponse;

    String isCallFromMain = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        try {

            Bundle i = getArguments();

            if (i == null) {
                isCallFromMain = null;
            } else {
                isCallFromMain = i.getString("INSIDE_APP");
            }
            ArrayList<String> languageList = ApplicationSettings.translationSettings.GetLanguageList();
            ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_language_list, languageList);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        ApplicationSettings.setLanguageToSetting(LANGUAGE_ENGLISH);
                        ApplicationSettings.dbAccessor.getTranslation();
                        selectedLangauge = 1043;
                    } else {
                        ApplicationSettings.setLanguageToSetting(LANGUAGE_DUTCH);
                        ApplicationSettings.dbAccessor.getTranslation();
                        selectedLangauge = 1045;
                    }

                    try {
                        ApplicationSettingsFilePath.language = selectedLangauge;
                        FragmentMainAlert.bitmap = null;
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSelectLanguage:Creating bitmap null ",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                    /*
                     * try { if
                     * (NetworkService.hasInternetConnection(getActivity())) {
                     * editLangaugeResponse = WebServiceWrapper
                     * .EditLanguage(ApplicationSettingsFilePath
                     * .AuthenticationKey, selectedLangauge);
                     * 
                     * if (editLangaugeResponse != null) { String msg =
                     * JSONHelper.getMessageResult( "EditLanguageResult",
                     * "Message", editLangaugeResponse); if
                     * (JSONHelper.getJsonObjectResult( "EditLanguageResult",
                     * "IsSuccessfull", editLangaugeResponse)) { // return
                     * "SUCCESS"; AlertMessage
                     * .Alert(ApplicationSettings.translationSettings
                     * .GetTranslation( "and_msg_noIntern", msg),
                     * getActivity()); } else { AlertMessage
                     * .Alert(ApplicationSettings.translationSettings
                     * .GetTranslation( "and_msg_noIn",
                     * "Failed To Update to server"), getActivity());
                     * 
                     * } } else { AlertMessage.Alert(
                     * ApplicationSettings.translationSettings .GetTranslation(
                     * "and_msg_noInternetAccess", "No internet access"),
                     * getActivity());
                     * 
                     * } } else { AlertMessage.Alert(
                     * ApplicationSettings.translationSettings .GetTranslation(
                     * "and_msg_noInternetAccess", "No internet access"),
                     * getActivity()); } } catch (Exception ex) {
                     * 
                     * Log.e("Langauge changed", ex.getMessage()); }
                     */
                    if (isCallFromMain == null) {
                        getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
                        getActivity().finish();

                    } else {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new FragmentSettings());
                        ft.commit();

                        HomePageActivity.titleMenu = ApplicationSettings.translationSettings.GetMenuDrawerList();
                        HomePageActivity.mMenuAdapter = new MenuListAdapter(getActivity(), HomePageActivity.titleMenu,
                                HomePageActivity.subtitle, HomePageActivity.icon);
                        HomePageActivity.mDrawerList.setAdapter(HomePageActivity.mMenuAdapter);
                        HomePageActivity.mMenuAdapter.notifyDataSetChanged();
                        try {
                            new changeLangauge().execute();
                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR,
                                    "FragmentSelectLanguage:onCreateDialog() - changeLanguage()",
                                    PMWF_Log.getStringFromStackTrace(ex));
                        }

                    }
                }
            });

            return builder.create();

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSelectLanguage:onCreateDialog()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }

        return null;

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (isCallFromMain == null) {

            ApplicationSettings.setLanguageToSetting(LANGUAGE_DUTCH);
            ApplicationSettings.dbAccessor.getTranslation();
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();

        } else {
            dismiss();
        }

    }

    class changeLangauge extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_editingLanguage",
                    "Updating Langauge..");
            AlertMessage.setAlertHeaderColor(message, getActivity());
            dialog.setCancelable(false);
            dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getActivity()));
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (NetworkService.hasInternetConnection(getActivity())) {
                editLangaugeResponse = WebServiceWrapper.EditLanguage(ApplicationSettingsFilePath.AuthenticationKey,
                        selectedLangauge);

                if (editLangaugeResponse != null) {
                    msg = JSONHelper.getMessageResult("EditLanguageResult", "Message", editLangaugeResponse);
                    if (JSONHelper.getJsonObjectResult("EditLanguageResult", "IsSuccessfull", editLangaugeResponse)) {
                        return "FAILED"; // user already exist
                    } else {
                        return "SUCCESS";
                    }
                } else {
                    return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                            "No internet access");
                }
            } else {
                return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                        "No internet access");
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                // Context ctx =
                // FragmentSelectLanguage.this.getSherlockActivity();
                Context ctx = HomePageActivity.context;

                if (result != null && result.length() > 0) {
                    if (result.equalsIgnoreCase("FAILED")) {
                        AlertMessage.Alert(msg, ctx);
                    } else if (result.equalsIgnoreCase("SUCCESS")) {
                        AlertMessage.Alert(msg, ctx);
                    } else {
                        AlertMessage.Alert(result, ctx);
                    }
                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSelectLanguage: onPostExecute()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
        }

    }

}
