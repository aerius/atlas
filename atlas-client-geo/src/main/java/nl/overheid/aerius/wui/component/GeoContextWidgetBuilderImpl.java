package nl.overheid.aerius.wui.component;

import com.google.inject.Inject;

import nl.overheid.aerius.shared.domain.PanelConfiguration;
import nl.overheid.aerius.wui.atlas.factories.ContextWidgetFactory;
import nl.overheid.aerius.wui.atlas.factories.GeoContextWidgetFactory;
import nl.overheid.aerius.wui.atlas.ui.context.MonitorUpPanel;

public class GeoContextWidgetBuilderImpl implements ContextWidgetBuilder {
  @Inject private GeoContextWidgetFactory geoFactory;
  @Inject private ContextWidgetFactory factory;

  @Override
  public MonitorUpPanel createContextPanel(final PanelConfiguration conf) {
    final MonitorUpPanel panel;

    switch (conf.asConfigurationProperties().getPanelType()) {
    case EXPORT:
      panel = factory.getContextExportView(conf);
      break;
    case INFO:
      panel = factory.getContextInfoView(conf);
      break;
    case META:
      panel = factory.getContextMetaView(conf);
      break;
    case PREFERENCES:
      panel = factory.getContextPreferencesView(conf);
      break;
    case LAYERS:
      panel = geoFactory.getContextLayerView(conf);
      break;
    case MAP:
      panel = geoFactory.getContextMapView(conf);
      break;
    case LOCATION:
      panel = geoFactory.getContextLocationView(conf);
      break;
    default:
      throw new RuntimeException("Option not implemented: " + conf.asConfigurationProperties().getPanelType());
    }

    return panel;
  }
}