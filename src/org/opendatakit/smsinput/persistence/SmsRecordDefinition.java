package org.opendatakit.smsinput.persistence;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;
import org.opendatakit.common.android.data.ElementDataType;

public class SmsRecordDefinition {
  
  private static final String TABLE_ID = "sms_records";
  
  public static class ColumnNames {
    public static final String WAS_PARSED = "was_parsed";
    public static final String WAS_TALLIED = "was_tallied";
    public static final String SENDING_ADDRESS = "sending_address";
    public static final String MESSAGE_BODY = "message_body";
  }
  
  public String getTableId() {
    return TABLE_ID;
  }
  
  public List<Column> getColumns() {
    List<Column> result = new ArrayList<Column>();
    
    Column parsed = new Column(
        ColumnNames.WAS_PARSED,
        ColumnNames.WAS_PARSED,
        ElementDataType.string.name(),
        null);
    
    Column tallied = new Column(
        ColumnNames.WAS_TALLIED,
        ColumnNames.WAS_TALLIED,
        ElementDataType.string.name(),
        null);
    
    Column address = new Column(
        ColumnNames.SENDING_ADDRESS,
        ColumnNames.SENDING_ADDRESS,
        ElementDataType.string.name(),
        null);
    
    Column messageBody = new Column(
        ColumnNames.MESSAGE_BODY,
        ColumnNames.MESSAGE_BODY,
        ElementDataType.string.name(),
        null);
    
    result.add(parsed);
    result.add(tallied);
    result.add(address);
    result.add(messageBody);
    
    return result;
    
  }

}
