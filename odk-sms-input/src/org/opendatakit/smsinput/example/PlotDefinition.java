package org.opendatakit.smsinput.example;

import java.util.List;

import org.opendatakit.aggregate.odktables.rest.entity.Column;
import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.api.ITableDefinition;
import org.opendatakit.smsinput.app.ITableDefinitionFactory;

import android.database.sqlite.SQLiteDatabase;

/**
 * An implementation of the plot table.
 * @author sudar.sam@gmail.com
 *
 */
public class PlotDefinition implements ITableDefinition {
  
  private static final String TABLE_ID = "plot";
  
  /**
   * This is the full definition of the table. This will include all the column
   * names--even the ones our parsers don't care about.
   */
  private ITableDefinition mFullDefinition;
  
  /**
   * Some of the column names in the table. We don't need all of them, but just
   * the ones that we'll need to refer to directly by name.
   */
  public static class ColumnNames {
    public static final String PLOT_NAME = "plot_name";
    public static final String PLANTING = "planting";
  }
  
  public PlotDefinition(
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase appDatabase) {
    ITableDefinitionFactory factory = new ITableDefinitionFactory();
    
    this.mFullDefinition = factory.createTableDefinition(
        dbUtil,
        appDatabase,
        TABLE_ID);
    
  }

  @Override
  public String getTableId() {
    return this.mFullDefinition.getTableId();
  }

  @Override
  public List<Column> getColumns() {
    return this.mFullDefinition.getColumns();
  }

}
