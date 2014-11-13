package org.opendatakit.smsinput.activity.receiver;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDataUtils;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.logic.AppSmsProcessor;
import org.opendatakit.smsinput.logic.MessageParser;
import org.opendatakit.smsinput.logic.SmsProcessor;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.AppSmsAccessor;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Config;
import org.opendatakit.smsinput.util.ModelConverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    

    
  }
      
  protected ModelConverter createConverter() {
    return new ModelConverter();
  }
  
  protected BundleUtil createBundleUtil() {
    return new BundleUtil();
  }
  
  protected SQLiteDatabase createDatabaseForApp(
      Context context,
      String appId) {
    SQLiteDatabase result = DatabaseFactory.get().getDatabase(context, appId);
    return result;
  }
  
  protected AppSmsProcessor createSmsProcessor(Context context, String appId) {
    ODKDatabaseUtils dbUtil = ODKDatabaseUtils.get();
    SQLiteDatabase database = this.createDatabaseForApp(context, appId);
    ModelConverter converter = this.createConverter();
    MessageParser parser = this.createMessageParser();
    
    AppSmsProcessor result = new AppSmsProcessor(
        appId,
        dbUtil,
        database,
        converter,
        parser);
    
    return result;
  }
  
  protected ModelConverter createModelConverter() {
    return new ModelConverter();
  }
  
  protected MessageParser createMessageParser() {
    return new MessageParser();
  }

}
