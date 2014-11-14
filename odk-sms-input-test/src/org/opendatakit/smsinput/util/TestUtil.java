package org.opendatakit.smsinput.util;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.smsinput.model.OdkSms;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class TestUtil {
  
  /**
   * Gets a list with two messages.
   * @return
   */
  public static List<OdkSms> getTestOdkSmsMessages() {
    List<OdkSms> result = new ArrayList<OdkSms>();
    
    OdkSms one = new OdkSms("3605551234", "Test message body one.");
    OdkSms two = new OdkSms(
        "2065559876",
        "I hear cell phones give you cancer.");
    
    result.add(one);
    result.add(two);
    
    return result;
    
  }

}
