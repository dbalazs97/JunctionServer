// Generated code from Butter Knife. Do not modify!
package com.example.aletta.nokiaowerinternet.home.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aletta.nokiaowerinternet.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceListAdapter$DeviceViewHolder_ViewBinding implements Unbinder {
  private DeviceListAdapter.DeviceViewHolder target;

  @UiThread
  public DeviceListAdapter$DeviceViewHolder_ViewBinding(DeviceListAdapter.DeviceViewHolder target,
      View source) {
    this.target = target;

    target.deviceName = Utils.findRequiredViewAsType(source, R.id.deviceName, "field 'deviceName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceListAdapter.DeviceViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.deviceName = null;
  }
}
