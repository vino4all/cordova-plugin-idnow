package com.idnow.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.idnow.sdk.IDnowSDK;

/**
 * IDnow plugin native implementation.
 */
public class IDnowPlugin extends CordovaPlugin {

    private Context context;
    private Activity activity;
    private static final String ACTION_START_VIDEO_IDENT = "startVideoIdent";
    private static final String DEV_TEST_TOKEN = ""; // It should be in 'XYZ-ABCDE' format
    public static final int REQUEST_VIDEO_IDENT = 311;

    private CallbackContext callbackContext = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        activity = cordova.getActivity();
        context = activity.getApplicationContext();

        if(action.equals(ACTION_START_VIDEO_IDENT)){
            String companyId = args.getString(0);
            String transactionToken = args.getString(1);
            String apiHost = args.getString(2);
            boolean showVideoOverviewCheck = args.getBoolean(3);
            boolean showErrorSuccessScreen = args.getBoolean(4);
            this.startVideoIdent(companyId, transactionToken, apiHost, showVideoOverviewCheck, showErrorSuccessScreen);
            return true;
        }
        return false;
    }

    private void startVideoIdent(String companyId, String transactionToken, String apiHost, boolean showVideoOverviewCheck, boolean showErrorSuccessScreen) {
        try
        {
            cordova.setActivityResultCallback (this);

            IDnowSDK.getInstance().initialize( activity, companyId );
            IDnowSDK.setTransactionToken( transactionToken, context );
            IDnowSDK.setApiHost(apiHost, context);
            IDnowSDK.setShowVideoOverviewCheck( showVideoOverviewCheck, context );
            IDnowSDK.setShowErrorSuccessScreen( showErrorSuccessScreen, context );

            IDnowSDK.getInstance().start( IDnowSDK.getTransactionToken( context ) );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Callback from the IDNow SDK
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if ( requestCode == IDnowSDK.REQUEST_ID_NOW_SDK )
        {
            if ( resultCode == IDnowSDK.RESULT_CODE_SUCCESS )
            {
                StringBuilder toastText = new StringBuilder( "Identification performed. " );
                if ( null != data )
                {
                    toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN ) );
                }
                Toast.makeText( context, toastText.toString(), Toast.LENGTH_LONG ).show();

                callbackContext.success(toastText.toString());
            }
            else if ( resultCode == IDnowSDK.RESULT_CODE_CANCEL )
            {
                StringBuilder toastText = new StringBuilder( "Identification canceled. " );
                if ( null != data )
                {
                    toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_ERROR ) );
                }
                Toast.makeText( context, toastText.toString(), Toast.LENGTH_LONG ).show();

                callbackContext.success(toastText.toString());
            }
            else if ( resultCode == IDnowSDK.RESULT_CODE_FAILED )
            {
                StringBuilder toastText = new StringBuilder( "Identification failed. " );
                if ( null != data )
                {
                    toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_ERROR ) );
                }
                Toast.makeText( context, toastText.toString(), Toast.LENGTH_LONG ).show();

                callbackContext.success(toastText.toString());
            }
            else
            {
                StringBuilder toastText = new StringBuilder( "Result Code: " );
                toastText.append( resultCode );
                Toast.makeText( context, toastText.toString(), Toast.LENGTH_LONG ).show();

                callbackContext.success(toastText.toString());
            }
        }
    }
}