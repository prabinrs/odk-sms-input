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
  
  public SmsDataRecord convertToDataRecord(
      OdkSms odkSms,
      boolean wasParsed,
      boolean wasTallied) {
    
    SmsDataRecord result = new SmsDataRecord(odkSms, wasParsed, wasTallied);
    
    return result;
    
  }
  
  /**
   * 
   * @param smsMessages
   * @return
   */
  public List<SmsDataRecord> getOdkSms(
      SmsMessage[] smsMessages,
      boolean wasParsed,
      boolean wasTallied) {
    
    List<SmsDataRecord> result = new ArrayList<SmsDataRecord>();
    
    for (SmsMessage message : smsMessages) {
      OdkSms odkSms = this.convertToOdkSms(message);
      
      SmsDataRecord record = this.convertToDataRecord(
          odkSms,
          wasParsed,
          wasTallied);
      
      result.add(record);
    }
    
    return result;
    
  }

}
