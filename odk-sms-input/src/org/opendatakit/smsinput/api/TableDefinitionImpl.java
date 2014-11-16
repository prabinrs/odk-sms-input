package org.opendatakit.smsinput.api;

import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;

/**
 * A very basic implementation of {@link ITableDefinition}.
 * @author sudar.sam@gmail.com
 *
 */
public class TableDefinitionImpl implements ITableDefinition {
  
  private String mTableId;
  private List<Column> mColumns;
  
  public TableDefinitionImpl(String tableId, List<Column> columns) {
    this.mTableId = tableId;
    this.mColumns = columns;
  }

  @Override
  public String getTableId() {
    return this.mTableId;
  }

  @Override
  public List<Column> getColumns() {
    return this.mColumns;
  }

}
