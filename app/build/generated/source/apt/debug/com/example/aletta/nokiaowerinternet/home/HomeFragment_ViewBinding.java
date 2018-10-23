// Generated code from Butter Knife. Do not modify!
package com.example.aletta.nokiaowerinternet.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aletta.nokiaowerinternet.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.fabConnect = Utils.findRequiredViewAsType(source, R.id.idConnect, "field 'fabConnect'", FloatingActionButton.class);
    target.parentHome = Utils.findRequiredViewAsType(source, R.id.parentHome, "field 'parentHome'", ConstraintLayout.class);
    target.searchTextView = Utils.findRequiredViewAsType(source, R.id.searchTextView, "field 'searchTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fabConnect = null;
    target.parentHome = null;
    target.searchTextView = null;
  }
}
