package org.opendatakit.smsinput.persistence;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.model.SmsDataRecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Read and write messages to/from the database.
 * @author sudar.sam@gmail.com
 *
 */
public class AppSmsAccessor {
  
  private ODKDatabaseUtils mDbUtil;
  private SQLiteDatabase mDatabase;
  private String mAppId;
  
  public AppSmsAccessor(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase database,
      String appId) {
    this.mDbUtil = dbUtil;
    this.mDatabase = database;
    this.mAppId = appId;
    
    // Make sure the table exists.
    this.assertOrCreateSmsTable();
    
  }
  
  protected void assertOrCreateSmsTable() {
    
    SmsRecordDefinition tableDefinition = new SmsRecordDefinition();
    
    this.mDbUtil.createOrOpenDBTableWithColumns(
        this.mDatabase,
        this.mAppId,
        tableDefinition.getTableId(),
        tableDefinition.getColumns());
    
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
	
	public void insertSmsDataRecord(SmsDataRecord record) {
	  throw new IllegalStateException("unimplemented");
	}
	
	/**
	 * Insert a new SMS record into the database.
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
