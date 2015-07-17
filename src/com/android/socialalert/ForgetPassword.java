package com.android.socialalert;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.common.Validate;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.WebServiceWrapper;
import com.android.socialalert.utilities.HideKeyboard;

public class ForgetPassword extends SherlockFragmentActivity implements TextWatcher {

    EditText edtTxtRecoverEmail;
    Button btnRecoverPass, btnBack;
    TableRow errorMsg;
    TextView txtErrorMsg, lblEmail;

    // private Handler handler;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_recoverPassword",
                titlebarText));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        HideKeyboard.setupUI(mCustomView, ForgetPassword.this);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.forget_password);
        initActionBar("Recover password");
        // handler = new Handler();
        txtErrorMsg = (TextView) findViewById(R.id.txtErrorMsg);
        errorMsg = (TableRow) findViewById(R.id.rowErrorMsg);

        lblEmail = (TextView) findViewById(R.id.recoverPass);
        lblEmail.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_email", "Email"));

        edtTxtRecoverEmail = (EditText) findViewById(R.id.txt_RecoverEmail);
        edtTxtRecoverEmail.addTextChangedListener(this);

        btnRecoverPass = (Button) findViewById(R.id.btnRecoverPass);
        btnRecoverPass.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_recover", "Recover"));

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_back", "Back"));

        btnRecoverPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Validate.isEmailAddress(edtTxtRecoverEmail, true)) {
                    edtTxtRecoverEmail.setBackgroundResource(R.drawable.edit_text_color);
                    errorMsg.setVisibility(View.GONE);
                    txtErrorMsg.setText("");

                    if (NetworkService.hasInternetConnection(ForgetPassword.this)) {

                        RecoverPassword recoverPass = new RecoverPassword();
                        if (recoverPass.getStatus() == AsyncTask.Status.FINISHED) {
                            recoverPass = new RecoverPassword();
                        }

                        if (recoverPass.getStatus() != AsyncTask.Status.RUNNING) {
                            recoverPass.execute();
                        }

                    } else {
                        AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                "and_msg_noInternetAccess", "No internet access."), ForgetPassword.this);
                    }
                } else {
                    errorMsg.setVisibility(View.VISIBLE);
                    txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_invalidEmailAddress", "Invalid email address"));
                    edtTxtRecoverEmail.setBackgroundResource(R.drawable.error_border);
                }
            }

        });
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra("EXIT", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        HideKeyboard.setupUI(findViewById(R.id.forgetPasswordMainLayout), ForgetPassword.this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("EXIT", false);
        setResult(RESULT_OK, intent);
        // overridePendingTransition(R.anim.slide_in_left,
        // R.anim.slide_out_right);
        finish();
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        if (s.length() > 0) {
            edtTxtRecoverEmail.setTextColor(Color.BLACK);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        try {
            if (s.length() > 0) {

                if (Validate.isEmailAddress(edtTxtRecoverEmail, true)) {
                    edtTxtRecoverEmail.setTextColor(Color.BLACK);
                    edtTxtRecoverEmail.setBackgroundResource(R.drawable.edit_text_color);
                    errorMsg.setVisibility(View.GONE);
                }
                edtTxtRecoverEmail.setTextColor(Color.BLACK);

            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Error in textWatcher OnTextChanged", e.toString());
        }

    }

    class RecoverPassword extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String forgotPswResponse = null;
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ForgetPassword.this);
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_recoveringPassword",
                    "Recovering password....");
            dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                if (ApplicationSettings.isWeberviceReachable) {

                    forgotPswResponse = WebServiceWrapper.ForgotPassword(edtTxtRecoverEmail.getText().toString());
                    if (forgotPswResponse == null) {
                        return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                                "No internet access.");
                    } else {
                        if (JSONHelper.getJsonObjectResult("ForgotPasswordResult", "IsSuccessfull", forgotPswResponse)) {
                            return "SUCCESS";
                        } else {
                            return "FAILED";
                        }
                    }
                } else {
                    return "Couldnot access to server.";
                }
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "editAsyncTask", ex.getMessage());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null && result.length() > 0) {
                    msg = JSONHelper.getMessageResult("ForgotPasswordResult", "Message", forgotPswResponse);
                    if (result.equalsIgnoreCase("SUCCESS")) {

                        String title = ApplicationSettings.translationSettings.GetTranslation(
                                "and_lbl_recoverPassword", "Recover password");
                        new AlertShow(ForgetPassword.this)
                                .setTitle(AlertMessage.setAlertHeaderColor(title, getApplicationContext()))
                                .setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = getIntent();
                                        intent.putExtra("EXIT", false);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }).setCancelable(false).show();
                    } else if (result.equalsIgnoreCase("FAILED")) {
                        AlertMessage.Alert(msg, ForgetPassword.this);
                    } else {
                        AlertMessage.Alert(result, ForgetPassword.this);
                    }

                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ForgetPassword: recoverpassword()",
                        PMWF_Log.getStringFromStackTrace(ex));
                finish();
            }
        }
    };

}
