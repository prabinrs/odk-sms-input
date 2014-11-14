package org.opendatakit.smsinput.app;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.logic.AppSmsProcessor;
import org.opendatakit.smsinput.logic.MessageParser;
import org.opendatakit.smsinput.logic.OdkTablesSmsProcessor;
import org.opendatakit.smsinput.model.ModelConverter;
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
  
  public List<AppSmsProcessor> getProcessorsForAppIds(
      Context context,
      List<String> appIds) {
    List<AppSmsProcessor> result = new ArrayList<AppSmsProcessor>();
    
    for (String appId : appIds) {
      AppSmsProcessor processor = this.getProcessorForAppId(context, appId);
      result.add(processor);
    }
    
    return result;
    
  }
  
  /**
   * Get the {@link AppSmsProcessor} for the given app id.
   * @param appId
   * @return
   */
  public AppSmsProcessor getProcessorForAppId(
      Context context,
      String appId) {
    if (!appId.equals(Constants.DEFAULT_TABLES_APP_ID)) {
      throw new IllegalArgumentException(
          "currently tables is the only supported sms parsing app");
    }
       
    OdkTablesSmsProcessor result =
        this.createTablesSmsProcessor(this.mContext);
    
    return result;
    
  }
  
  protected OdkTablesSmsProcessor createTablesSmsProcessor(Context context) {
    String appId = Constants.DEFAULT_TABLES_APP_ID;
    ODKDatabaseUtils dbUtil = ODKDatabaseUtils.get();
    SQLiteDatabase tablesDatabase = DatabaseFactory.get().getDatabase(
        context,
        appId);
    ModelConverter converter = new ModelConverter();
    MessageParser parser = new MessageParser();
    
    OdkTablesSmsProcessor result = new OdkTablesSmsProcessor(
        appId,
        dbUtil,
        tablesDatabase,
        converter,
        parser);
    
    return result;
    
  }

}
