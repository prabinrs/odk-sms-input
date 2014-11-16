package org.opendatakit.smsinput.api;

import org.opendatakit.smsinput.model.OdkSms;

import android.content.ContentValues;

/**
 * A message parser for a table.
 * @author sudar.sam@gmail.com
 *
 */
public interface IMessageParser {
  
  public abstract boolean isForThisParser(OdkSms sms);
  
  public abstract ContentValues extractValues(OdkSms sms);
  

}
