package org.opendatakit.smsinput.util;

import android.os.Bundle;
import android.telephony.SmsMessage;

public class BundleUtil {
  
  /**
   * Get any SMS messages from the given {@link Bundle}.
   * <p>
   * Taken from: https://web.archive.org/web/20121022021217/http://mobdev.olin.edu/mobdevwiki/FrontPage/Tutorials/SMS%20Messaging
   * @param bundle
   * @return
   */
  public SmsMessage[] getMessageFromBundle(Bundle bundle) {
    Object[] pdusObj = (Object[]) bundle.get("pdus");
    SmsMessage[] messages = new SmsMessage[pdusObj.length];

    // getting SMS information from Pdu.
    for (int i = 0; i < pdusObj.length; i++) {
      messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
    }
    
    return messages;
    
  }

}
