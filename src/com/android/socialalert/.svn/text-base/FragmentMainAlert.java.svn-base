package com.android.socialalert;

import java.io.FileNotFoundException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;

public class FragmentMainAlert extends SherlockFragment {

    ImageView titleImageClientLogo;
    TextView txtTitlebar;

    TextView imgAlertDesc;
    public static Bitmap bitmap = null;

    // ArrayList<ContactInfo> contactList = new ArrayList<ContactInfo>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alert, container, false);
        titleImageClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        titleImageClientLogo.setVisibility(View.VISIBLE);

        ImageView titleIconExtra = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        titleIconExtra.setVisibility(View.GONE);

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alert", "Alert"));
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        /*
         * int newHeight =
         * getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
         * int orgWidth = image.getDrawable().getIntrinsicWidth(); int orgHeight
         * = image.getDrawable().getIntrinsicHeight();
         * 
         * //double check my math, this should be right, though int newWidth =
         * Math.floor((orgWidth * newHeight) / orgHeight);
         */

        // int fromUserId =
        // ApplicationSettings.dbAccessor.getUserId(ApplicationSettingsFilePath.AccountName);
        int contactCount = ApplicationSettings.dbAccessor.getAllContactCount();
        // contactList =
        // ApplicationSettings.dbAccessor.getContactsList(fromUserId);
        if (contactCount > 0) {
            ImageView alertViewAlertButton = (ImageView) rootView.findViewById(R.id.alertButton);
            // alertViewAlertButton.setBackgroundResource(R.drawable.imgalert);

            imgAlertDesc = (TextView) rootView.findViewById(R.id.alertMessage);
            imgAlertDesc
                    .setText(ApplicationSettings.translationSettings
                            .GetTranslation(
                                    "and_msg_imgAlertInfo",
                                    "Pressing the alert button provides various alert type options to send to added contacts. When none of the alert types are selected within 20 seconds, Emergency alert is sent to all contact"));

            try {
                // alertViewAlertButton.setImageBitmap(ScaleImage.ShrinkBitmap(this.getActivity(),newWidth));
                alertViewAlertButton.setImageBitmap(ShrinkBitmap(width));
            } catch (FileNotFoundException e) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "Alert: File not found exception", PMWF_Log.getStringFromStackTrace(e));
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "Alert: Exception setting bitmap", PMWF_Log.getStringFromStackTrace(ex));
            } catch (Error er) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "Alert: Error setting bitmap", PMWF_Log.getStringFromError(er));
                try {
                    alertViewAlertButton.setImageDrawable(getResources().getDrawable(R.drawable.imgalert));
                } catch (Exception e) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "Alert: Exception setting drawable",
                            PMWF_Log.getStringFromStackTrace(e));
                } catch (Error err) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "Alert: Error setting drawable", PMWF_Log.getStringFromError(err));
                }

            }

            alertViewAlertButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    try {
                        Intent intent = new Intent(getActivity(), AlertActivity.class);
                        getActivity().startActivityForResult(intent, HomePageActivity.INTENT_ALERT_SENT);
                    } catch (Exception e) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentMainAlert: AlertViewAlertButton On Click",
                                PMWF_Log.getStringFromStackTrace(e));

                    }

                }
            });
        } else {
            // ImageView alertViewAlertButton = (ImageView)
            // rootView.findViewById(R.id.alertButton);
            // alertViewAlertButton.setBackgroundResource(R.drawable.imgalert);

            imgAlertDesc = (TextView) rootView.findViewById(R.id.alertMessage);
            imgAlertDesc
                    .setText(ApplicationSettings.translationSettings
                            .GetTranslation(
                                    "and_no_contacts",
                                    "There are currently no contacts linked to alerttype, please go to the menu at the top left of the screen to add new contacts"));

            /*
             * try { alertViewAlertButton.setImageBitmap(ShrinkBitmap(width)); }
             * catch (FileNotFoundException e) { e.printStackTrace(); }
             */

        }

        return rootView;
    }

    private Bitmap ShrinkBitmap(double width) throws FileNotFoundException {
        if (bitmap == null) {
            // BitmapFactory.Options bmpFactoryOptions = new
            // BitmapFactory.Options();
            // bmpFactoryOptions.inJustDecodeBounds = true;
            // //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
            // R.drawable.imgalert, bmpFactoryOptions);
            // int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth /
            // (float) width);
            //
            // bmpFactoryOptions.inSampleSize = widthRatio;
            //
            // if (bmpFactoryOptions.inSampleSize <= 0) {
            // bmpFactoryOptions.inSampleSize = 0;
            // }
            // bmpFactoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
            // bmpFactoryOptions.inJustDecodeBounds = false;
            width = width - (width / 4);

            if (ApplicationSettingsFilePath.language == 1043) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgalert);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgalarm);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) width, (int) width, true);
        }
        return bitmap;

    }

}
