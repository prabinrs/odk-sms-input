package org.opendatakit.smsinput.component;

import java.util.List;

import org.opendatakit.smsinput.R;
import org.opendatakit.smsinput.model.SmsDataRecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class SmsDataRecordAdapter extends BaseAdapter implements ListAdapter {
  
  private List<SmsDataRecord> mRecords;
  
  public SmsDataRecordAdapter(List<SmsDataRecord> records) {
    this.mRecords = records;
  }

  @Override
  public int getCount() {
    return mRecords.size();
  }

  @Override
  public Object getItem(int position) {
    return this.mRecords.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View result = convertView;
    if (result == null) {
      result = this.createView(parent);
    }
    
    SmsDataRecord record = (SmsDataRecord) this.getItem(position);
    
    TextView senderView =
        (TextView) result.findViewById(R.id.sms_data_record_sender);
    TextView messageBodyView =
        (TextView) result.findViewById(R.id.sms_data_record_message_body);
    
    senderView.setText(record.getOdkSms().getSender());
    messageBodyView.setText(record.getOdkSms().getMessageBody());
    
    return result;
    
  }
  
  protected View createView(ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View result = inflater.inflate(
        R.layout.sms_data_record_view,
        parent,
        false);
    return result;
  }

}
