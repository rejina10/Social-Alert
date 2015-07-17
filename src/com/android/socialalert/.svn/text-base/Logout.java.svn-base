package com.android.socialalert;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.android.socialalert.accountAuthenticator.AccountGeneral;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.service.AlertService;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;

public class Logout extends SherlockDialogFragment {
    private AccountManager mAccountManager;

    private String mAuthTokenType;

    public static NotificationManager notificationManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAccountManager = AccountManager.get(Logout.this.getActivity());
        mAuthTokenType = Logout.this.getActivity().getIntent().getStringExtra(AutoUpdateActivity.ARG_AUTH_TYPE);
        if (mAuthTokenType == null) {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }

        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_logOut", "Log out");

        SpannableString ss = new SpannableString(title);
        ss.setSpan(new RelativeSizeSpan(1f), 0, ss.length(), 0);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lauren)), 0, ss.length(), 0);
        // AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        AlertShow dialog = new AlertShow(getActivity());
        dialog.setTitle(ss);
        dialog.setMessage(ApplicationSettings.translationSettings.GetTranslation("and_msg_logout",
                "Are you sure you want to Log out?"));
        // null should be your on click listener
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
                    mAccountManager.removeAccount(availableAccounts[0], null, null);

                    ApplicationSettingsFilePath.AccountName = "";
                    ApplicationSettingsFilePath.AuthenticationKey = "";
                    // getActivity().stopService(HomePageActivity.serviceIntent);
                    getActivity().stopService(new Intent(getActivity(), AlertService.class));
                    ApplicationSettings.closeDBAccessor();

                    if (notificationManager != null) {
                        for (int i = 0; i < AlertService.notificationIds.size(); i++) {
                            notificationManager.cancel(AlertService.notificationIds.get(i));
                        }
                    }

                    AlertService.notificationIds.clear();

                    Intent intent = getActivity().getIntent();
                    intent.putExtra("ExitApplication", true);
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    getActivity().finish();

                    // Toast.makeText(getActivity(), isServiceRunn+"",
                    // Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "Logout: Ok click()", PMWF_Log.getStringFromStackTrace(ex));
                    if (notificationManager != null) {
                        for (int i = 0; i < AlertService.notificationIds.size(); i++) {
                            notificationManager.cancel(AlertService.notificationIds.get(i));
                        }
                    }
                    AlertService.notificationIds.clear();

                    getActivity().setResult(Activity.RESULT_CANCELED);
                    getActivity().finish();

                }
            }
        });

        dialog.setNegativeButton(ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().setTitle(HomePageActivity.titlelbl);
                        dialog.dismiss();

                    }
                });

        return dialog.create();
    }

}
