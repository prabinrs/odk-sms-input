package org.opendatakit.smsinput.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author sudar.sam@gmail.com
 *
 */
public class ToastUtil {
  
  public static void doShort(Context context, int resourceId) {
    Toast.makeText(
        context,
        resourceId,
        Toast.LENGTH_SHORT)
      .show();
  }

}
