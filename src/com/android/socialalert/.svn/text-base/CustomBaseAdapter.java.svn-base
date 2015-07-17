package com.android.socialalert;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.map.SenderMapLocation;
import com.android.socialalert.settings.ApplicationSettings;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<Person> item;
    String SENT = "Sent";
    String SEEN = "Seen";
    String PENDING = "Pending";
    String ACCEPTED = "Accepted";
    String REJECTED = "Rejected";

    // ArrayList<Item> itemList;
    public CustomBaseAdapter(Context context, List<Person> item) {
        this.context = context;
        this.item = item;
    }

    public class Holder {
        TextView nameDetails;
        TextView status;
        TextView alertName;
        TextView alertType;
        TextView recevingDate;
        TextView recvDate;
        TextView ackName;
        TextView ackDate;
        ImageView imgViewMap;
        ImageView imgAlertStatus;
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
        Holder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            try {
                convertView = inflater.inflate(R.layout.sentalertlist, null);
                holder = new Holder();

                holder.nameDetails = (TextView) convertView.findViewById(R.id.nameDetails);
                holder.status = (TextView) convertView.findViewById(R.id.status);
                // holder.alertName = (TextView)
                // convertView.findViewById(R.id.alert);
                holder.alertType = (TextView) convertView.findViewById(R.id.alertType);

                /*
                 * holder.recevingDate = (TextView) convertView
                 * .findViewById(R.id.receivDateName);
                 */
                holder.recvDate = (TextView) convertView.findViewById(R.id.receivDate);
                holder.imgAlertStatus = (ImageView) convertView.findViewById(R.id.ImgStatus);

                holder.imgViewMap = (ImageView) convertView.findViewById(R.id.ImgMap);
                // holder.imgViewMap.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_showLocation",
                // "Show Location"));

                holder.imgViewMap.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(ActionBarSlider.context(),
                        // "Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomePageActivity.context(), SenderMapLocation.class);
                        intent.putExtra("LATITUDE", item.get(position).getLatitude());
                        intent.putExtra("LONGITUDE", item.get(position).getLongitude());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        HomePageActivity.context().startActivity(intent);
                    }
                });

                if (item.get(position).getLatitude() == null || item.get(position).getLongitude() == null) {
                    holder.imgViewMap.setVisibility(View.GONE);
                } else if (item.get(position).getLatitude().equals("") || item.get(position).getLongitude().equals("")) {
                    holder.imgViewMap.setVisibility(View.GONE);
                }

                convertView.setTag(holder);
            } catch (Exception e) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "Error in convertView", e.toString());
            }
        } else {
            holder = (Holder) convertView.getTag();
        }

        Person sendAlertInfo = (Person) getItem(position);

        if (sendAlertInfo.getContactFirstName().toString().length() > 0) {
            holder.nameDetails.setText(sendAlertInfo.getContactLastName() + "("
                    + sendAlertInfo.getContactFirstName().toString() + ")");
        } else {
            holder.nameDetails.setText(sendAlertInfo.getContactLastName());
        }

        String status = sendAlertInfo.getAlertStatus();
        if (status.equals(PENDING)) {
            String pending = ApplicationSettings.translationSettings.GetTranslation("and_lbl_pending", "Pending");
            holder.status.setText(pending);
        } else if (status.equals(SENT)) {
            String sent = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertSentStatus", "Sent");

            holder.status.setText(sent);
        } else if (status.equals(SEEN)) {
            String seen = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertSeenStatus", "Seen");
            holder.status.setText(seen);
            holder.imgAlertStatus.setImageResource(R.drawable.offline);
        } else if (status.equals(ACCEPTED)) {
            String accepted = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertAcceptedStatus",
                    "Accepted");

            holder.status.setText(accepted);
            holder.imgAlertStatus.setImageResource(R.drawable.online);
        } else if (status.equals(REJECTED)) {
            String rejected = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertRejectedStatus",
                    "Rejected");
            holder.status.setText(rejected);
            holder.imgAlertStatus.setImageResource(R.drawable.inactive);
        }
        int userStatus = sendAlertInfo.getUserStatus();
        if (userStatus == 2 && status.equals(SENT)) {
            holder.imgAlertStatus.setImageResource(R.drawable.inactive);
        }
        holder.alertType.setText(sendAlertInfo.getContactAlertType());
        holder.recvDate.setText(sendAlertInfo.getAlertSendDateTime());

        /*
         * if(sendAlertInfo.getAlertStatus().equalsIgnoreCase("pending")) {
         * holder.imgAlertStatus.setImageResource(R.drawable.alert_pending); }
         * else if(sendAlertInfo.getAlertStatus().equalsIgnoreCase("sent")) {
         * 
         * holder.imgAlertStatus.setImageResource(R.drawable.sent); } else
         * if(sendAlertInfo.getAlertStatus().equalsIgnoreCase("rejected")) {
         * 
         * holder.imgAlertStatus.setImageResource(R.drawable.reject); }
         */

        return convertView;
    }

}
