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
  
  public void processSmsMessages(List<OdkSms> messages) {
    for (OdkSms odkSms : messages) {
      this.handleSms(odkSms);
    }
  }
  
  /**
   * Handle the sms.
   * @param odkSms
   */
  protected void handleSms(OdkSms odkSms) {    
    AppSmsAccessor accessor = this.createAccessor();
    accessor.insertNewSmsMessage(odkSms, false, false);
  }
  
  /**
   * Get the accessor for the given app. NB: this should perhaps be passed as a
   * constructor parameter to facilitate app-dependent accessors.
   * @param appId
   * @return
   */
  protected AppSmsAccessor createAccessor() {
    AppSmsAccessor result = new AppSmsAccessor(
        this.mUtil,
        this.mDatabase,
        this.mAppId);
    return result;
  }
  
}
