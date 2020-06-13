package io.yogh.gwt.wui.future;

import io.yogh.gwt.wui.service.BaseRequestCallback;

public abstract class SimpleDebuggableRequest extends BaseRequestCallback implements DebuggableRequest {
  protected String origin;

  @Override
  public void setRequestOrigin(final String origin) {
    this.origin = origin;
  }
}
