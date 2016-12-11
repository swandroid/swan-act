// Generated code from Butter Knife. Do not modify!
package interdroid.swan.ttn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PacketAdapter$ViewHolder$$ViewBinder<T extends interdroid.swan.ttn.PacketAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689735, "field 'mTime'");
    target.mTime = finder.castView(view, 2131689735, "field 'mTime'");
    view = finder.findRequiredView(source, 2131689736, "field 'mData'");
    target.mData = finder.castView(view, 2131689736, "field 'mData'");
    view = finder.findRequiredView(source, 2131689734, "field 'mDeviceId'");
    target.mDeviceId = finder.castView(view, 2131689734, "field 'mDeviceId'");
  }

  @Override public void unbind(T target) {
    target.mTime = null;
    target.mData = null;
    target.mDeviceId = null;
  }
}
