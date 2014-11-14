package org.opendatakit.smsinput.persistence;

import java.util.List;

import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.AbsTableInserter;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.model.SmsDataRecord;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Read and write messages to/from the database. This is just in the ground
 * truth table, where the stock messages themselves are stored. This does not
 * input arbitrary values into an arbitrary table. Rather it simply saves SMS
 * messages for a given app so that all messages coming in to the device are
 * saved.
 * @author sudar.sam@gmail.com
 *
 */
public class StockMessageAccessor extends AbsTableInserter {

  public static final String TAG = StockMessageAccessor.class.getSimpleName();

  public StockMessageAccessor(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase database,
      String appId,
      ITableDefinition defaultTableDefinition) {
    super(dbUtil, database, appId, defaultTableDefinition);
  }

  /**
   * Get the messages that have not yet been dealt with. E.g. this might return
   * the messages that have not attempted to be parsed and put into the
   * database.
   * 
   * @param appId
   * @return
   */
  public List<SmsDataRecord> getUntalliedMessages(String appId) {
    return null;
  }

  /**
   * Get all the messages from the database.
   * 
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
        record.wasTallied());

    return result;

  }

  /**
   * Insert the data record into the database.
   * 
   * @param record
   */
  public void insertSmsDataRecord(SmsDataRecord record) {

    ContentValues contentValues = this.getContentValuesFromRecord(record);

    this.insertValuesIntoDatabase(contentValues);

  }

  /**
   * Insert a new SMS record into the database.
   * <p>
   * Convenience method for calling {@link this.insertSmsDataRecord} with the
   * given parsed and tallied values.
   * 
   * @param odkSms
   */
  public void insertNewSmsMessage(
      OdkSms odkSms,
      boolean wasParsed,
      boolean wasTallied) {
    SmsDataRecord dataRecord = new SmsDataRecord(odkSms, wasParsed, wasTallied);
    this.insertSmsDataRecord(dataRecord);
  }

}
