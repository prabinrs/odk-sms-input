package org.opendatakit.smsinput.model;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Too basic to need this. Including it to encourage tests if it becomes more
 * complicated. 
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class OdkSmsTest {
  
  OdkSms odkSms;
  
  String sender = "I am the sender";
  String body = "I am the body";
  
  @Before
  public void before() {
    this.odkSms = new OdkSms(this.sender, this.body);
  }
  
  @Test
  public void getSenderCorrect() {
    assertThat(this.odkSms.getSender()).isEqualTo(this.sender);
  }
  
  @Test
  public void getBodyCorrect() {
    assertThat(this.odkSms.getMessageBody()).isEqualTo(this.body);
  }

}
