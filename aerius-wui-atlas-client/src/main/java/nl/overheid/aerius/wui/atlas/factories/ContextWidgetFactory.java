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
package nl.overheid.aerius.wui.atlas.factories;

import nl.overheid.aerius.shared.domain.PanelConfiguration;
import nl.overheid.aerius.wui.atlas.ui.context.export.ContextExportViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.info.ContextInfoViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.layer.ContextLayerViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.location.ContextLocationViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.map.ContextMapViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.meta.ContextMetaViewImpl;
import nl.overheid.aerius.wui.atlas.ui.context.preferences.ContextPreferencesViewImpl;

public interface ContextWidgetFactory {
  ContextInfoViewImpl getContextInfoView(PanelConfiguration config);

  ContextMetaViewImpl getContextMetaView(PanelConfiguration config);

  ContextMapViewImpl getContextMapView(PanelConfiguration config);

  ContextPreferencesViewImpl getContextPreferencesView(PanelConfiguration config);

  ContextExportViewImpl getContextExportView(PanelConfiguration config);

  ContextLayerViewImpl getContextLayerView(PanelConfiguration conf);

  ContextLocationViewImpl getContextLocationView(PanelConfiguration conf);
}
