package com.android.socialalert.accountAuthenticator;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static com.android.socialalert.accountAuthenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.android.socialalert.accountAuthenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
import static com.android.socialalert.accountAuthenticator.AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY;
import static com.android.socialalert.accountAuthenticator.AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.socialalert.logger.PMWF_Log;

/**
 * Created with IntelliJ IDEA. User: Udini Date: 19/03/13* Time: 18:58
 */
public class UdinicAuthenticator extends AbstractAccountAuthenticator {

    private String TAG = "UdinicAuthenticator";
    private final Context mContext;

    public UdinicAuthenticator(Context context) {
        super(context);
        // I hate you! Google - set mConext as protected!
        this.mContext = context;

    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
            String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType,
            Bundle options) throws NetworkErrorException {

        try {
            // If the caller requested an authToken type we don't support, then
            // return an error
            if (!authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY)
                    && !authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
                return result;
            }

            // Extract the username and password from the Account Manager, and
            // ask
            // the server for an appropriate AuthToken.
            final AccountManager am = AccountManager.get(mContext);

            String authToken = am.peekAuthToken(account, authTokenType);

            Log.d("udinic", TAG + "> peekAuthToken returned - " + authToken);

            // Lets give another try to authenticate the user
            if (TextUtils.isEmpty(authToken)) {
                final String password = am.getPassword(account);
                if (password != null) {
                    try {
                        Log.d("udinic", TAG + "> re-authenticating with the existing password");
                        // authToken =
                        // sServerAuthenticate.userSignIn(account.name,
                        // password, authTokenType);
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UdinicAuthenticator::getAuthToken(1)",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                }
            }
            // If we get an authToken - we return it
            if (!TextUtils.isEmpty(authToken)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return result;
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UdinicAuthenticator::getAuthToken(2)", ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";

    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features)
            throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType,
            Bundle options) throws NetworkErrorException {
        return null;
    }

}
