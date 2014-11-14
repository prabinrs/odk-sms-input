package org.opendatakit.smsinput.model;


/**
 * An SMS message intended for ODK.
 * @author sudar.sam@gmail.com
 *
 */
public class OdkSms {
	
	private String mSender;
	private String mMessageBody;
	
	public OdkSms(String sender, String messageBody) {
		this.mSender = sender;
		this.mMessageBody = messageBody;
	}
	
	public String getSender() {
		return this.mSender;
	}
	
	public String getMessageBody() {
		return this.mMessageBody;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mMessageBody == null) ? 0 : mMessageBody.hashCode());
		result = prime * result + ((mSender == null) ? 0 : mSender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OdkSms other = (OdkSms) obj;
		if (mMessageBody == null) {
			if (other.mMessageBody != null)
				return false;
		} else if (!mMessageBody.equals(other.mMessageBody))
			return false;
		if (mSender == null) {
			if (other.mSender != null)
				return false;
		} else if (!mSender.equals(other.mSender))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OdkSms [mSender=" + mSender + ", mMessageBody=" + mMessageBody
				+ "]";
	}

}
