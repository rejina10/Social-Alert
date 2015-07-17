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

public class MenuListAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    String[] mTitle;
    ArrayList<String> mtitle;
    String[] mSubTitle;
    int[] mIcon;
    LayoutInflater inflater;

    public MenuListAdapter(Context context, ArrayList<String> title, String[] subtitle, int[] icon) {
        this.context = context;
        this.mtitle = title;
        this.mSubTitle = subtitle;
        this.mIcon = icon;
    }

    @Override
    public int getCount() {
        return mtitle.size();
    }

    @Override
    public Object getItem(int position) {
        return mtitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView txtTitle;
        ImageView imgIcon;

        try {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.drawer_list_item, parent, false);

            // Locate the TextViews in drawer_list_item.xml
            txtTitle = (TextView) itemView.findViewById(R.id.title);

            // Locate the ImageView in drawer_list_item.xml
            imgIcon = (ImageView) itemView.findViewById(R.id.icon);

            txtTitle.setText(mtitle.get(position));

            imgIcon.setImageResource(mIcon[position]);
            return itemView;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "MenuListAdapert", PMWF_Log.getStringFromStackTrace(ex));

        }
        return null;
    }

}
