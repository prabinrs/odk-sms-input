package org.opendatakit.smsinput.logic;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.TestUtil;
import org.robolectric.RobolectricTestRunner;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class SmsFilterTest {
  
  SmsFilter filter;
  
  MessageParser mockParser;
  
  @Before
  public void before() {
    this.mockParser = mock(MessageParser.class);
    
    this.filter = new SmsFilter(this.mockParser);
  }
  
  @Test
  public void getAppIdToMessagesReturnsCorrectResult() {
    
    // We will have a list of four messages consisting of two copies each of
    // two different messages. The 0th we will say is for tables, while the 1st
    // we will say is for a testAppId.
    String tablesAppId = "tables";
    String testAppId = "smsfiltertest_appid";
    
    List<OdkSms> messages = TestUtil.getTestOdkSmsMessages();
    messages.addAll(TestUtil.getTestOdkSmsMessages());
    
    assertThat(messages).hasSize(4);
    
    OdkSms messageForTables = messages.get(0);
    OdkSms messageForTestApp = messages.get(1);
    
    doReturn(tablesAppId).when(this.mockParser).getAppId(eq(messageForTables));
    doReturn(testAppId).when(this.mockParser).getAppId(eq(messageForTestApp));
    
    List<OdkSms> targetTablesMessages = new ArrayList<OdkSms>();
    targetTablesMessages.add(messages.get(0));
    targetTablesMessages.add(messages.get(2));
    
    List<OdkSms> targetTestAppMessages = new ArrayList<OdkSms>();
    targetTestAppMessages.add(messages.get(1));
    targetTestAppMessages.add(messages.get(3));
    
    Map<String, List<OdkSms>> target = new HashMap<String, List<OdkSms>>();
    target.put(tablesAppId, targetTablesMessages);
    target.put(testAppId,targetTestAppMessages);
    
    Map<String, List<OdkSms>> actual =
        this.filter.getAppIdToMessages(messages);
    
    assertThat(actual)
      .hasSize(2)
      .isEqualTo(target);
    
  }
  
  @Test
  public void getMessagesForOdkReturnsCorrectResult() {
    
    // We will have a list of four messages consisting of two copies each of
    // two different messages. The 0th we will say is for ODK, while the 1st we
    // will say is not.
    List<OdkSms> messages = TestUtil.getTestOdkSmsMessages();
    
    messages.addAll(TestUtil.getTestOdkSmsMessages());
    
    assertThat(messages).hasSize(4);
    
    OdkSms messageForOdk = messages.get(0);
    OdkSms messageNotForOdk = messages.get(1);
    
    doReturn(true).when(this.mockParser).isForOdk(eq(messageForOdk));
    doReturn(false).when(this.mockParser).isForOdk(eq(messageNotForOdk));
    
    List<OdkSms> target = new ArrayList<OdkSms>();
    target.add(messageForOdk);
    target.add(messages.get(2));
    
    List<OdkSms> actual = this.filter.getMessagesForOdk(messages);
    
    assertThat(actual)
      .hasSameSizeAs(target)
      .isEqualTo(target);
    
  }
  
  

}
