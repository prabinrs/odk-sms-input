package org.opendatakit.smsinput.receiver;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.R;
import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.app.OdkAppMessageProcessor;
import org.opendatakit.smsinput.app.OdkAppReader;
import org.opendatakit.smsinput.logic.MessageParser;
import org.opendatakit.smsinput.logic.SmsFilter;
import org.opendatakit.smsinput.logic.WriteStockMessageProcessor;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.AppSmsAccessor;
import org.opendatakit.smsinput.persistence.SmsRecordDefinition;
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
  
  /**
   * Do the universal procesing for every SMS that is performed by the SMS
   * Input app itself.
   * @param context
   * @param messages
   */
  protected void doSmsInputProcessing(Context context, List<OdkSms> messages) {

    String appId = Constants.DEFAULT_SMS_APP_ID;

    ISmsProcessor stockProcessor = WriteStockMessageProcessor.createForApp(
        context,
        appId);
    
    stockProcessor.processSmsMessages(messages);
    
  }
  
  protected OdkAppReader createAppReader(Context context) {
    OdkAppReader result = new OdkAppReader(context);
    return result;
  }
    
  protected void doAppSpecificProcessing(
      Context context,
      List<OdkSms> odkMessages) {
    
    Log.e(
        TAG,
        "[doAppSpecificProcessing] currently unimplemented for anything "
            + "beyond basic ODK Tables debugging functionality.");
    
    OdkAppReader appReader = this.createAppReader(context);
    
    ISmsProcessor allAppProcessor = this.createAllAppProcessor(appReader);
    
    allAppProcessor.processSmsMessages(odkMessages);
    
  }
  
  protected OdkAppMessageProcessor createAllAppProcessor(
      OdkAppReader appReader) {
    OdkAppMessageProcessor result = new OdkAppMessageProcessor(appReader);
    return result;
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
        Constants.DEFAULT_SMS_APP_ID,
        new SmsRecordDefinition());
    
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
  
  protected ModelConverter createModelConverter() {
    return new ModelConverter();
  }
  
  protected MessageParser createMessageParser() {
    return new MessageParser();
  }

}
