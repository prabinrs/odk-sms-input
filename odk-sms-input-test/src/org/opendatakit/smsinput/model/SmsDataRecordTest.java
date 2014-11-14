package org.opendatakit.smsinput.model;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.opendatakit.smsinput.util.TestUtil;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class SmsDataRecordTest {
  
  SmsDataRecord dataRecord;
  
  OdkSms odkSms;
  boolean wasParsed;
  boolean wasTallied;
  
  @Before
  public void before() {
    
    this.odkSms = TestUtil.getTestOdkSmsMessages().get(0);
    this.wasParsed = true;
    this.wasTallied = true;
    
    this.dataRecord = new SmsDataRecord(
        this.odkSms,
        this.wasParsed,
        this.wasTallied);
    
  }
  
  @Test
  public void getOdkSmsCorrect() {
    assertThat(this.dataRecord.getOdkSms())
      .isSameAs(this.odkSms);
  }
  
  @Test
  public void getWasParsedCorrect() {
    assertThat(this.dataRecord.wasParsed()).isEqualTo(this.wasParsed);
  }
  
  @Test
  public void getWasTalliedCorrect() {
    assertThat(this.dataRecord.wasTallied()).isEqualTo(this.wasTallied);
  }

}
