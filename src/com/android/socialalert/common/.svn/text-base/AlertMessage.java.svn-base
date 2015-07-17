package com.android.socialalert.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.android.socialalert.AlertShow;
import com.android.socialalert.HomePageActivity;
import com.android.socialalert.R;
import com.android.socialalert.settings.ApplicationSettings;

public class AlertMessage {
    static boolean isExtraContact;
    private static final String lauren = "#E82D22";

    public static void Alert(String alertMessage, final Context cxt) {
        //String title = "Alert";
        SpannableString ss1 = new SpannableString(ApplicationSettings.translationSettings.GetTranslation(
                "and_lbl_alert", "Alert"));
        ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
        ss1.setSpan(new ForegroundColorSpan(cxt.getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);
        // AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        AlertShow builder = new AlertShow(cxt);
        builder.setCancelable(false);
        builder.setTitle(ss1);
        builder.setDividerColor(lauren);
        builder.setMessage(alertMessage);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // DO nothing

            }
        });
        builder.show();
    }

    public static void AlertAndReturnToMain(String alertMessage, final Context cxt) {
       // String title = "Alert";
        SpannableString ss1 = new SpannableString(ApplicationSettings.translationSettings.GetTranslation(
                "and_lbl_alert", "Alert"));
        ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
        ss1.setSpan(new ForegroundColorSpan(cxt.getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);
        AlertShow builder = new AlertShow(cxt);
        builder.setCancelable(false);
        builder.setTitle(ss1);
        builder.setMessage(alertMessage);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Intent intent = new Intent(cxt, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                cxt.startActivity(intent);

            }
        });
        builder.show();
    }

    public static SpannableString setAlertHeaderColor(String title, final Context cxt) {

        SpannableString ss1 = new SpannableString(title);
        ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
        ss1.setSpan(new ForegroundColorSpan(cxt.getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);

        return ss1;
    }
}
