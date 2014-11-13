package org.opendatakit.smsinput.util;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;
import org.opendatakit.common.android.data.ColumnDefinition;

/**
 * Converts things from AndroidCommon that don't have anything to do with the
 * logic behind the app itself.
 * @author sudar.sam@gmail.com
 *
 */
public class AndroidCommonConverter {
  
  public ColumnDefinition getColumnDefinitionFromColumn(Column column) {
    ColumnDefinition result = new ColumnDefinition(
        column.getElementKey(),
        column.getElementName(),
        column.getElementType(),
        column.getListChildElementKeys());
    
    return result;
  }
  
  /**
   * Convenience method for calling {@link getColumnDefinitionFromColumn}.
   * @param columns
   * @return
   */
  public List<ColumnDefinition> getColumnDefinitionsFromColumns(
      List<Column> columns) {
    
    List<ColumnDefinition> result = new ArrayList<ColumnDefinition>();
    
    for (Column column : columns) {
      result.add(this.getColumnDefinitionFromColumn(column));
    }
    
    return result;
    
  }

}
