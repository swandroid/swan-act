// Generated code from Butter Knife. Do not modify!
package interdroid.swan.ttn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TtnActivity$$ViewBinder<T extends interdroid.swan.ttn.TtnActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689737, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131689737, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131689729, "field 'mNodeEui'");
    target.mNodeEui = finder.castView(view, 2131689729, "field 'mNodeEui'");
    view = finder.findRequiredView(source, 2131689738, "field 'mDataList'");
    target.mDataList = finder.castView(view, 2131689738, "field 'mDataList'");
    view = finder.findRequiredView(source, 2131689740, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131689740, "field 'mProgressBar'");
  }

  @Override public void unbind(T target) {
    target.mToolbar = null;
    target.mNodeEui = null;
    target.mDataList = null;
    target.mProgressBar = null;
  }
}
