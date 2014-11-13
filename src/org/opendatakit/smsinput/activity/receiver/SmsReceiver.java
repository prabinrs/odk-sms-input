package org.opendatakit.smsinput.activity.receiver;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.logic.AppSmsProcessor;
import org.opendatakit.smsinput.logic.MessageParser;
import org.opendatakit.smsinput.logic.SmsFilter;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.AppSmsAccessor;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Config;
import org.opendatakit.smsinput.util.Constants;
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
    
    SmsFilter filter = this.createSmsFilter();
    
    SmsMessage[] messages = bundleUtil.getMessageFromBundle(extras);
    
    List<OdkSms> odkMessages = filter.convertToOdkMessages(messages);
    
    // Save the SMS globally, so that we will be saving all the SMS messages
    // received by the phone in the database.
    this.saveMessagesInSmsInputDatabase(context, odkMessages);
    
    this.doAppSpecificProcessing(odkMessages);
    
  }
  
  protected void doAppSpecificProcessing(List<OdkSms> odkMessages) {
    Log.i(
        TAG,
        "[doAppSpecificProcessing] currently unimplemented on every "
            + "received message.");
  }
  
  protected void saveMessagesInSmsInputDatabase(
      Context context,
      List<OdkSms> odkMessages) {
    ODKDatabaseUtils dbUtil = this.createDatabaseUtil();
    SQLiteDatabase smsInputDatabase = this.createDatabaseForApp(
        context,
        Constants.DEFAULT_SMS_APP_ID);
    
    AppSmsAccessor smsInputAccessor = new AppSmsAccessor(
        dbUtil,
        smsInputDatabase,
        Constants.DEFAULT_SMS_APP_ID);
    
    for (OdkSms odkSms : odkMessages) {
      smsInputAccessor.insertNewSmsMessage(odkSms, false, false);
    }
    
  }
  
  /**
   * Get the database for the sms input app itself, as opposed to a
   * user-defined ODK 2.0 app.
   * @param context
   * @return
   */
  protected SQLiteDatabase createSmsInputDatabase(Context context) {
    SQLiteDatabase result = this.createDatabaseForApp(
        context,
        Constants.DEFAULT_SMS_APP_ID);
    return result;
  }
      
  protected ModelConverter createConverter() {
    return new ModelConverter();
  }
  
  protected BundleUtil createBundleUtil() {
    return new BundleUtil();
  }
  
  protected SmsFilter createSmsFilter() {
    SmsFilter result = new SmsFilter(
        this.createConverter(),
        this.createMessageParser());
    
    return result;
  }
  
  protected SQLiteDatabase createDatabaseForApp(
      Context context,
      String appId) {
    SQLiteDatabase result = DatabaseFactory.get().getDatabase(context, appId);
    return result;
  }
  
  protected ODKDatabaseUtils createDatabaseUtil() {
    ODKDatabaseUtils result = ODKDatabaseUtils.get();
    return result;
  }
  
  protected AppSmsProcessor createSmsProcessor(Context context, String appId) {
    ODKDatabaseUtils dbUtil = this.createDatabaseUtil();
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
