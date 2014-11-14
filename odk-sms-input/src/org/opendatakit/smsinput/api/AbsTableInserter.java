package org.opendatakit.smsinput.api;

import java.util.ArrayList;
import java.util.UUID;

import org.opendatakit.common.android.data.ColumnDefinition;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.util.Config;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Defines writing into a table.
 * @author sudar.sam@gmail.com
 *
 */
public abstract class AbsTableInserter {
  
  private ODKDatabaseUtils mDbUtil;
  private SQLiteDatabase mDatabase;
  private String mAppId;
  private ITableDefinition mTableDefinition;
  
  public AbsTableInserter(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase appDatabase,
      String appId,
      ITableDefinition tableDefinition) {
    this.mDbUtil = dbUtil;
    this.mDatabase = appDatabase;
    this.mAppId = appId;
    this.mTableDefinition = tableDefinition;
    
    // Make sure the table exists
    this.assertOrCreateTable();
  }
  
  protected ODKDatabaseUtils getDbUtil() {
    return this.mDbUtil;
  }
  
  protected SQLiteDatabase getAppDatabase() {
    return this.mDatabase;
  }
  
  protected String getAppId() {
    return this.mAppId;
  }
  
  protected ITableDefinition getTableDefinition() {
    return this.mTableDefinition;
  }
  
  /**
   * Ensure that the table exists in the database, creating it if necessary.
   */
  protected void assertOrCreateTable() {
    this.getDbUtil().createOrOpenDBTableWithColumns(
        this.mDatabase,
        this.mAppId,
        this.getTableDefinition().getTableId(),
        this.getTableDefinition().getColumns());
  }
  
  private String getDebugTag() {
    return this.getClass().getSimpleName();
  }
  
  String createNewRowId() {
    String result = UUID.randomUUID().toString();
    return result;
  }
  
  /**
   * Insert the content values into this table as a new row.
   * @param values
   */
  public void insertValuesIntoDatabase(ContentValues values) {
    
    ArrayList<ColumnDefinition> columnDefinitions =
        ColumnDefinition.buildColumnDefinitions(
            this.mAppId,
            this.mTableDefinition.getTableId(),
            this.mTableDefinition.getColumns());
    
    String rowId = this.createNewRowId();
    
    if (Config.DEBUG) {
      Log.d(
          this.getDebugTag(),
          "[insertValuesIntoDatabase] inserting values [" +
              values +
              "] with id [" +
              rowId + "]");
    }
    
    this.mDbUtil.insertDataIntoExistingDBTableWithId(
        this.mDatabase,
        this.mTableDefinition.getTableId(),
        columnDefinitions,
        values,
        rowId);
        
  }
  
}
