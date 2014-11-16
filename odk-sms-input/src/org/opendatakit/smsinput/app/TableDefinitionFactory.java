package org.opendatakit.smsinput.app;

import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.api.TableDefinitionImpl;

import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class TableDefinitionFactory {
  
  public ITableDefinition createTableDefinition(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase appDatabase,
      String tableId) {
        
    List<Column> columns = dbUtil.getUserDefinedColumns(
        appDatabase,
        tableId);
    
    ITableDefinition result = new TableDefinitionImpl(tableId, columns);
    
    return result;
    
  }

}
