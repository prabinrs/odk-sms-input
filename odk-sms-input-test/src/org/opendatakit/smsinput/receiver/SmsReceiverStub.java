package org.opendatakit.smsinput.receiver;

import org.opendatakit.smsinput.app.OdkAppMessageProcessor;
import org.opendatakit.smsinput.app.OdkAppReader;
import org.opendatakit.smsinput.logic.SmsInputAppProcessor;
import org.opendatakit.smsinput.model.ModelConverter;
import org.opendatakit.smsinput.util.BundleUtil;

import android.content.Context;

public class SmsReceiverStub extends SmsReceiver {
  
  public static final BundleUtil DEFAULT_BUNDLE_UTIL = null;
  public static final ModelConverter DEFAULT_MODEL_CONVERTER = null;
  public static final SmsInputAppProcessor DEFAULT_SMS_INPUT_PROCESSOR = null;
  public static final OdkAppMessageProcessor
    DEFAULT_ODK_APP_MESSAGE_PROCESSOR = null;
  
  public static BundleUtil BUNDLE_UTIL = DEFAULT_BUNDLE_UTIL;
  public static ModelConverter MODEL_CONVERTER = DEFAULT_MODEL_CONVERTER;
  public static SmsInputAppProcessor SMS_INPUT_PROCESSOR =
      DEFAULT_SMS_INPUT_PROCESSOR;
  public static OdkAppMessageProcessor ODK_APP_MESSAGE_PROCESSOR =
      DEFAULT_ODK_APP_MESSAGE_PROCESSOR;
  
  public static void resetState() {
    BUNDLE_UTIL = DEFAULT_BUNDLE_UTIL;
    MODEL_CONVERTER = DEFAULT_MODEL_CONVERTER;
    SMS_INPUT_PROCESSOR = DEFAULT_SMS_INPUT_PROCESSOR;
    ODK_APP_MESSAGE_PROCESSOR = DEFAULT_ODK_APP_MESSAGE_PROCESSOR;
  }
  
  @Override
  protected BundleUtil createBundleUtil() {
    return BUNDLE_UTIL;
  }
  
  @Override
  protected ModelConverter createModelConverter() {
    return MODEL_CONVERTER;
  }
  
  @Override
  protected SmsInputAppProcessor createSmsInputProcessor(Context context) {
    return SMS_INPUT_PROCESSOR;
  }
  
  @Override
  protected OdkAppMessageProcessor createAllAppProcessor(OdkAppReader appReader) {
    return ODK_APP_MESSAGE_PROCESSOR;
  }

}
