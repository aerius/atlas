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
package nl.overheid.aerius.wui.atlas.dev;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.google.web.bindery.event.shared.binder.GenericEvent;

import nl.overheid.aerius.wui.atlas.command.ActivateBigContextCommand;
import nl.overheid.aerius.wui.atlas.command.ActivateSmallContextCommand;
import nl.overheid.aerius.wui.atlas.command.BroadcastSelectorsCommand;
import nl.overheid.aerius.wui.atlas.command.ChapterReplacementCommand;
import nl.overheid.aerius.wui.atlas.command.ChapterReplacementEvent;
import nl.overheid.aerius.wui.atlas.command.ChapterSelectionChangeCommand;
import nl.overheid.aerius.wui.atlas.command.CompactModeActivationCommand;
import nl.overheid.aerius.wui.atlas.command.CompactModeDeactivationCommand;
import nl.overheid.aerius.wui.atlas.command.ConfigurationPropertyChangeCommand;
import nl.overheid.aerius.wui.atlas.command.ContextPanelCollapseCommand;
import nl.overheid.aerius.wui.atlas.command.ContextPanelOpenCommand;
import nl.overheid.aerius.wui.atlas.command.DataSetChangeCommand;
import nl.overheid.aerius.wui.atlas.command.NoStoryCommand;
import nl.overheid.aerius.wui.atlas.command.PanelSelectionChangeCommand;
import nl.overheid.aerius.wui.atlas.command.SelectionCommand;
import nl.overheid.aerius.wui.atlas.command.SelectionEvent;
import nl.overheid.aerius.wui.atlas.command.SelectorCommand;
import nl.overheid.aerius.wui.atlas.command.StatelessCommand;
import nl.overheid.aerius.wui.atlas.command.StoryFilterSelectionChangeCommand;
import nl.overheid.aerius.wui.atlas.command.StorySelectionChangeCommand;
import nl.overheid.aerius.wui.atlas.command.ToggleLayerPanelCommand;
import nl.overheid.aerius.wui.atlas.command.UserAuthorizationChangedCommand;
import nl.overheid.aerius.wui.atlas.daemon.library.LibraryStatusChangedEvent;
import nl.overheid.aerius.wui.atlas.event.ActivatorActiveEvent;
import nl.overheid.aerius.wui.atlas.event.ActivatorInactiveEvent;
import nl.overheid.aerius.wui.atlas.event.AdblockerDetectedEvent;
import nl.overheid.aerius.wui.atlas.event.ChapterSelectionChangeEvent;
import nl.overheid.aerius.wui.atlas.event.ContextOptionsChangedEvent;
import nl.overheid.aerius.wui.atlas.event.ContextPanelCollapseEvent;
import nl.overheid.aerius.wui.atlas.event.ContextPanelOpenEvent;
import nl.overheid.aerius.wui.atlas.event.DataSetChangeEvent;
import nl.overheid.aerius.wui.atlas.event.DataSetListChangeEvent;
import nl.overheid.aerius.wui.atlas.event.LibraryChangeEvent;
import nl.overheid.aerius.wui.atlas.event.MapSearchSuggestionEvent;
import nl.overheid.aerius.wui.atlas.event.PanelConfigurationChangeEvent;
import nl.overheid.aerius.wui.atlas.event.PanelSelectionChangeEvent;
import nl.overheid.aerius.wui.atlas.event.SelectorConfigurationChangeEvent;
import nl.overheid.aerius.wui.atlas.event.SelectorConfigurationClearEvent;
import nl.overheid.aerius.wui.atlas.event.SelectorConfigurationReloadEvent;
import nl.overheid.aerius.wui.atlas.event.SelectorEvent;
import nl.overheid.aerius.wui.atlas.event.StoryFilterSelectionChangeEvent;
import nl.overheid.aerius.wui.atlas.event.StoryFragmentChangedEvent;
import nl.overheid.aerius.wui.atlas.event.StoryLoadedEvent;
import nl.overheid.aerius.wui.atlas.event.StoryLoadingEvent;
import nl.overheid.aerius.wui.atlas.event.StoryOptionsChangedEvent;
import nl.overheid.aerius.wui.atlas.event.StorySelectionChangeEvent;
import nl.overheid.aerius.wui.atlas.event.ToggleLayerPanelEvent;
import nl.overheid.aerius.wui.atlas.event.UserAuthorizationChangedEvent;
import nl.overheid.aerius.wui.command.PlaceChangeCommand;
import nl.overheid.aerius.wui.command.SimpleCommand;
import nl.overheid.aerius.wui.command.SimpleGenericCommand;
import nl.overheid.aerius.wui.dev.DevelopmentObserver;
import nl.overheid.aerius.wui.dev.GWTProd;
import nl.overheid.aerius.wui.event.NotificationEvent;
import nl.overheid.aerius.wui.event.PlaceChangeEvent;
import nl.overheid.aerius.wui.event.SimpleGenericEvent;
import nl.overheid.aerius.wui.place.PlaceController;

@Singleton
public class AtlasDevelopmentObserver implements DevelopmentObserver {
  interface DevelopmentObserverMonitorImplEventBinder extends EventBinder<AtlasDevelopmentObserver> {}

