package org.opendatakit.smsinput.api;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.common.android.data.ColumnDefinition;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.persistence.SmsRecordDefinition;
import org.robolectric.RobolectricTestRunner;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class AbsTableInserterTest {
  
  private static final String TEST_APP_ID = "abs_table_inserter_test_app_id";
  
  AbsTableInserter inserter;
  
  ODKDatabaseUtils mockDbUtil;
  SQLiteDatabase mockDatabase;
  String appId;
  ITableDefinition tableDefinition;
  
  @Before
  public void before() {
    
    this.mockDbUtil = mock(ODKDatabaseUtils.class);
    this.mockDatabase = mock(SQLiteDatabase.class);
    this.appId = TEST_APP_ID;
    
    this.tableDefinition = new SmsRecordDefinition();
    
    this.inserter = new AbsTableInserter(
        this.mockDbUtil,
        this.mockDatabase,
        this.appId,
        this.tableDefinition) {
      
    };
  }
  
  @Test
  public void assertOrCreateTableCallsCorrectMethods() {
    
    this.inserter = spy(this.inserter);
    
    this.inserter.assertOrCreateTable();
    
    verify(this.inserter, times(1)).assertOrCreateTable();
    
  }
  
  @Test
  public void insertValuesIntoDatabaseCallsCorrectMethods() {
    
    this.inserter = spy(this.inserter);
    
    ArrayList<ColumnDefinition> columnDefinitions =
        ColumnDefinition.buildColumnDefinitions(
            this.appId,
            this.tableDefinition.getTableId(),
            this.tableDefinition.getColumns());
    
    String testRowId = "test_row_id_for_abs_table_inserter";
    
    doReturn(testRowId).when(this.inserter).createNewRowId();
    
    ContentValues dummyValues = new ContentValues();
    dummyValues.put("test_key_1", "hidy ho");
    dummyValues.put("test_key_2", 12345);
    
    this.inserter.insertValuesIntoDatabase(dummyValues);
    
    verify(this.mockDbUtil, times(1))
      .insertDataIntoExistingDBTableWithId(
          eq(this.mockDatabase),
          eq(this.tableDefinition.getTableId()),
          eq(columnDefinitions),
          eq(dummyValues),
          eq(testRowId));
    
  }

}
