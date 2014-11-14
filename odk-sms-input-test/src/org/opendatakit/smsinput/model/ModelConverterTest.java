package org.opendatakit.smsinput.model;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.smsinput.util.TestUtil;
import org.robolectric.RobolectricTestRunner;

import android.telephony.SmsMessage;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class ModelConverterTest {
  
  ModelConverter converter;
  
  /**
   * Very annoyingly, you can only create this from an array of pdus, it seems.
   * So we'll just mock one with its accessors.
   */
  SmsMessage mockSms;
  
  String messageBody;
  String originAddress;
  
  @Before
  public void before() {
    this.converter = new ModelConverter();
    
    this.messageBody = "test converter message body";
    this.originAddress = "origin address for converter test";
    
    this.mockSms = mock(SmsMessage.class);
    
    doReturn(this.messageBody)
      .when(this.mockSms)
      .getMessageBody();
    doReturn(this.originAddress)
      .when(this.mockSms)
      .getDisplayOriginatingAddress();
    
  }
  
  @Test
  public void convertToOdkSmsSucceeds() {
    
    OdkSms target = new OdkSms(this.originAddress, this.messageBody);
    
    OdkSms actual = this.converter.convertToOdkSms(this.mockSms);
    
    assertThat(actual).isEqualTo(target);
    
  }
  
  @Test
  public void convertToDataRecordSucceeds() {
    
    OdkSms message = TestUtil.getTestOdkSmsMessages().get(0);
    
    SmsDataRecord targetAllTrue = new SmsDataRecord(message, true, true);
    SmsDataRecord targetAllFalse = new SmsDataRecord(message, false, false);
    
    SmsDataRecord actualAllTrue = this.converter.convertToDataRecord(
        message,
        true,
        true);
    
    SmsDataRecord actualAllFalse = this.converter.convertToDataRecord(
        message,
        false,
        false);
    
    assertThat(actualAllTrue)
      .isEqualTo(targetAllTrue)
      .isEqualsToByComparingFields(targetAllTrue);
    
    assertThat(actualAllFalse)
      .isEqualTo(targetAllFalse)
      .isEqualsToByComparingFields(targetAllFalse);
    
  }

}
