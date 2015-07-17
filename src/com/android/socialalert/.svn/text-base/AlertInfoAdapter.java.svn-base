package com.android.socialalert;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class AlertInfoAdapter extends BaseAdapter {
    Context context;
    List<AlertInfo> item;

    // ArrayList<Item> itemList;
    public AlertInfoAdapter(Context context, List<AlertInfo> item) {
        this.context = context;
        this.item = item;
    }

    public class Holder {
        TextView nameEmail;
        TextView nameInfo;
        TextView alertType;
        CheckBox alertCheck;

    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return item.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            try {
                convertView = inflater.inflate(R.layout.alert_info_details, null);
                final Holder holder = new Holder();

                holder.nameEmail = (TextView) convertView.findViewById(R.id.nameEmail);
                holder.nameInfo = (TextView) convertView.findViewById(R.id.nameInfo);
                // holder.alertName = (TextView)
                // convertView.findViewById(R.id.alert);
                holder.alertType = (TextView) convertView.findViewById(R.id.alertType);
                holder.alertCheck = (CheckBox) convertView.findViewById(R.id.chk);

                holder.alertCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        AlertInfo alert = (AlertInfo) holder.alertCheck.getTag();
                        alert.setAlertcheck(buttonView.isChecked());
                        // item.get(position).alertcheck = isChecked;
                    }
                });

                convertView.setTag(holder);
                holder.alertCheck.setTag(item.get(position));
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "send()", PMWF_Log.getStringFromStackTrace(ex));
            }
        } else {
            ((Holder) convertView.getTag()).alertCheck.setTag(item.get(position));
        }

        Holder holder = (Holder) convertView.getTag();
        AlertInfo receiveAlertInfo = (AlertInfo) getAlertInfo(position);

        try {
            ArrayList<String> userinfo = new ArrayList<String>();
            int fromUserId = ApplicationSettings.dbAccessor.getId(receiveAlertInfo.getFromUserGuid());
            String alertType = ApplicationSettings.dbAccessor.getAlertDescription(receiveAlertInfo.getAlertType());
            userinfo = ApplicationSettings.dbAccessor.getUserName(fromUserId + "");
            holder.nameInfo.setText(userinfo.get(1) + "(" + userinfo.get(0) + ")");
            holder.nameEmail.setText(userinfo.get(2));
            holder.alertType.setText(alertType);
            holder.alertCheck.setChecked(receiveAlertInfo.isAlertcheck());
            // holder.alertCheck.setOnCheckedChangeListener(myCheckChangList);
            // holder.alertCheck.setTag(position);
            // holder.alertCheck.setChecked(receiveAlertInfo.alertcheck);
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertInfoAdapter: getView()", PMWF_Log.getStringFromStackTrace(e));
        }

        /*
         * Product getProduct(int position) { return ((Product)
         * getItem(position)); }
         */

        return convertView;
    }

    AlertInfo getAlertInfo(int position) {
        return ((AlertInfo) getItem(position));
    }

    ArrayList<AlertInfo> getAlertList() {
        ArrayList<AlertInfo> alertInfoList = new ArrayList<AlertInfo>();
        for (AlertInfo alertList : item) {
            if (alertList.alertcheck)
                alertInfoList.add(alertList);
        }
        return alertInfoList;
    }

}
