package org.opendatakit.smsinput.util;

import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;
import org.opendatakit.smsinput.api.ITableDefinition;

/**
 * Util functions associated with the android common project.
 * @author sudar.sam@gmail.com
 *
 */
public class AndroidCommonUtil {
  
  /**
   * Conver the list of columns to an array of element keys.
   * @param tableDefinition
   * @return
   */
  public String[] getElementKeysForSelection(
      ITableDefinition tableDefinition) {
    
    List<Column> columns = tableDefinition.getColumns();
    
    String[] result = new String[columns.size()];
    
    for (int i = 0; i < result.length; i++) {
      
      result[i] = columns.get(i).getElementKey();
      
    }
    
    return result;
    
  }
  
  /**
   * Get a boolean from the int representation in the database.
   * @param intValue
   * @return
   */
  public boolean getBooleanFromInt(int intValue) {
    boolean result = intValue == 0 ? false : true;
    return result;
  }

}
