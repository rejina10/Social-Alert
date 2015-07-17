package com.android.socialalert;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.socialalert.logger.PMWF_Log;

public class SettingListAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    ArrayList<String> sTitle;
    // String[] sTitle;

    // String[] mSubTitle;
    int[] mIcon;
    LayoutInflater inflater;

    public SettingListAdapter(Context context, ArrayList<String> title, int[] icon) {
        this.context = context;
        this.sTitle = title;
        this.mIcon = icon;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return sTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return sTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup parent) {
        TextView txtTitle;
        ImageView settingImage;

        try {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.drawer_list_item, parent, false);

            // Locate the TextViews in drawer_list_item.xml
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            settingImage = (ImageView) itemView.findViewById(R.id.icon);
            txtTitle.setText(sTitle.get(position));
            settingImage.setImageResource(mIcon[position]);

            return itemView;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "MenuListAdapert", ex.getMessage());

        }
        return null;
    }

}
