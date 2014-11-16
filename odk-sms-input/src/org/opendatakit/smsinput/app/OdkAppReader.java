package org.opendatakit.smsinput.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.example.PlotInserter;
import org.opendatakit.smsinput.example.PlotProcessor;
import org.opendatakit.smsinput.logic.WriteStockMessageProcessor;
import org.opendatakit.smsinput.util.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Communicates with the various ODK 2.0 apps installed on the device.
 * @author sudar.sam@gmail.com
 *
 */
public class OdkAppReader {
  
  private static final String TAG = OdkAppReader.class.getSimpleName();
  
  private Context mContext;
  
  public OdkAppReader(Context context) {
    this.mContext = context;
  }
  
  public List<String> getAllInstalledAppIds() {
    Log.e(TAG, "[getAllInstalledAppIds] for now just returning tables");
    List<String> result = new ArrayList<String>();
    result.add(Constants.DEFAULT_TABLES_APP_ID);
    
    return result;
  }
  
  public List<String> getAppIdsWithSmsInputEnabled() {
    Log.e(
        TAG,
        "[getAppIdsWithSmsInputEnabled] unimplemented--just returning tables");
    List<String> result = new ArrayList<String>();
    result.add(Constants.DEFAULT_TABLES_APP_ID);
    
    return result;
  }
  
  /**
   * Get the {@link ISmsProcessor}s associated with the given app ids.
   * @param context
   * @param appIds
   * @return
   */
  public Map<String, List<ISmsProcessor>> getProcessorsForAppIds(
      List<String> appIds) {

    Map<String, List<ISmsProcessor>> result =
        new HashMap<String, List<ISmsProcessor>>();
    
    for (String appId : appIds) {
      List<ISmsProcessor> appProcessors = this.getProcessorsForAppId(
          this.mContext,
          appId);
      result.put(appId, appProcessors);
    }
    
    return result;
    
  }
  
  /**
   * Get the {@link WriteStockMessageProcessor} for the given app id.
   * @param appId
   * @return
   */
  public List<ISmsProcessor> getProcessorsForAppId(
      Context context,
      String appId) {
    if (!appId.equals(Constants.DEFAULT_TABLES_APP_ID)) {
      throw new IllegalArgumentException(
          "currently tables is the only supported sms parsing app");
    }
    
    ISmsProcessor tablesProcessor = WriteStockMessageProcessor.createForApp(
        context,
        appId);
       
    List<ISmsProcessor> result = new ArrayList<ISmsProcessor>();
    result.add(tablesProcessor);
    
    // We will also add the plot processor.
    ISmsProcessor plotProcessor = new PlotProcessor(this.mContext, appId);
    result.add(plotProcessor);
    
    return result;
    
  }

}
