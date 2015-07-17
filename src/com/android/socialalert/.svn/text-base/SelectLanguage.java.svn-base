package com.android.socialalert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SelectLanguage extends SherlockFragmentActivity {

    SherlockDialogFragment FragmentSelectLanguage = new FragmentSelectLanguage();

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(titlebarText);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        FragmentSelectLanguage.show(getSupportFragmentManager(), "dialog");

        initActionBar("Select Language");
        setContentView(R.layout.layout_selectlanguage);

    }

}
