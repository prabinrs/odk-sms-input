package org.opendatakit.smsinput.logic;

import java.util.List;

import org.opendatakit.smsinput.api.ISmsProcessor;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.Constants;

import android.content.Context;

/**
 * Processes SMS messages for the SMS Input app.
 * @author sudar.sam@gmail.com
 *
 */
public class SmsInputAppProcessor implements ISmsProcessor {
  

  private WriteStockMessageProcessor mStockProcessor;
  
  public SmsInputAppProcessor(Context context) {
    this(
        context,
        WriteStockMessageProcessor.createForApp(
          context,
          Constants.DEFAULT_SMS_APP_ID));
  }
  
  protected SmsInputAppProcessor(
      Context context,
      WriteStockMessageProcessor stockMessageProcessor) {
    this.mStockProcessor = stockMessageProcessor;
  }

  @Override
  public void processSmsMessages(List<OdkSms> messages) {
    this.mStockProcessor.processSmsMessages(messages);
  }

}
