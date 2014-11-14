package org.opendatakit.smsinput.persistence;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.model.SmsDataRecord;
import org.opendatakit.smsinput.util.TestUtil;
import org.robolectric.RobolectricTestRunner;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class StockMessageAccessorTest {
  
  String TEST_APP_ID = "stock_message_accessor_test_app_id";
  
  StockMessageAccessor stockAccessor;
  
  ODKDatabaseUtils mockDbUtil;
  SQLiteDatabase mockDatabase;
  
  String appId;
  ITableDefinition defaultTableDefinition;
  
  @Before
  public void before() {
    
    this.mockDbUtil = mock(ODKDatabaseUtils.class);
    this.mockDatabase = mock(SQLiteDatabase.class);
    
    this.appId = TEST_APP_ID;
    this.defaultTableDefinition = new SmsRecordDefinition();
    
    this.stockAccessor = new StockMessageAccessor(
        this.mockDbUtil,
        this.mockDatabase,
        appId,
        defaultTableDefinition);
    
  }
  
  @Test
  public void getContentValuesFromRecordCorrect() {
    OdkSms odkSms = TestUtil.getTestOdkSmsMessages().get(0);
    SmsDataRecord smsDataRecord = new SmsDataRecord(
        odkSms,
        true,
        true);
    
    ContentValues target = new ContentValues();
    
    target.put(
        SmsRecordDefinition.ColumnNames.MESSAGE_BODY,
        odkSms.getMessageBody());
    
    target.put(
        SmsRecordDefinition.ColumnNames.SENDING_ADDRESS,
        odkSms.getSender());
    
    target.put(
        SmsRecordDefinition.ColumnNames.WAS_PARSED,
        true);
    
    target.put(
        SmsRecordDefinition.ColumnNames.WAS_TALLIED,
        true);
    
    ContentValues actual =
        this.stockAccessor.getContentValuesFromRecord(smsDataRecord);
    
    assertThat(actual).isEqualTo(target);
    
  }
  
  @Test
  public void insertSmsDataRecordCorrect() {
    
    this.stockAccessor = spy(this.stockAccessor);
    
    OdkSms odkSms = TestUtil.getTestOdkSmsMessages().get(0);
    SmsDataRecord record = new SmsDataRecord(odkSms, true, false);
    
    ContentValues dummyValues = new ContentValues();
    dummyValues.put("dummy_key", "a fancy test value");
    
    doReturn(dummyValues)
      .when(this.stockAccessor)
      .getContentValuesFromRecord(record);
    
    this.stockAccessor.insertSmsDataRecord(record);
    
    verify(this.stockAccessor, times(1)).insertValuesIntoDatabase(dummyValues);
    
  }
  
  @Test
  public void insertNewOdkMessageCorrect() {
    
    this.stockAccessor = spy(this.stockAccessor);
    
    OdkSms odkSms = TestUtil.getTestOdkSmsMessages().get(1);
    
    boolean wasParsed = false;
    boolean wasTallied = true;
    
    SmsDataRecord targetRecord = new SmsDataRecord(
        odkSms,
        wasParsed,
        wasTallied);
    
    this.stockAccessor.insertSmsDataRecord(targetRecord);
    
    verify(this.stockAccessor, times(1)).insertSmsDataRecord(targetRecord);
    
  }

}
