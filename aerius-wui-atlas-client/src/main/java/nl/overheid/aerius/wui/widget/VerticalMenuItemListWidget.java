/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.overheid.aerius.wui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public abstract class VerticalMenuItemListWidget<T> extends AbstractMenuItemListWidget<VerticalMenuItemPopup<T>, T> {
  interface VerticalMenuItemListWidgetUiBinder extends UiBinder<Widget, VerticalMenuItemListWidget<?>> {}

  private static final VerticalMenuItemListWidgetUiBinder UI_BINDER = GWT.create(VerticalMenuItemListWidgetUiBinder.class);

  public VerticalMenuItemListWidget() {}

  public VerticalMenuItemListWidget(final String name, final String description) {
    super(name, description);
  }

  public VerticalMenuItemListWidget(final VerticalMenuItemPopup<T> popup) {
    super(popup);
  }

  @Override
  protected Widget createWidget() {
    return UI_BINDER.createAndBindUi(this);
  }

  @Override
  protected VerticalMenuItemPopup<T> createMenuPopup() {
    return new VerticalMenuItemPopup<T>();
  }
}
