package org.opendatakit.smsinput.api;

import java.util.List;

import org.opendatakit.smsinput.model.OdkSms;

/**
 * Defines actions that are taken while processing received sms messages.
 * @author sudar.sam@gmail.com
 *
 */
public interface ISmsProcessor {
  
  public void processSmsMessages(List<OdkSms> messages);

}
