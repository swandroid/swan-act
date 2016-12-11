// Generated code from Butter Knife. Do not modify!
package interdroid.swan.ttn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NodeAdapter$ViewHolder$$ViewBinder<T extends interdroid.swan.ttn.NodeAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689729, "field 'mNodeId'");
    target.mNodeId = finder.castView(view, 2131689729, "field 'mNodeId'");
    view = finder.findRequiredView(source, 2131689730, "field 'mLastSeen'");
    target.mLastSeen = finder.castView(view, 2131689730, "field 'mLastSeen'");
    view = finder.findRequiredView(source, 2131689732, "field 'mPacketsCount'");
    target.mPacketsCount = finder.castView(view, 2131689732, "field 'mPacketsCount'");
    view = finder.findRequiredView(source, 2131689731, "field 'mLastGatewayEui'");
    target.mLastGatewayEui = finder.castView(view, 2131689731, "field 'mLastGatewayEui'");
  }

  @Override public void unbind(T target) {
    target.mNodeId = null;
    target.mLastSeen = null;
    target.mPacketsCount = null;
    target.mLastGatewayEui = null;
  }
}
