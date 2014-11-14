package org.opendatakit.smsinput.api;

import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;

/**
 * The api for defining interactions with a table in an app database.
 * @author sudar.sam@gmail.com
 *
 */
public interface ITableDefinition {
  
  public String getTableId();
  
  public List<Column> getColumns();

}
