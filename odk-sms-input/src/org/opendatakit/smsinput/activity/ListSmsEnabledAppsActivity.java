package org.opendatakit.smsinput.activity;

import java.util.List;

import org.opendatakit.smsinput.component.SmsEnabledAppLoader;
import org.opendatakit.smsinput.util.Constants;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class ListSmsEnabledAppsActivity extends ListActivity implements 
    LoaderManager.LoaderCallbacks<List<String>> {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    this.getLoaderManager().initLoader(
        Constants.LoaderIds.SMS_ENABLED_APPS,
        null,
        this);
    
  }
  
  public void handleLoad(List<String> appIds) {
    
    String[] values = appIds.toArray(new String[appIds.size()]);
    
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        this,
        android.R.layout.simple_list_item_1,
        values);
    
    this.setListAdapter(adapter);
    
  }

  @Override
  public Loader<List<String>> onCreateLoader(int id, Bundle args) {
    SmsEnabledAppLoader result = new SmsEnabledAppLoader(this);
    return result;
  }

  @Override
  public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
    this.handleLoad(data);
  }

  @Override
  public void onLoaderReset(Loader<List<String>> loader) {
    // no-op
  }
  
  
  

}
