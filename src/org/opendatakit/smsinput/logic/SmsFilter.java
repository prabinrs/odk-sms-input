package org.opendatakit.smsinput.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.ModelConverter;

import android.telephony.SmsMessage;

/**
 * Converts SMS messages from all incoming SMSs to those that are meant for
 * ODK. Also handles dividing things up by app name, etc.
 * @author sudar.sam@gmail.com
 *
 */
public class SmsFilter {
  
  private ModelConverter mConverter;
  private MessageParser mParser;
  
  public SmsFilter(ModelConverter converter, MessageParser parser) {
    this.mConverter = converter;
    this.mParser = parser;
  }
  
  /**
   * Separate the list of messages into the messages intended for a given app.
   * @param odkMessages
   * @return
   */
  public Map<String, List<OdkSms>> getAppIdToMessages(
      List<OdkSms> odkMessages) {
    Map<String, List<OdkSms>> result = new HashMap<String, List<OdkSms>>();
    
    for (OdkSms sms : odkMessages) {
      String appId = this.mParser.getAppId(sms);
      
      if (!result.containsKey(appId)) {
        List<OdkSms> messagesForApp = new ArrayList<OdkSms>();
        messagesForApp.add(sms);
        result.put(appId, messagesForApp);
      } else {
        result.get(appId).add(sms);
      }
      
    }
    
    return result;
    
  }
  
  /**
   * Get the messages that are intended for ODK.
   * @param messages
   * @return
   */
  public List<OdkSms> getMessagesForOdk(SmsMessage[] messages) {
    List<OdkSms> convertedMessages = this.convertToOdkMessages(messages);
    List<OdkSms> result = this.getMessagesForOdk(convertedMessages);
    
    return result;
  }
  
  /**
   * Convert the {@link SmsMessage}s to {@link OdkSms}.
   * @param messages
   * @return
   */
  public List<OdkSms> convertToOdkMessages(SmsMessage[] messages) {
    List<OdkSms> odkMessages = new ArrayList<OdkSms>();
    
    for (SmsMessage message : messages) {
      OdkSms odkSms = this.mConverter.convertToOdkSms(message);
      odkMessages.add(odkSms);
    }
    
    return odkMessages;
    
  }
  
  /**
   * Get the SMS messages meant for ODK.
   * @param messages
   * @return
   */
  protected List<OdkSms> getMessagesForOdk(List<OdkSms> messages) {
    List<OdkSms> result = new ArrayList<OdkSms>();
    for (OdkSms sms : messages) {
      if (this.mParser.isForOdk(sms)) {
        result.add(sms);
      }
    }
    return result;
  }

}
