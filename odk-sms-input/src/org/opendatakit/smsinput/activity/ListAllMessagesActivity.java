package org.opendatakit.smsinput.activity;

import java.util.List;

import org.opendatakit.common.android.database.DatabaseFactory;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.R;
import org.opendatakit.smsinput.component.SmsDataRecordAdapter;
import org.opendatakit.smsinput.model.SmsDataRecord;
import org.opendatakit.smsinput.persistence.SmsDataRecordLoader;
import org.opendatakit.smsinput.persistence.SmsRecordDefinition;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Config;
import org.opendatakit.smsinput.util.Constants;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class ListAllMessagesActivity extends ListActivity 
    implements LoaderManager.LoaderCallbacks<List<SmsDataRecord>> {
  
  private static final String TAG =
      ListAllMessagesActivity.class.getSimpleName();
  
  private String mAppId;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    this.setContentView(R.layout.activity_list_all_messages);
    
    Bundle extras = this.getIntent().getExtras();
    
    BundleUtil bundleUtil = this.createBundleUtil();
    
    this.mAppId = bundleUtil.getAppIdFromBundle(extras);
    
    if (Config.DEBUG) {
      Log.d(TAG, "[onCreate] found app id: " + this.mAppId);
    }
    
    this.getLoaderManager().initLoader(
        Constants.LoaderIds.SMS_DATA_RECORDS,
        null,
        this);
    
  }
  
  protected void handleLoad(List<SmsDataRecord> records) {
    
    if (Config.DEBUG) {
      Log.d(TAG, "[handleLoad] handling load for: " + records);
    }
    
    SmsDataRecordAdapter adapter = new SmsDataRecordAdapter(records);
    this.setListAdapter(adapter);
  }
  
  protected BundleUtil createBundleUtil() {
    return new BundleUtil();
  }
  
  protected ODKDatabaseUtils createDbUtil() {
    return ODKDatabaseUtils.get();
  }
  
  protected SQLiteDatabase createDatabase() {
    return DatabaseFactory.get().getDatabase(this, this.mAppId);
  }

  @Override
  public Loader<List<SmsDataRecord>> onCreateLoader(int id, Bundle args) {
    
    SmsDataRecordLoader result = new SmsDataRecordLoader(
        this,
        this.createDbUtil(),
        this.createDatabase(),
        this.mAppId,
        new SmsRecordDefinition());
    
    return result;
  }

  @Override
  public void onLoadFinished(Loader<List<SmsDataRecord>> loader, List<SmsDataRecord> data) {
    this.handleLoad(data);    
  }

  @Override
  public void onLoaderReset(Loader<List<SmsDataRecord>> loader) {
    // no-op here.    
  }
  
  

}
