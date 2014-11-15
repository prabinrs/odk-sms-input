package org.opendatakit.smsinput.component;

import java.util.List;

import org.opendatakit.smsinput.app.OdkAppReader;

import android.content.Context;

/**
 * Loads the ids of apps that have SMS enabled.
 * @author sudar.sam@gmail.com
 *
 */
public class SmsEnabledAppLoader extends AbsSmsInputLoader<List<String>> {

  public SmsEnabledAppLoader(Context context) {
    super(context);
  }
  
  @Override
  protected List<String> doSynchronousLoad() {
    OdkAppReader reader = this.createAppReader();
    List<String> result =reader.getAppIdsWithSmsInputEnabled();
    return result;
  }
  
  protected OdkAppReader createAppReader() {
    OdkAppReader result = new OdkAppReader(this.getContext());
    return result;
  }

}