  private final DevelopmentObserverMonitorImplEventBinder EVENT_BINDER = GWT.create(DevelopmentObserverMonitorImplEventBinder.class);
  private final EventBus eventBus;
  private boolean debugMode;
  private final PlaceController placeController;

  // private final Logger logger = Logger.getLogger("DevelopmentObserver");

  @Inject
  public AtlasDevelopmentObserver(final PlaceController placeController, final EventBus eventBus) {
    this.placeController = placeController;
    this.eventBus = eventBus;
    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @SuppressWarnings("rawtypes")
  @EventHandler(handles = { NotificationEvent.class, ChapterSelectionChangeEvent.class, ContextOptionsChangedEvent.class,
      PanelSelectionChangeEvent.class, DataSetChangeEvent.class, DataSetListChangeEvent.class, LibraryChangeEvent.class,
      StoryFragmentChangedEvent.class, StoryOptionsChangedEvent.class, StoryLoadedEvent.class, ToggleLayerPanelEvent.class,
      StorySelectionChangeEvent.class, SelectorConfigurationChangeEvent.class, UserAuthorizationChangedEvent.class,
      UserAuthorizationChangedEvent.class, StoryFilterSelectionChangeEvent.class, MapSearchSuggestionEvent.class, ActivatorActiveEvent.class,
      ActivatorInactiveEvent.class, ChapterReplacementEvent.class, SelectionEvent.class, SelectorConfigurationReloadEvent.class,
      PanelConfigurationChangeEvent.class
  })
  public void onSimpleGenericEvent(final SimpleGenericEvent e) {
    log(e.getClass().getSimpleName(), e.getValue());
  }

  @SuppressWarnings("rawtypes")
  @EventHandler(handles = { ChapterSelectionChangeCommand.class, PanelSelectionChangeCommand.class, DataSetChangeCommand.class,
      ToggleLayerPanelCommand.class, StorySelectionChangeCommand.class, UserAuthorizationChangedCommand.class, UserAuthorizationChangedCommand.class,
      StoryFilterSelectionChangeCommand.class, ChapterReplacementCommand.class, SelectionCommand.class })
  public void onSimpleGenericCommand(final SimpleGenericCommand c) {
    log(c.getClass().getSimpleName(), c.getValue());
  }

  @EventHandler
  public void onConfigurationPropertyChangeCommand(final ConfigurationPropertyChangeCommand c) {
    log("CONFIG", c.getKey() + " > " + c.getValue());
  }

  @EventHandler
  public void onLibraryStatusChangedEvent(final LibraryStatusChangedEvent e) {
    log(e.getClass().getSimpleName(), e.getValue() + " > " + e.isAvailable());
  }

  @SuppressWarnings("rawtypes")
  @EventHandler(handles = { ContextPanelOpenCommand.class, ContextPanelCollapseCommand.class })
  public void onSimpleGenericCommand(final SimpleCommand c) {
    log(c.getClass().getSimpleName());
  }

  @EventHandler(handles = { ContextPanelOpenEvent.class, ContextPanelCollapseEvent.class,
      NoStoryCommand.class, ActivateBigContextCommand.class, ActivateSmallContextCommand.class,
      AdblockerDetectedEvent.class })
  public void onSimpleGenericCommand(final GenericEvent c) {
    log(c.getClass().getSimpleName());
  }

  @EventHandler(handles = { CompactModeActivationCommand.class, CompactModeDeactivationCommand.class, BroadcastSelectorsCommand.class })
  public void onSimpleGenericCommand(final StatelessCommand c) {
    log(c.getClass().getSimpleName());
  }

  @EventHandler
  public void onSelectorCommand(final SelectorCommand c) {
    log(c.getClass().getSimpleName(), c.getSelector().getType() + " > " + c.getValue().getValue().orElse("N/A"));
  }

  @EventHandler
  public void onSelectorEvent(final SelectorEvent e) {
    log(e.getClass().getSimpleName(), e.getSelector().getType() + " > " + e.getValue().getValue().orElse("N/A"));
  }

  @EventHandler(handles = { StoryLoadingEvent.class, SelectorConfigurationClearEvent.class })
  public void onGenericEvent(final GenericEvent e) {
    log(e.getClass().getSimpleName());
  }

  @EventHandler
  public void onStoryChangeEvent(final StoryLoadedEvent e) {
    Scheduler.get().scheduleDeferred(() -> brbr());
  }

  @EventHandler
  public void onPlaceChangeCommand(final PlaceChangeCommand e) {
    log("PlaceChangeCommand", e.getValue());

    Scheduler.get().scheduleDeferred(() -> brbr());
  }

  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    log("PlaceChangeEvent", e.getValue());

    Scheduler.get().scheduleDeferred(() -> brbr());
  }

  private void brbr() {
    logRaw("");
  }

  private void log(final String origin) {
    logRaw("[" + origin + "]");
  }

  private void log(final String origin, final Object val) {
    logRaw("[" + origin + "] " + String.valueOf(val));
  }

  private void logRaw(final String string) {
    GWTProd.log(string);
  }
}