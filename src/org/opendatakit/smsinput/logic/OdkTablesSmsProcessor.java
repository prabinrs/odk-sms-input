package org.opendatakit.smsinput.logic;

import org.opendatakit.common.android.utilities.ODKDatabaseUtils;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.model.OdkSms;

import android.database.sqlite.SQLiteDatabase;

/**
 * A hard-coded processor for ODK tables.
 * @author sudar.sam@gmail.com
 *
 */
public class OdkTablesSmsProcessor extends AppSmsProcessor {
  
  public OdkTablesSmsProcessor(
      String appId,
      ODKDatabaseUtils dbUtil,
      SQLiteDatabase tablesDatabase,
      ModelConverter converter,
      MessageParser parser) {
    super(appId, dbUtil, tablesDatabase, converter, parser);
  }
  
  @Override
  protected void handleSms(OdkSms odkSms) {
    super.handleSms(odkSms);
  }

}
