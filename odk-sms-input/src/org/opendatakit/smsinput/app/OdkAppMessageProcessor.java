package org.opendatakit.smsinput.app;

import java.util.List;
import java.util.Map;

import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.Config;

import android.util.Log;

/**
 * Processes messages for all relevant ODK apps.
 * @author sudar.sam@gmail.com
 *
 */
public class OdkAppMessageProcessor implements ISmsProcessor {
  
  private static final String TAG =
      OdkAppMessageProcessor.class.getSimpleName();
  
  private OdkAppReader mAppReader;
  
  public OdkAppMessageProcessor(OdkAppReader appReader) {
    this.mAppReader = appReader;
  }

  @Override
  public void processSmsMessages(List<OdkSms> messages) {
    List<String> smsAppIds = this.mAppReader.getAppIdsWithSmsInputEnabled();
    
    Map<String, List<ISmsProcessor>> appProcessors =
        this.mAppReader.getProcessorsForAppIds(smsAppIds);
    
    for (Map.Entry<String, List<ISmsProcessor>> entry :
      appProcessors.entrySet()) {
    
    if (Config.DEBUG) {
      Log.d(
          TAG,
          "[processSmsMessages] invoking processors for [" +
              entry.getKey() +
              "]");
    }
    
    for (ISmsProcessor processor : entry.getValue()) {
      processor.processSmsMessages(messages);
    }
    
  }
    
  }

}
