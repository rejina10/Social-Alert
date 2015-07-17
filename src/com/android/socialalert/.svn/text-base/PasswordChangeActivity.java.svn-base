package com.android.socialalert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.common.Validate;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;
import com.android.socialalert.utilities.HideKeyboard;

public class PasswordChangeActivity extends SherlockFragment implements OnItemClickListener, TextWatcher {

    EditText editNewPsw;
    EditText editConfirmPsw;
    EditText editEmailAddress;
    EditText editOldPsw;

    Button btnChgPassword;
    Button btnCancel;
    // ImageView imgClientLogo;
    ImageView imgAddContact;
    TextView txtTitlebar;
    TableRow errorMsg;
    TextView txtErrorMsg;
    EditText[] editText;

    // Button chngPassword;

    TextView lblOldPsw, lblNewPsw, lblConfirmPsw;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_pswchange, container, false);
        // chngPassword = (Button) getActivity().findViewById(R.id.btnChgPsw);
        txtErrorMsg = (TextView) rootView.findViewById(R.id.txtErrorMsg);
        errorMsg = (TableRow) rootView.findViewById(R.id.rowErrorMsg);
        editOldPsw = (EditText) rootView.findViewById(R.id.oldPsw);
        editNewPsw = (EditText) rootView.findViewById(R.id.newPsw);
        editConfirmPsw = (EditText) rootView.findViewById(R.id.confirmPsw);
        btnChgPassword = (Button) rootView.findViewById(R.id.btnChgPassword);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        // imgClientLogo = (ImageView)
        // getActivity().findViewById(R.id.title_imgview_clientlogo);
        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        lblOldPsw = (TextView) rootView.findViewById(R.id.lblOldPsw);
        lblNewPsw = (TextView) rootView.findViewById(R.id.lblNewPsw);
        lblConfirmPsw = (TextView) rootView.findViewById(R.id.lblConfirmNewPsw);

        editOldPsw.addTextChangedListener(this);
        editNewPsw.addTextChangedListener(this);
        // chngPassword.setVisibility(View.VISIBLE);
        // imgClientLogo.setImageResource(R.drawable.client_logo);

        ImageView titleImageViewClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        titleImageViewClientLogo.setVisibility(View.GONE);

        ImageView titleIconExtra = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        titleIconExtra.setVisibility(View.GONE);

        HideKeyboard.setupUI(rootView, getActivity());

        final View activityRootView = (rootView).findViewById(R.id.passwordChangeLayout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                // r will be populated with the coordinates of your view
                // that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its
                                        // probably a keyboard...
                    // Toast.makeText(getApplicationContext(),
                    // "keyboard might be", Toast.LENGTH_SHORT).show();
                    (activityRootView).findViewById(R.id.bottomMenus).setVisibility(View.GONE);
                } else {
                    (activityRootView).findViewById(R.id.bottomMenus).setVisibility(View.VISIBLE);
                }
            }
        });

        editConfirmPsw.addTextChangedListener(this);

        btnChgPassword.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_save", "Save"));

        btnCancel.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"));

        // imgClientLogo.setVisibility(View.GONE);

        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_changePassword",
                "Change password"));

        editText = new EditText[3];
        editText[0] = editOldPsw;
        editText[1] = editNewPsw;
        editText[2] = editConfirmPsw;

        lblOldPsw
                .setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_oldPassword", "Old password"));

        lblNewPsw
                .setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_newPassword", "New password"));

        lblConfirmPsw.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_confirmPassword",
                "Confirm password"));

        setUpBtnListener();
        return rootView;
    }

    private void setUpBtnListener() {
        btnChgPassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isValidate = Validate();
                try {
                    if (isValidate) {

                        if (NetworkService.hasInternetConnection(PasswordChangeActivity.this.getActivity())) {

                            ChangePassword chngPass = new ChangePassword();
                            if (chngPass.getStatus() == AsyncTask.Status.FINISHED) {
                                chngPass = new ChangePassword();
                            }

                            if (chngPass.getStatus() != AsyncTask.Status.RUNNING) {
                                chngPass.execute();
                            }

                        } else {
                            AlertMessage.Alert("No internet access", PasswordChangeActivity.this.getActivity());
                        }

                    }
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "PasswordChangeActivity:: changePassword click()",
                            PMWF_Log.getStringFromStackTrace(ex));
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                editOldPsw.setText("");
                editNewPsw.setText("");
                editConfirmPsw.setText("");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentSettings());
                ft.commit();
            }
        });

    }

    class ChangePassword extends AsyncTask<String, String, String> {

        ProgressDialog chgPswDialog = null;

        String pswResetResponse = null;
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chgPswDialog = new ProgressDialog(PasswordChangeActivity.this.getActivity());
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_changingPassword",
                    "Changing password...");
            chgPswDialog.setMessage(AlertMessage.setAlertHeaderColor(message, getActivity()));
            chgPswDialog.setCancelable(false);
            chgPswDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
            if (ApplicationSettings.isWeberviceReachable) {
                pswResetResponse = WebServiceWrapper.ResetPassword(ApplicationSettingsFilePath.AuthenticationKey,
                        ApplicationSettingsFilePath.AccountName, editNewPsw.getText().toString(), editOldPsw.getText()
                                .toString());
                if (pswResetResponse != null) {
                    msg = JSONHelper.getMessageResult("ResetPasswordResult", "Message", pswResetResponse);
                    if (JSONHelper.getJsonObjectResult("ResetPasswordResult", "IsSuccessfull", pswResetResponse)) {
                        return "SUCCESS";
                    } else {
                        return msg;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null && result.length() > 0) {

                    if (result.equalsIgnoreCase("SUCCESS")) {

                        String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_changePassword",
                                "Change Password");
                        new AlertShow(PasswordChangeActivity.this.getActivity())
                                .setTitle(AlertMessage.setAlertHeaderColor(title, getActivity())).setMessage(msg)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            Intent intent = getActivity().getIntent();
                                            getActivity().setResult(Activity.RESULT_OK, intent);
                                            editOldPsw.setText("");
                                            editNewPsw.setText("");
                                            editConfirmPsw.setText("");
                                            final android.support.v4.app.FragmentTransaction ft = getFragmentManager()
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, new FragmentMainAlert());
                                            ft.commit();

                                            // finish();
                                        } catch (Exception ex) {
                                            PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                    "PasswordChangeActivity: ChangePassword onPostExecute()",
                                                    PMWF_Log.getStringFromStackTrace(ex));
                                        }
                                    }
                                }).setCancelable(false).show();
                    } else {
                        AlertMessage.Alert(msg, PasswordChangeActivity.this.getActivity());
                    }

                } else {
                    AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_noInternetAccess", "No internet access."), PasswordChangeActivity.this
                            .getActivity());
                }
                chgPswDialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "PasswordChangeActivity: ChangePassword onPostExecute(1)",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
        }

    };

    protected boolean Validate() {
        String toastMessage = "";
        boolean passwordValid = true;
        for (int i = 0; i < editText.length; i++) {
            if (Validate.hasText(editText[i])) {
                errorMsg.setVisibility(View.GONE);
                editText[i].setBackgroundResource(R.drawable.edit_text_color);
                // txtErrorMsg.setText("");

            } else {
                errorMsg.setVisibility(View.VISIBLE);
                editText[i].setBackgroundResource(R.drawable.error_border);
                txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
                passwordValid = false;
            }
        }

        if (Validate.hasText(editNewPsw) && editNewPsw.getText().length() < 6) {
            editNewPsw.setBackgroundResource(R.drawable.error_border);
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_password_length",
                    "Password should be of minimum 6 characters."));
            editNewPsw.requestFocus();
            passwordValid = false;

        } else if (Validate.hasText(editOldPsw) && Validate.hasText(editNewPsw) && Validate.hasText(editConfirmPsw)) {
            if (Validate.matchPassword(editNewPsw, editConfirmPsw)) {
                editConfirmPsw.setBackgroundResource(R.drawable.edit_text_color);
                editNewPsw.setBackgroundResource(R.drawable.edit_text_color);
                errorMsg.setVisibility(View.GONE);
            } else {
                editConfirmPsw.setBackgroundResource(R.drawable.error_border);
                errorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_passwordDoesntMatch", "Password doesnot match"));
                editConfirmPsw.requestFocus();
                passwordValid = false;
            }
        } else {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
        }

        // if (Validate.hasText(editOldPsw)) {
        // if (Validate.hasText(editNewPsw)) {
        // if (Validate.hasText(editConfirmPsw)) {
        // if (Validate.matchPassword(editNewPsw, editConfirmPsw)) {
        // errorMsg.setVisibility(View.GONE);
        // editConfirmPsw
        // .setBackgroundResource(R.drawable.edit_text_color);
        // editNewPsw
        // .setBackgroundResource(R.drawable.edit_text_color);
        // editOldPsw
        // .setBackgroundResource(R.drawable.edit_text_color);
        // txtErrorMsg.setText("");
        // return true;
        //
        // } else {
        // // toastMessage = "Password doesn't match.";
        // errorMsg.setVisibility(View.VISIBLE);
        // editConfirmPsw
        // .setBackgroundResource(R.drawable.error_border);
        // txtErrorMsg.setText("Password doesn't match");
        // editConfirmPsw.requestFocus();
        //
        // }
        // } else {
        // errorMsg.setVisibility(View.VISIBLE);
        // editConfirmPsw
        // .setBackgroundResource(R.drawable.error_border);
        // txtErrorMsg.setText("Empty Confirm Password");
        // // toastMessage = "Empty Confirm Password.";
        // editConfirmPsw.requestFocus();
        // }
        // } else {
        // errorMsg.setVisibility(View.VISIBLE);
        // editConfirmPsw.setBackgroundResource(R.drawable.error_border);
        // txtErrorMsg.setText("Empty Password");
        // // toastMessage = "Empty Password.";
        // editNewPsw.requestFocus();
        //
        // }
        // } else {
        // errorMsg.setVisibility(View.VISIBLE);
        // txtErrorMsg.setText("Incorrect Password");
        // // toastMessage = "Incorrect Password.";
        // editOldPsw.requestFocus();
        // }

        if (passwordValid) {
            txtErrorMsg.setText("");
        }
        if (toastMessage != "") {
            AlertMessage.Alert(toastMessage, PasswordChangeActivity.this.getActivity());
        }
        return passwordValid;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            if (s.length() > 0) {
                if (editConfirmPsw.getText().length() > 0) {
                    editConfirmPsw.setBackgroundResource(R.drawable.edit_text_color);
                    editConfirmPsw.setTextColor(Color.BLACK);
                }
                if (editNewPsw.getText().length() > 0) {
                    editNewPsw.setBackgroundResource(R.drawable.edit_text_color);
                    editNewPsw.setTextColor(Color.BLACK);
                }
                if (editOldPsw.getText().length() > 0) {
                    editOldPsw.setBackgroundResource(R.drawable.edit_text_color);
                    editOldPsw.setTextColor(Color.BLACK);

                }

                if (Validate.matchPassword(editNewPsw, editConfirmPsw)) {
                    errorMsg.setVisibility(View.GONE);
                    editConfirmPsw.setBackgroundResource(R.drawable.edit_text_color);
                    editNewPsw.setBackgroundResource(R.drawable.edit_text_color);

                    txtErrorMsg.setText("");
                }

            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Error in textWatcher OnTextChanged", e.toString());
        }
    }

}
