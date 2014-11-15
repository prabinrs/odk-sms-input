package org.opendatakit.smsinput.persistence;

import java.util.List;

import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.component.AbsSmsInputLoader;
import org.opendatakit.smsinput.model.SmsDataRecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SmsDataRecordLoader extends
    AbsSmsInputLoader<List<SmsDataRecord>> {
  
  protected ODKDatabaseUtils mDbUtil;
  protected SQLiteDatabase mDatabase;
  protected String mAppId;
  protected ITableDefinition mTableDefinition;
  
  private StockMessageAccessor mAccessor;

  public SmsDataRecordLoader(
      Context context,
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase appDatabase,
      String appId,
      ITableDefinition tableDefinition) {
    super(context);
    
    this.mAppId = appId;
    this.mDatabase = appDatabase;
    this.mDbUtil = dbUtil;
    this.mTableDefinition = tableDefinition;
    
    this.mAccessor = new StockMessageAccessor(
        this.mDbUtil,
        this.mDatabase,
        this.mAppId,
        this.mTableDefinition);
  }

  @Override
  protected List<SmsDataRecord> doSynchronousLoad() {
   List<SmsDataRecord> result = this.mAccessor.getAllMessages();
   return result;
  }

}
