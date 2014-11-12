package org.opendatakit.smsinput.model;

/**
 * The record of an SMS in the database. Essentially represents a row in the
 * database.
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class SmsDataRecord {

  private OdkSms mOdkSms;

  private boolean mWasParsed;
  private boolean mWasDealtWith;

  public SmsDataRecord(
      OdkSms odkSms,
      boolean wasParsed,
      boolean wasDealtWith) {
    this.mOdkSms = odkSms;
    this.mWasParsed = wasParsed;
    this.mWasDealtWith = wasDealtWith;
  }

  public OdkSms getmOdkSms() {
    return mOdkSms;
  }

  public boolean ismWasParsed() {
    return mWasParsed;
  }

  public boolean ismWasDealtWith() {
    return mWasDealtWith;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mOdkSms == null) ? 0 : mOdkSms.hashCode());
    result = prime * result + (mWasDealtWith ? 1231 : 1237);
    result = prime * result + (mWasParsed ? 1231 : 1237);
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
    SmsDataRecord other = (SmsDataRecord) obj;
    if (mOdkSms == null) {
      if (other.mOdkSms != null)
        return false;
    } else if (!mOdkSms.equals(other.mOdkSms))
      return false;
    if (mWasDealtWith != other.mWasDealtWith)
      return false;
    if (mWasParsed != other.mWasParsed)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SmsDataRecord [mOdkSms=" + mOdkSms + ", mWasParsed=" + mWasParsed + ", mWasDealtWith="
        + mWasDealtWith + "]";
  }
  
  

}
