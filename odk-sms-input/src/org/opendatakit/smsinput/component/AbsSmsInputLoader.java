package org.opendatakit.smsinput.component;

/**
 * A skeleton implementation of a loader for use in the SMS Input app. Based
 * on the implementation outlined here:
 * http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
 * @author sudar.sam@gmail.com
 */
import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class AbsSmsInputLoader<T> extends AsyncTaskLoader<T> {
  
  protected T mData;

  public AbsSmsInputLoader(Context context) {
    super(context);
  }
  
  /**
   * Load the data. {@link #loadInBackground()} should call this. This method
   * is included for testing purposes.
   * @return
   */
  protected abstract T doSynchronousLoad();

  @Override
  public T loadInBackground() {
    this.mData = this.doSynchronousLoad();
    return this.mData;
  }
  
  @Override
  public void deliverResult(T data) {
    // This is based largely on the implementation linked above.
    if (isReset()) {
      // The load has been reset, so we'll ignore the result.
      releaseResources(data);
      return;
    }
    // Hold a reference to the old data so it doesn't get garbage collected.
    // We must protected it until the new data has been delivered.
    T oldData = this.mData;
    this.mData = data;
    if (isStarted()) {
      // If the loader is in a started state, deliver the result to the client.
      // the superclass method does this for us.
      super.deliverResult(data);
    }
    // Invalidate the old data because we don't need it anymore.
    if (oldData != null && oldData != data) {
      releaseResources(oldData);
    }
  }
  
  @Override
  protected void onStartLoading() {
    if (mData != null) {
      // Deliver any previously loaded data immediately.
      deliverResult(mData);
    }
    if (takeContentChanged() || mData == null) {
      // When the observer detects a change, it should call onContentChanged
      // on the loader, which will cause the next call to takeContentChanged
      // to return true. If this is ever the case (or if the current data is
      // null, we force a new load.
      forceLoad();
    }
  }
  
  
  @Override
  protected void onStopLoading() {
    // The loader is in a stopped state, so we should attempt to cancel the
    // current laod (if there is one).
    cancelLoad();
    // Note that we leave the observer as it is. Loaders in a stopped state
    // should still monitor the data source for changes so that the loader will
    // know to force a new load if it is ever started again.
  }
  
  
  @Override
  protected void onReset() {
    // ensure the loader has been stopped.
    onStopLoading();
    // at this point we can release the resources associated with mData
    if (mData != null) {
      releaseResources(mData);
      this.mData = null;
    }
    // the loader is being reset, so we should stop monitoring for changes
    // unregister any receivers here.
  }
  
  
  @Override
  public void onCanceled(T data) {
    // attempt to cancle the current async load
    super.onCanceled(data);
    // The load has been canceled, so we should release the resources
    // associated with data.
    releaseResources(data);
  }
  
  protected void releaseResources(T data) {
    // Nothing to do here, since we just have a list.
  }
  

  

}
