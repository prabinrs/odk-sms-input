package org.opendatakit.smsinput.activity;

import org.opendatakit.smsinput.R;
import org.opendatakit.smsinput.util.BundleUtil;
import org.opendatakit.smsinput.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.setShowMessagesHandler();
		this.setShowEnabledAppsHandler();
		
	}
	
	protected void setShowEnabledAppsHandler() {
	  
	  Button button = (Button) this.findViewById(R.id.show_enabled_apps);
	  
	  button.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        
        intent.setClass(
            WelcomeActivity.this,
            ListSmsEnabledAppsActivity.class);
        
        WelcomeActivity.this.startActivity(intent);
        
      }
    });
	  
	}
	
	protected void setShowMessagesHandler() {
	  Button button = (Button) this.findViewById(R.id.show_processed_messages);
	  
	  button.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Bundle extras = new Bundle();
        BundleUtil bundleUtil = new BundleUtil();
        
        bundleUtil.putAppIdInBundle(extras, Constants.DEFAULT_SMS_APP_ID);
        
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, ListAllMessagesActivity.class);
        
        intent.putExtras(extras);
        
        WelcomeActivity.this.startActivity(intent);
        
      }
    });
	  
	}
	
	

}
