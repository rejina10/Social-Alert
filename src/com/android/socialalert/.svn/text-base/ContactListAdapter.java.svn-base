package com.android.socialalert;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.socialalert.common.NetworkService;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class ContactListAdapter extends ArrayAdapter<ContactInfo> {

    Context context;
    int layoutResourceId;
    private ArrayList<ContactInfo> data;

    public ContactListAdapter(Context context, int LayoutResourceId, ArrayList<ContactInfo> data) {
        super(context, LayoutResourceId, data);

        this.context = context;
        this.data = data;
        this.layoutResourceId = LayoutResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        try {

            ContactInfo object = data.get(position);

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                row = inflater.inflate(R.layout.contactlist_rowlayout, null, false);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.contactName = (TextView) row.findViewById(R.id.contactName);
                viewHolder.contactEmailAddress = (TextView) row.findViewById(R.id.emailAddress);
                viewHolder.contactType = (TextView) row.findViewById(R.id.contactType);
                viewHolder.status = (TextView) row.findViewById(R.id.status);
                viewHolder.contactAlert = (TextView) row.findViewById(R.id.contactAlert);
                // viewHolder.userStatus = (TextView)
                // row.findViewById(R.id.contactStatus);
                viewHolder.imgStatus = (ImageView) row.findViewById(R.id.imgUserStatus);

                row.setTag(viewHolder);

            } else {
                row = convertView;
            }

            ViewHolder holder = (ViewHolder) row.getTag();

            String firstname = object.getContactFirstName();
            String lastName = object.getContactLastName();
            String emailAddress = object.getContactEmailAddressName();
            String contactType = object.getContactType();
            String status = object.getUserIsActive();
            int userStatus = object.getUserStatus();

            if (firstname.length() > 0 && lastName.length() > 0) {
                holder.contactName.setText(lastName + "(" + firstname + ")");
            } else {
                holder.contactName.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_friend",
                        "Friend"));
            }

            holder.contactEmailAddress.setText(emailAddress);
            holder.contactType.setText(contactType);
            holder.status.setText(status);

            // if (contactType.equalsIgnoreCase("Sender")) {
            // ArrayList<String> alertInfo = ApplicationSettings.dbAccessor
            // .getEditAlertInfo(emailAddress, contactType);
            // String allAlerts = "";
            // for (int i = 0; i < alertInfo.size(); i++) {
            // allAlerts +=
            // ApplicationSettings.dbAccessor.getAlertDescription(Integer.valueOf(alertInfo.get(i)));
            // if (i < alertInfo.size() - 1) {
            // allAlerts += ",";
            // }
            // }
            // holder.contactAlert.setText("(" + allAlerts + ")");
            // }
            //
            // if (contactType.equalsIgnoreCase("Receiver")) {
            if (contactType != null && contactType.length() > 0) {
                ArrayList<String> alertInfo = ApplicationSettings.dbAccessor
                        .getEditAlertInfo(emailAddress, contactType);
                String allAlerts = "";
                for (int i = 0; i < alertInfo.size(); i++) {
                    allAlerts += ApplicationSettings.dbAccessor.getAlertDescription(Integer.valueOf(alertInfo.get(i)));
                    if (i < alertInfo.size() - 1) {
                        allAlerts += ",";
                    }
                }
                holder.contactAlert.setText("(" + allAlerts + ")");
                if (contactType.equalsIgnoreCase("Sender")) {
                    holder.contactType.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_sender",
                            "Sender"));
                } else {
                    holder.contactType.setText(ApplicationSettings.translationSettings.GetTranslation(
                            "and_lbl_receiver", "Receiver"));
                }
            }

            if (userStatus > 0 && NetworkService.hasInternetConnection(HomePageActivity.context)) {
                // String userStatusDescription =
                // ApplicationSettings.dbAccessor.getUserStatusDescription(userStatus);
                // holder.userStatus.setText(userStatusDescription);
                if (userStatus == 1) {
                    holder.imgStatus.setImageResource(R.drawable.online);
                } else if (userStatus == 2) {
                    holder.imgStatus.setImageResource(R.drawable.offline);
                } else if (userStatus == 5) {
                    holder.imgStatus.setImageResource(R.drawable.inactive);

                }
            } else {
                // holder.userStatus.setText("");
                holder.imgStatus.setVisibility(View.GONE);
            }
            // return super.getView(position, convertView, parent);
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactListAdapter: getView()", PMWF_Log.getStringFromStackTrace(ex));
        }
        return row;
    }

    class ViewHolder {
        TextView contactName;
        TextView contactEmailAddress;
        TextView status;
        TextView contactType;
        TextView contactAlert;
        // TextView userStatus;
        ImageView imgStatus;

    }
}
