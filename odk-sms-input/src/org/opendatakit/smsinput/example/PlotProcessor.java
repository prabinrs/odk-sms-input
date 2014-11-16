package org.opendatakit.smsinput.example;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.model.OdkSms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Processes SMS messages for the plot table.
 * @author sudar.sam@gmail.com
 *
 */
public class PlotProcessor implements ISmsProcessor {
  
  PlotInserter mAccessor;
  
  public PlotProcessor(Context context, String appId) {
    ODKDatabaseUtils dbUtil = ODKDatabaseUtils.get();
    
    SQLiteDatabase database = DatabaseFactory.get().getDatabase(
        context,
        appId);
    
    PlotDefinition plotDefinition = new PlotDefinition(dbUtil, database); 
    
    this.mAccessor = new PlotInserter(dbUtil, database, appId, plotDefinition);
    
  }

  @Override
  public void processSmsMessages(List<OdkSms> messages) {
    
    BasicPlotParser parser = new BasicPlotParser();
    
    for (OdkSms message : messages) {
      
      if (parser.isForThisParser(message)) {
        
        ContentValues values = parser.extractValues(message);
        this.mAccessor.insertValuesIntoDatabase(values);
        
      }
      
    }
    
  }
  
}
