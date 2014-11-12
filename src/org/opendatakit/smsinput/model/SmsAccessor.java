package org.opendatakit.smsinput.model;

import java.util.List;

import android.telephony.SmsMessage;

/**
 * Read and write messages to/from the database.
 * @author sudar.sam@gmail.com
 *
 */
public class SmsAccessor {
	
	/**
	 * Get the messages that have not yet been dealt with. E.g. this might
	 * return the messages that have not attempted to be parsed and put into
	 * the database.
	 * @param appId
	 * @return
	 */
	public List<SmsDataRecord> getUntalliedMessages(String appId) {
		return null;
	}
	
	/**
	 * Get all the messages from the database.
	 * @param appId
	 * @return
	 */
	public List<SmsDataRecord> getAllMessages(String appId) {
		return null;
	}
	
	public void insertSmsDataRecord(SmsDataRecord record) {
	  throw new IllegalStateException("unimplemented");
	}

}
