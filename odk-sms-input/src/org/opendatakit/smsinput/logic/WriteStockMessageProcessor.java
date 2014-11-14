package org.opendatakit.smsinput.logic;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.persistence.StockMessageAccessor;
import org.opendatakit.smsinput.persistence.SmsRecordDefinition;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * An {@link ISmsProcessor} implementation that writes the stock message
 * information to the database.
 * @author sudar.sam@gmail.com
 *
 */
public class WriteStockMessageProcessor implements ISmsProcessor {
  
  /**
   * Factory method for creating a default processor that simply saves all the
   * SMS messages in a database for the given app id.
   * @param context
   * @param appId
   * @return
   */
  public static WriteStockMessageProcessor createForApp(
      Context context,
      String appId) {
    
    ODKDatabaseUtils dbUtil = ODKDatabaseUtils.get();
 
    SQLiteDatabase database = DatabaseFactory.get().getDatabase(
        context,
        appId);
 
    ITableDefinition defaultTableDefinition = new SmsRecordDefinition();
    
    StockMessageAccessor appAccessor = new StockMessageAccessor(
        dbUtil,
        database,
        appId,
        defaultTableDefinition);
    
    WriteStockMessageProcessor stockProcessor =
        new WriteStockMessageProcessor(appAccessor);
    
    return stockProcessor;
    
  }
  
  private StockMessageAccessor mAccessor;
  
  public WriteStockMessageProcessor(StockMessageAccessor accessor) {
    this.mAccessor = accessor;
  }
  
  @Override
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
    this.mAccessor.insertNewSmsMessage(odkSms, false, false);
  }

}
