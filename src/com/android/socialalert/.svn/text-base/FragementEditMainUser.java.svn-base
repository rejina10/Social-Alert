package com.android.socialalert;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.common.Validate;
import com.android.socialalert.database.UsersInfo;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;
import com.android.socialalert.utilities.HideKeyboard;

public class FragementEditMainUser extends SherlockFragment implements TextWatcher {

    TextView txtName;
    TextView txtPhoneNumber;
    TextView txtEmailAddress;
    TextView txtTitlebar;

    EditText editUserFirstName;
    EditText editUserLastName;
    EditText editUserEmailAddress;
    EditText editUserPhoneNumber;

    Button btnSave;
    Button btnCancel;

    UsersInfo mainUserDetails = null;

    TableRow errorMsg;
    TextView txtErrorMsg;

    String userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_editmainuser, container, false);

        txtName = (TextView) rootView.findViewById(R.id.name);
        txtName.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_name", "Name"));

        txtEmailAddress = (TextView) rootView.findViewById(R.id.email);
        txtEmailAddress.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_email", "Email"));

        txtPhoneNumber = (TextView) rootView.findViewById(R.id.phone);
        txtPhoneNumber.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_phone", "Phone"));

        editUserFirstName = (EditText) rootView.findViewById(R.id.userFirstName);
        // editUserFirstName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintFirstName",
        // "First name"));

        editUserLastName = (EditText) rootView.findViewById(R.id.userLastName);
        // editUserLastName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintLastName",
        // "Last name"));

        editUserEmailAddress = (EditText) rootView.findViewById(R.id.user_EmailAddress);
        editUserPhoneNumber = (EditText) rootView.findViewById(R.id.phoneNumber);
        editUserPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String text = editUserPhoneNumber.getText().toString();
                if (!text.startsWith("0") && text.length() > 0) {
                    editUserPhoneNumber.setText("0" + text);
                    // editUserPhoneNumber.setP
                    Selection.setSelection(editUserPhoneNumber.getText(), 2);
                }
            }
        });

        txtErrorMsg = (TextView) rootView.findViewById(R.id.txtErrorMsg);
        errorMsg = (TableRow) rootView.findViewById(R.id.rowErrorMsg);

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_editPersonalInfo",
                "Edit personal info"));

        HideKeyboard.setupUI(rootView, getActivity());

        final View activityRootView = (rootView).findViewById(R.id.editMainUserLayout);
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

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_save", "Save"));
        btnSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    boolean validUser = getUserValidated();
                    if (validUser) {

                        if (NetworkService.hasInternetConnection(FragementEditMainUser.this.getActivity())) {

                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("LastName", editUserLastName.getText().toString().trim());
                            jsonObj.put("FirstName", editUserFirstName.getText().toString().trim());
                            jsonObj.put("PhoneNumber", editUserPhoneNumber.getText().toString().trim());
                            userInfo = jsonObj.toString();

                            EditUsersInfo editUsers = new EditUsersInfo();
                            if (editUsers.getStatus() == AsyncTask.Status.FINISHED) {
                                editUsers = new EditUsersInfo();
                            }

                            if (editUsers.getStatus() != AsyncTask.Status.RUNNING) {
                                editUsers.execute();
                            }

                        } else {
                            AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_msg_noInternetAccess", "No internet access"), FragementEditMainUser.this
                                    .getActivity());
                        }

                    } else {
                        // Toast.makeText(
                        // FragementEditMainUser.this.getActivity(),
                        // ApplicationSettings.translationSettings.GetTranslation("and_msg_userNotValid",
                        // "User not found"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "FragementEditMainUser:: btn Save click()",
                            PMWF_Log.getStringFromStackTrace(ex));
                }

            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"));
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentSettings());
                ft.commit();
            }
        });

        mainUserDetails = ApplicationSettings.dbAccessor.getMainUserInfo(ApplicationSettingsFilePath.AccountName);

        if (mainUserDetails != null) {
            editUserLastName.setText(mainUserDetails.getLastName());
            editUserFirstName.setText(mainUserDetails.getFirstName());
            editUserEmailAddress.setText(mainUserDetails.getEmailAddress());
            editUserPhoneNumber.setText(mainUserDetails.getPhoneNumber());
        }
        editUserEmailAddress.setEnabled(false);
        editUserEmailAddress.setTextColor(getResources().getColor(R.color.light_gray_header_color));

        editUserLastName.addTextChangedListener(this);
        editUserFirstName.addTextChangedListener(this);

        return rootView;
    }

    private boolean getUserValidated() {

        if (Validate.hasText(editUserFirstName)) {
            editUserFirstName.setBackgroundResource(R.drawable.edit_text_color);
            errorMsg.setVisibility(View.GONE);
            if (Validate.hasText(editUserLastName)) {
                editUserLastName.setBackgroundResource(R.drawable.edit_text_color);
                errorMsg.setVisibility(View.GONE);
                if (editUserPhoneNumber.getText().length() < 15) {
                    editUserLastName.setBackgroundResource(R.drawable.edit_text_color);
                    errorMsg.setVisibility(View.GONE);
                    return true;
                } else {
                    errorMsg.setVisibility(View.VISIBLE);
                    editUserPhoneNumber.setBackgroundResource(R.drawable.error_border);
                    txtErrorMsg.setText("");
                    txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_validation_phoneNumber", "Phone number field exceed the maximum length"));
                    editUserPhoneNumber.setFocusable(true);
                    return false;
                }
            } else {
                errorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText("");
                txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
                editUserLastName.setBackgroundResource(R.drawable.error_border);
                return false;

            }

        } else {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText("");
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
            editUserFirstName.setBackgroundResource(R.drawable.error_border);
            return false;
        }
    }

    class EditUsersInfo extends AsyncTask<String, String, String> {

        ProgressDialog editUserDialog = null;

        String editUserResponse = null;
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            editUserDialog = new ProgressDialog(FragementEditMainUser.this.getActivity());
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_editingMainUser",
                    "Editing personal information...");
            editUserDialog.setMessage(AlertMessage.setAlertHeaderColor(message, getActivity()));
            editUserDialog.setCancelable(false);
            editUserDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
            if (ApplicationSettings.isWeberviceReachable) {

                editUserResponse = WebServiceWrapper.editUser(ApplicationSettingsFilePath.AuthenticationKey, userInfo);
                if (editUserResponse != null) {
                    msg = JSONHelper.getMessageResult("EditUserResult", "Message", editUserResponse);
                    if (JSONHelper.getJsonObjectResult("EditUserResult", "IsSuccessfull", editUserResponse)) {
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

                        String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_personalInfo",
                                "Personal Info");
                        new AlertShow(FragementEditMainUser.this.getActivity())
                                .setTitle(AlertMessage.setAlertHeaderColor(title, getActivity())).setMessage(msg)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            // Intent intent =
                                            // getActivity().getIntent();
                                            // getActivity().setResult(getActivity().RESULT_OK,intent);

                                            ApplicationSettings.dbAccessor.updateUserInfo(userInfo);

                                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.content_frame, new FragmentSettings());
                                            ft.commit();

                                            // finish();
                                        } catch (Exception ex) {
                                            PMWF_Log.fnlog(PMWF_Log.ERROR, "finishApp()",
                                                    PMWF_Log.getStringFromStackTrace(ex));
                                        }
                                    }
                                }).setCancelable(false).show();
                    } else {
                        AlertMessage.Alert(msg, FragementEditMainUser.this.getActivity());
                    }

                } else {
                    AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_noInternetAccess", "No internet access."), FragementEditMainUser.this
                            .getActivity());
                }
                editUserDialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentEditMainUser: finishApp(1)",
                        PMWF_Log.getStringFromStackTrace(ex));
            }

        }

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

            if (editUserFirstName.getText().length() > 0) {

                editUserFirstName.setBackgroundResource(R.drawable.edit_text_color);
                errorMsg.setVisibility(View.GONE);
            }
            if (editUserLastName.getText().length() > 0) {

                editUserLastName.setBackgroundResource(R.drawable.edit_text_color);
                errorMsg.setVisibility(View.GONE);
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentEditMainUser: onTextChanged()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
    }
}
