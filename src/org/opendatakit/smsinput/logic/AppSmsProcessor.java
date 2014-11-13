package org.opendatakit.smsinput.logic;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.AppSmsAccessor;
import org.opendatakit.smsinput.util.Config;

import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Process SMS messages for a particular app.
 * @author sudar.sam@gmail.com
 *
 */
public class AppSmsProcessor {
  
  private static final String TAG = AppSmsProcessor.class.getSimpleName();
  
  private String mAppId;
  private ODKDatabaseUtils mUtil;
  private SQLiteDatabase mDatabase;
  private ModelConverter mConverter;
  private MessageParser mParser;
  
  public AppSmsProcessor(
      String appId,
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase appDatabase,
      ModelConverter converter,
      MessageParser parser) {
    this.mAppId = appId;
    this.mUtil = dbUtil;
    this.mDatabase = appDatabase;
    this.mConverter = converter;
    this.mParser = parser;
  }
  
  public void processSmsMessages(SmsMessage[] messages) {
    
    List<OdkSms> allMessages = this.convertToOdkMessages(messages);
    
    List<OdkSms> messagesForOdk = this.getMessagesForOdk(allMessages);
    
    if (messagesForOdk.isEmpty()) {
      if (Config.DEBUG) {
        Log.d(TAG, "[processSmsMessages] no messages for odk");
      }
    }
    
    for (OdkSms odkSms : messagesForOdk) {
      this.handleSms(odkSms);
    }
    
  }
  
  protected List<OdkSms> convertToOdkMessages(SmsMessage[] messages) {
    List<OdkSms> odkMessages = new ArrayList<OdkSms>();
    
    for (SmsMessage message : messages) {
      OdkSms odkSms = this.mConverter.convertToOdkSms(message);
      odkMessages.add(odkSms);
    }
    
    return odkMessages;
    
  }
  
  /**
   * Handle the sms.
   * @param odkSms
   */
  protected void handleSms(OdkSms odkSms) {    
    AppSmsAccessor accessor = this.getAccessor();
    accessor.insertNewSmsMessage(odkSms, false, false);
  }
  
  /**
   * Get the SMS messages meant for ODK.
   * @param messages
   * @return
   */
  protected List<OdkSms> getMessagesForOdk(List<OdkSms> messages) {
    List<OdkSms> result = new ArrayList<OdkSms>();
    for (OdkSms sms : messages) {
      if (this.mParser.isForOdk(sms)) {
        result.add(sms);
      }
    }
    return result;
  }
  
  /**
   * Get the accessor for the given app. Would be relevant 
   * @param appId
   * @return
   */
  protected AppSmsAccessor getAccessor() {
    AppSmsAccessor result = new AppSmsAccessor(
        this.mUtil,
        this.mDatabase,
        this.mAppId);
    return result;
  }
  
}
