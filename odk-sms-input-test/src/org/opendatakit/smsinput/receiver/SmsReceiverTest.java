package org.opendatakit.smsinput.receiver;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.smsinput.app.OdkAppMessageProcessor;
import org.opendatakit.smsinput.logic.SmsInputAppProcessor;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.model.OdkSms;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.TestUtil;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * This is based in part on the very helpful tutorial found here:
 * http://raptordigital.blogspot.com/2014/02/robolectric-broadcastreceiver-and.html
 * @author sudar.sam@gmail.com
 *
 */
@RunWith(RobolectricTestRunner.class)
public class SmsReceiverTest {
  
  SmsReceiver receiverStub;
  
  BundleUtil mockBundleUtil;
  ModelConverter mockConverter;
  SmsInputAppProcessor mockSmsInputProcessor;
  OdkAppMessageProcessor mockOdkAppProcessor;
  Context context;
  
  @Before
  public void before() {
    this.context = Robolectric.application;
    
    this.mockBundleUtil = mock(BundleUtil.class);
    this.mockConverter = mock(ModelConverter.class);
    this.mockSmsInputProcessor = mock(SmsInputAppProcessor.class);
    this.mockOdkAppProcessor = mock(OdkAppMessageProcessor.class);
    
    SmsReceiverStub.BUNDLE_UTIL = this.mockBundleUtil;
    SmsReceiverStub.MODEL_CONVERTER = this.mockConverter;
    SmsReceiverStub.SMS_INPUT_PROCESSOR = this.mockSmsInputProcessor;
    SmsReceiverStub.ODK_APP_MESSAGE_PROCESSOR = this.mockOdkAppProcessor;
    
    this.receiverStub = new SmsReceiverStub();
    
  }
  
  @After
  public void after() {
    SmsReceiverStub.resetState();
  }
  
  /**
   * Create an intent that the receiver thinks it can process.
   * @return
   */
  protected Intent createValidIntent() {
    Intent result = new Intent();
    Bundle nonNullExtras = new Bundle();
    nonNullExtras.putString("test_key", "test_value");
    
    result.putExtras(nonNullExtras);
    
    return result;
  }
  
  @Test
  public void onReceiveCallsCorrectMethods() {
    
    this.receiverStub = spy(this.receiverStub);
    
    Intent validIntent = this.createValidIntent();
    
    Bundle bundle = validIntent.getExtras();
    
    SmsMessage[] mockSmsMessages = new SmsMessage[2];
    mockSmsMessages[0] = mock(SmsMessage.class);
    mockSmsMessages[1] = mock(SmsMessage.class);
    
    List<OdkSms> odkMessages = TestUtil.getTestOdkSmsMessages();
    
    doReturn(mockSmsMessages)
      .when(this.mockBundleUtil)
      .getMessageFromBundle(bundle);
    
    doReturn(odkMessages)
      .when(this.mockConverter)
      .convertToOdkSms(mockSmsMessages);
    
    doNothing()
      .when(this.receiverStub)
      .doSmsInputProcessing(this.context, odkMessages);
    
    doNothing()
      .when(this.receiverStub)
      .doAppSpecificProcessing(this.context, odkMessages);
    
    this.receiverStub.onReceive(this.context, validIntent);
    
    verify(this.receiverStub, times(1)).doSmsInputProcessing(
        this.context,
        odkMessages);
    
    verify(this.receiverStub, times(1)).doAppSpecificProcessing(
        this.context,
        odkMessages);
    
  }
  
  @Test
  public void doSmsInputProcessingCorrect() {
    
    List<OdkSms> messages = TestUtil.getTestOdkSmsMessages();
    
    this.receiverStub.doSmsInputProcessing(this.context, messages);
    
    verify(this.mockSmsInputProcessor, times(1)).processSmsMessages(messages);
    
  }
  
  @Test
  public void doAppSpecificProcessingCorrect() {
    
    List<OdkSms> messages = TestUtil.getTestOdkSmsMessages();
    
    this.receiverStub.doAppSpecificProcessing(this.context, messages);
    
    verify(this.mockOdkAppProcessor, times(1)).processSmsMessages(messages);
    
  }
  
  @Test
  public void receiverIsRegistered() {
    
    List<ShadowApplication.Wrapper> receivers =
        Robolectric.getShadowApplication().getRegisteredReceivers();
    
    boolean foundReceiver = false;
    for (ShadowApplication.Wrapper wrapper : receivers) {
      BroadcastReceiver receiver = wrapper.getBroadcastReceiver();
      if (receiver.getClass().getSimpleName()
          .equals(SmsReceiver.class.getSimpleName())) {
        foundReceiver = true;
        break;
      }
    }
    
    assertThat(foundReceiver).isTrue();
    
  }

}
