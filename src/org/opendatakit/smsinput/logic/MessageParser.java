package org.opendatakit.smsinput.logic;

import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.Constants;

import android.util.Log;

/**
 * Get information out of a text message.
 * @author sudar.sam@gmail.com
 *
 */
public class MessageParser {
  
  private static final String TAG = MessageParser.class.getSimpleName();
  
  public boolean isForOdk(OdkSms sms) {
    Log.e(TAG, "[isForTable] unimplemented");
    return true;
  }

  public String getAppId(OdkSms sms) {
    Log.e(TAG, "[getAppId] app id always returning tables");
    return Constants.DEFAULT_TABLES_APP_ID;
  }

}
