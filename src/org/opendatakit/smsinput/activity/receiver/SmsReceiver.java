package org.opendatakit.smsinput.activity.receiver;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.model.SmsAccessor;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Config;
import org.opendatakit.smsinput.util.ModelConverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Receive SMS messages and put it in the database.
 * @author sudar.sam@gmail.com
 *
 */
public class SmsReceiver extends BroadcastReceiver {
  
  private static final String TAG = SmsReceiver.class.getSimpleName();

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    
    if (Config.DEBUG) {
      Log.d(TAG, "[onReceive] received intent");
    }
    
    if (Config.DEBUG) {
      if (extras == null) {
        Log.d(TAG, "[onReceive] extras was null");
      } else {
        Log.d(TAG, "[onReceive] extras not null");
      }
    }
    
    if (extras == null) {
      if (Config.DEBUG) {
        Log.d(TAG, "[onReceive] extras was null");
      }
      return;
    }
    
    BundleUtil bundleUtil = this.createBundleUtil();
    
    SmsMessage[] messages = bundleUtil.getMessageFromBundle(extras);
    
    SmsAccessor accessor = this.createSmsAccessor();
    ModelConverter converter = this.createConverter();
    
    List<OdkSms> odkSmsMessages = new ArrayList<OdkSms>();
    
    for (SmsMessage message : messages) {
      OdkSms odkSms = converter.convertToOdkSms(message);
      odkSmsMessages.add(odkSms);
      
      accessor.insertNewSmsMessage(odkSms, false, false);
      
    }
    
  }
    
  protected ModelConverter createConverter() {
    return new ModelConverter();
  }
  
  protected BundleUtil createBundleUtil() {
    return new BundleUtil();
  }
  
  protected SmsAccessor createSmsAccessor() {
    return new SmsAccessor();
  }

}
