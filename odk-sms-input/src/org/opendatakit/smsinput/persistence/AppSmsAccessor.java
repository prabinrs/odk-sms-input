package org.opendatakit.smsinput.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.opendatakit.common.android.data.ColumnDefinition;
import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.model.SmsDataRecord;
import org.opendatakit.smsinput.util.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Read and write messages to/from the database.
 * @author sudar.sam@gmail.com
 *
 */
public class AppSmsAccessor {
  
  public static final String TAG = AppSmsAccessor.class.getSimpleName();
  
  private ODKDatabaseUtils mDbUtil;
  private SQLiteDatabase mDatabase;
  private String mAppId;
  
  private SmsRecordDefinition mTableDefinition = new SmsRecordDefinition();
  
  public AppSmsAccessor(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase database,
      String appId) {
    this.mDbUtil = dbUtil;
    this.mDatabase = database;
    this.mAppId = appId;
    
    this.mTableDefinition = new SmsRecordDefinition();
    
    // Make sure the table exists.
    this.assertOrCreateSmsTable();
    
  }
    
  protected void assertOrCreateSmsTable() {
        
    this.mDbUtil.createOrOpenDBTableWithColumns(
        this.mDatabase,
        this.mAppId,
        this.mTableDefinition.getTableId(),
        this.mTableDefinition.getColumns());
    
  }
  
  protected SQLiteDatabase getDatabase(Context context, String appId) {
    SQLiteDatabase result = DatabaseFactory.get().getDatabase(context, appId);
    return result;
  }
  
  
	
	/**
	 * Get the messages that have not yet been dealt with. E.g. this might
	 * return the messages that have not attempted to be parsed and put into
	 * the database.
	 * @param appId
	 * @return
	 */
	public List<SmsDataRecord> getUntalliedMessages(String appId) {
		return null;
	}
	
	/**
	 * Get all the messages from the database.
	 * @param appId
	 * @return
	 */
	public List<SmsDataRecord> getAllMessages(String appId) {
		return null;
	}
	
	protected ContentValues getContentValuesFromRecord(SmsDataRecord record) {
	  ContentValues result = new ContentValues();
	  
	  result.put(
	      SmsRecordDefinition.ColumnNames.MESSAGE_BODY,
	      record.getOdkSms().getMessageBody());
	  
	  result.put(
	      SmsRecordDefinition.ColumnNames.SENDING_ADDRESS,
	      record.getOdkSms().getSender());
	  
	  result.put(
	      SmsRecordDefinition.ColumnNames.WAS_PARSED,
	      record.wasParsed());
	  
	  result.put(
	      SmsRecordDefinition.ColumnNames.WAS_TALLIED,
	      record.wasDealtWith());
	  
	  return result;
	  
	}
	
	/**
	 * Insert the data record into the database.
	 * @param record
	 */
	public void insertSmsDataRecord(SmsDataRecord record) {
	  
	  ContentValues contentValues = this.getContentValuesFromRecord(record);
	  	  
	  ArrayList<ColumnDefinition> columnDefinitions =
	      ColumnDefinition.buildColumnDefinitions(
	          this.mAppId,
	          this.mTableDefinition.getTableId(),
	          this.mTableDefinition.getColumns()); 
	  
	  String rowId = UUID.randomUUID().toString();
	  
	  if (Config.DEBUG) {
	    Log.d(
	        TAG,
	        "[insertSmsDataRecord] inserting sms record [" +
	            record +
	            "] with id [" +
	            rowId + "]");
	  }
	  
	  this.mDbUtil.insertDataIntoExistingDBTableWithId(
	      this.mDatabase,
	      this.mTableDefinition.getTableId(),
	      columnDefinitions,
	      contentValues,
	      rowId);
	  
	}
	
	/**
	 * Insert a new SMS record into the database.
	 * <p>
	 * Convenience method for calling {@link this.insertSmsDataRecord} with the
	 * given parsed and tallied values.
	 * @param odkSms
	 */
	public void insertNewSmsMessage(
	    OdkSms odkSms,
	    boolean wasParsed,
	    boolean wasTallied) {
	  SmsDataRecord dataRecord = new SmsDataRecord(
	      odkSms,
	      wasParsed,
	      wasTallied);
	  this.insertSmsDataRecord(dataRecord);
	}

}
