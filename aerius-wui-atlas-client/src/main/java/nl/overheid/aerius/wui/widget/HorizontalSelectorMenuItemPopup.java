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

import nl.overheid.aerius.shared.domain.Selector;

public class HorizontalSelectorMenuItemPopup<T extends Selector> extends HorizontalMenuItemPopup<T> {
  public HorizontalSelectorMenuItemPopup() {}

  public HorizontalSelectorMenuItemPopup(final String titleTxt, final String descriptionTxt) {
    super(titleTxt, descriptionTxt);
  }

  @Override
  protected MaskedButton<T> createMaskedButton(final T item) {
    return new SelectorPopupControl<T>(item, this);
  }
}
