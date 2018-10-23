// Generated code from Butter Knife. Do not modify!
package com.example.aletta.nokiaowerinternet.devicecommunication;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aletta.nokiaowerinternet.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceCommunicaionFragment_ViewBinding implements Unbinder {
  private DeviceCommunicaionFragment target;

  @UiThread
  public DeviceCommunicaionFragment_ViewBinding(DeviceCommunicaionFragment target, View source) {
    this.target = target;

    target.sendLight = Utils.findRequiredViewAsType(source, R.id.sendLight, "field 'sendLight'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceCommunicaionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sendLight = null;
  }
}
