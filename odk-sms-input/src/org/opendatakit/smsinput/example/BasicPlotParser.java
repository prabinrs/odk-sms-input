package org.opendatakit.smsinput.example;

import org.opendatakit.smsinput.api.IMessageParser;
import org.opendatakit.smsinput.model.OdkSms;

import android.content.ContentValues;

/**
 * An example parser. Expects a message of the format:
 * "plot plot_name planting". All single words, separated by spaces.
 * @author sudar.sam@gmail.com
 *
 */
public class BasicPlotParser implements IMessageParser {

  @Override
  public boolean isForThisParser(OdkSms sms) {
    return sms.getMessageBody().startsWith("plot");
  }

  @Override
  public ContentValues extractValues(OdkSms sms) {
    
    String[] brokenOnSpaces = sms.getMessageBody().split(" ");
    
    ContentValues result = new ContentValues();
    
    result.put(PlotDefinition.ColumnNames.PLOT_NAME, brokenOnSpaces[1]);
    result.put(PlotDefinition.ColumnNames.PLANTING, brokenOnSpaces[2]);
    
    return result;
    
  }

}
