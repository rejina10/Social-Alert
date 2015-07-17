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

public class RecievedAlertAdapter extends BaseAdapter {
    Context context;
    List<Person> item;
    String ACCEPTED = "Accepted";
    String REJECTED = "Rejected";
    String RECEIVED = "Received";

    // ArrayList<Item> itemList;
    public RecievedAlertAdapter(Context context, List<Person> item) {
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
        try {
            Holder holder = null;
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.receive_alerts, null);
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

                /*
                 * try{ if (item.get(position).getLatitude().equals("") ||
                 * item.get(position).getLongitude().equals("")) {
                 * holder.imgViewMap.setVisibility(View.GONE); }
                 * }catch(NullPointerException ne){ ne.printStackTrace(); }
                 */

                holder.imgViewMap = (ImageView) convertView.findViewById(R.id.ImgMap);
                /*
                 * holder.imgViewMap
                 * .setText(ApplicationSettings.translationSettings
                 * .GetTranslation("and_lbl_showLocation", "Show Location"));
                 */
                holder.imgViewMap.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // Toast.makeText(ActionBarSlider.context(),
                        // "Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomePageActivity.context(), SenderMapLocation.class);
                        intent.putExtra("LATITUDE", item.get(position).getLatitude());
                        intent.putExtra("LONGITUDE", item.get(position).getLongitude());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        HomePageActivity.context().startActivity(intent);
                    }
                });
                /*
                 * holder.ackName = (TextView) convertView
                 * .findViewById(R.id.ackDateName); holder.ackDate = (TextView)
                 * convertView.findViewById(R.id.ackDate);
                 */

                // holder.ItemsTxtDetails=(TextView)convertView.findViewById(R.id.title);

                if (item.get(position).getLatitude() == null || item.get(position).getLongitude() == null) {
                    holder.imgViewMap.setVisibility(View.GONE);
                } else if (item.get(position).getLatitude().equals("") || item.get(position).getLongitude().equals("")) {
                    holder.imgViewMap.setVisibility(View.GONE);
                }

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }

            // String itmStr=(String)getItem(position);
            Person rcvAlertInfo = (Person) getItem(position);
            // int img=cat.getImageResources();
            // Item item=(Item)getItem(position);
            if (rcvAlertInfo.getContactFirstName().toString().length() > 0) {
                holder.nameDetails.setText(rcvAlertInfo.getContactLastName() + "("
                        + rcvAlertInfo.getContactFirstName().toString() + ")");
            } else {
                holder.nameDetails.setText(rcvAlertInfo.getContactLastName());
            }
            // holder.nameDetails.setText(rcvAlertInfo.getContactFirstName());
            // holder.status.setImageResource(img);
            String status = rcvAlertInfo.getAlertStatus();
            if (status.equals(ACCEPTED)) {
                String accepted = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertAcceptedStatus",
                        "Accepted");

                holder.status.setText(accepted);
                holder.imgAlertStatus.setImageResource(R.drawable.online);
            } else if (status.equals(REJECTED)) {
                String rejected = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertRejectedStatus",
                        "Rejected");

                holder.status.setText(rejected);
                holder.imgAlertStatus.setImageResource(R.drawable.inactive);
            } else if (status.equals(RECEIVED)) {
                String received = ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertReceivedStatus",
                        "Received");
                holder.imgAlertStatus.setImageResource(R.drawable.offline);
                holder.status.setText(received);
            }
            holder.alertType.setText(rcvAlertInfo.getContactAlertType());
            holder.recvDate.setText(rcvAlertInfo.getAlertSendDateTime());
            // holder.ackDate.setText(cat.getAlertSendDateTime());

            // holder.ItemsTxtDetails.setText(item.getName()+"\n"+item.getDescription());
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ReceivedAlertAdapter: getView()", PMWF_Log.getStringFromStackTrace(e));
        }
        return convertView;
    }

}
