package org.opendatakit.smsinput.receiver;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.R;
import org.opendatakit.smsinput.app.OdkAppReader;
import org.opendatakit.smsinput.logic.AppSmsProcessor;
import org.opendatakit.smsinput.logic.MessageParser;
import org.opendatakit.smsinput.logic.SmsFilter;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.AppSmsAccessor;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Config;
import org.opendatakit.smsinput.util.Constants;
import org.opendatakit.smsinput.util.ToastUtil;

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
    
    if (Config.DEBUG_TOASTS) {
      
    }
    
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
      return;
    }
    
    if (Config.DEBUG_TOASTS) {
      ToastUtil.doShort(context, R.string.received_potential_intent);
    }
    
    BundleUtil bundleUtil = this.createBundleUtil();
    
    SmsMessage[] messages = bundleUtil.getMessageFromBundle(extras);
    
    ModelConverter converter = this.createConverter();
    
    List<OdkSms> odkMessages = converter.convertToOdkSms(messages);
    
    // Save the SMS globally, so that we will be saving all the SMS messages
    // received by the phone in the database.
    this.saveMessagesInSmsInputDatabase(context, odkMessages);
    
    this.doAppSpecificProcessing(context, odkMessages);
    
  }
    
  protected void doAppSpecificProcessing(
      Context context,
      List<OdkSms> odkMessages) {
    Log.i(
        TAG,
        "[doAppSpecificProcessing] currently unimplemented for anything "
            + "beyond basic ODK Tables debugging functionality.");
    
    OdkAppReader appReader = new OdkAppReader(context);
    
    List<String> smsAppIds = appReader.getAppIdsWithSmsInputEnabled();
    
    List<AppSmsProcessor> smsProcessors = appReader.getProcessorsForAppIds(
        context,
        smsAppIds);
    
    for (AppSmsProcessor smsProcessor : smsProcessors) {
      smsProcessor.processSmsMessages(odkMessages);
    }
    
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
