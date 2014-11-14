package org.opendatakit.smsinput.model;

import java.util.ArrayList;
import java.util.List;

import android.telephony.SmsMessage;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class ModelConverter {
  
  public OdkSms convertToOdkSms(SmsMessage message) {
    OdkSms result = new OdkSms(
        message.getDisplayOriginatingAddress(),
        message.getMessageBody());
    return result;
  }
  
  /**
   * Convenience method for calling {@link #convertToOdkSms(SmsMessage)} on
   * every message in messages.
   * @param messages
   * @return
   */
  public List<OdkSms> convertToOdkSms(SmsMessage[] messages) {
    List<OdkSms> result = new ArrayList<OdkSms>();
    
    for (SmsMessage smsMessage : messages) {
      OdkSms odkSms = this.convertToOdkSms(smsMessage);
      result.add(odkSms);
    }
    
    return result;
    
  }
  
  public SmsDataRecord convertToDataRecord(
      OdkSms odkSms,
      boolean wasParsed,
      boolean wasTallied) {
    
    SmsDataRecord result = new SmsDataRecord(odkSms, wasParsed, wasTallied);
    
    return result;
    
  }

}
