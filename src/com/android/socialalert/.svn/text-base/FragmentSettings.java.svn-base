package com.android.socialalert;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.accountAuthenticator.AccountGeneral;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class FragmentSettings extends SherlockFragment implements OnItemClickListener {
    ListView lv;
    TextView label;
    TextView settingLayout;

    ArrayList<String> settingMenu;
    // String[] setting = new String[] { "Change password", "Change language",
    // "Shake control"};
    // ImageView imgRefresh;

    ImageView imgClientLogo;
    public static final int INTENT_RESET_PSW = 6;
    public static final int INTENT_ALERT = 9;
    public static final int INTENT_ADD_CONTACT = 10;

    private String mAuthTokenType;
    TextView settingList;
    TextView txtTitlebar;

    Button chngPassword;

    SettingListAdapter settingListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting, container, false);
        // label = (TextView) rootView.findViewById(R.id.txtSetting);
        lv = (ListView) rootView.findViewById(R.id.SettingList);
        // settingLayout = (TextView) rootView.findViewById(R.id.settingItem);
        imgClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        imgClientLogo.setVisibility(View.VISIBLE);

        ImageView titleIconExtra = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        titleIconExtra.setVisibility(View.GONE);

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_setting", "Setting"));

        // chngPassword =(Button)getActivity().findViewById(R.id.btnChgPsw);
        // chngPassword.setVisibility(View.GONE);

        mAuthTokenType = FragmentSettings.this.getActivity().getIntent()
                .getStringExtra(AutoUpdateActivity.ARG_AUTH_TYPE);
        if (mAuthTokenType == null) {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }

        try {
            settingMenu = ApplicationSettings.translationSettings.GetSettingMenuList();
            int[] icon = new int[] { R.drawable.editpersonal, R.drawable.changepassword, R.drawable.changelang,
                    R.drawable.shakecontrol };
            settingListAdapter = new SettingListAdapter(getActivity(), settingMenu, icon);
            /*
             * ArrayAdapter<String> adapter = new
             * ArrayAdapter<String>(FragmentSettings
             * .this.getActivity(),android.R.layout.simple_gallery_item,
             * setting); lv.setAdapter(adapter);
             */
            lv.setAdapter(settingListAdapter);
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSettings: Error generating Setting Menu list",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
        lv.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        String name = (String) lv.getItemAtPosition(position);

        // Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();

        if (name.equals(ApplicationSettings.translationSettings.GetTranslation("and_lbl_editPersonalInfo",
                "Edit personal information"))) {

            Fragment newFragment = new FragementEditMainUser();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this
            // fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.content_frame, newFragment, "FRAGMENT_EDIT_PERSONAL_INFO");
            // transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            /*
             * Intent loginUserIntent = new
             * Intent(FragmentSettings.this.getActivity
             * (),PasswordChangeActivity.class);
             * getActivity().startActivityForResult(loginUserIntent,
             * INTENT_RESET_PSW);
             * getActivity().overridePendingTransition(R.anim.
             * slide_in_right,R.anim.slide_out_left);
             */
        }

        else if (name.equals(ApplicationSettings.translationSettings.GetTranslation("and_lbl_changePassword",
                "Change password"))) {

            Fragment newFragment = new PasswordChangeActivity();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this
            // fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.content_frame, newFragment, "FRAGMENT_PASSWORD_CHANGE");
            // transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            /*
             * Intent loginUserIntent = new
             * Intent(FragmentSettings.this.getActivity
             * (),PasswordChangeActivity.class);
             * getActivity().startActivityForResult(loginUserIntent,
             * INTENT_RESET_PSW);
             * getActivity().overridePendingTransition(R.anim.
             * slide_in_right,R.anim.slide_out_left);
             */
        } else if (name.equals(ApplicationSettings.translationSettings.GetTranslation("and_lbl_changeLanguage",
                "Change language"))) {
            SherlockDialogFragment fragmentSelectLanguage = new FragmentSelectLanguage();
            // Fragment newFragment = new FragmentSelectLanguage();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this
            // fragment,
            // and add the transaction to the back stack

            fragmentSelectLanguage.show(getFragmentManager(), "dialog");
            Bundle args = new Bundle();
            args.putString("INSIDE_APP", "INSIDE");
            fragmentSelectLanguage.setArguments(args);
            // transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            /*
             * Intent addContactIntent = new
             * Intent(FragmentSettings.this.getActivity
             * (),ContactListActivity.class); //ContactInformation.class
             * addContactIntent.putExtra("ADD_CONTACT", true);
             * getActivity().startActivityForResult(addContactIntent,
             * INTENT_ADD_CONTACT);
             * getActivity().overridePendingTransition(R.anim
             * .slide_in_right,R.anim.slide_out_left);
             */
        } else if (name.equals(ApplicationSettings.translationSettings.GetTranslation("and_lbl_shakeControl",
                "Shake control"))) {

            Fragment newFragment = new FragmentShakeControl();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this
            // fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.content_frame, newFragment);
            // transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

            /*
             * Intent shakeIntent = new
             * Intent(FragmentSettings.this.getActivity(),ShakeControl.class);
             * getActivity().startActivity(shakeIntent);
             * getActivity().overridePendingTransition
             * (R.anim.slide_in_right,R.anim.slide_out_left);
             */

        }
    }

}
