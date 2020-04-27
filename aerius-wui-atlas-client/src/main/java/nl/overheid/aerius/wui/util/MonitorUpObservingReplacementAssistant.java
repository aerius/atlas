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
package nl.overheid.aerius.wui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.overheid.aerius.geo.event.InfoLocationChangeEvent;
import nl.overheid.aerius.wui.atlas.command.ChapterReplacementEvent;
import nl.overheid.aerius.wui.atlas.command.StoryReplacementEvent;
import nl.overheid.aerius.wui.atlas.event.ActivatorActiveEvent;
import nl.overheid.aerius.wui.atlas.event.SelectorEvent;
import nl.overheid.aerius.wui.atlas.event.StoryLoadedEvent;

@Singleton
public class MonitorUpObservingReplacementAssistant implements ObservingReplacementAssistant {
  interface ObservingReplacementAssistantEventBinder extends EventBinder<MonitorUpObservingReplacementAssistant> {}

  private final ObservingReplacementAssistantEventBinder EVENT_BINDER = GWT.create(ObservingReplacementAssistantEventBinder.class);

  private class ReplacementItem {
    private final String template;
    private final Consumer<String> consumer;
    private final boolean strict;

    public ReplacementItem(final String template, final Consumer<String> consumer, final boolean strict) {
      this.template = template;
      this.consumer = consumer;
      this.strict = strict;
    }

    @Override
    public String toString() {
      return "ReplacementItem [template=" + template + ", strict=" + strict + "]";
    }
  }

  private final Map<Object, ReplacementItem> targets = new HashMap<>();
  private final Map<Object, String> cache = new HashMap<>();

  private final ReplacementAssistant replacer;

  private boolean scheduled;

  @Inject
  public MonitorUpObservingReplacementAssistant(final ReplacementAssistant replacer, final EventBus eventBus) {
    this.replacer = replacer;

    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @Override
  public ReplacementRegistration registerStrict(final String template, final Consumer<String> consumer) {
    return register(template, consumer, true);
  }

  @Override
  public ReplacementRegistration register(final String template, final Consumer<String> consumer) {
    return register(template, consumer, false);
  }

  private ReplacementRegistration register(final String template, final Consumer<String> consumer, final boolean strict) {
    final Object handle = new Object();

    // Add tracker
    final ReplacementItem item = new ReplacementItem(template, consumer, strict);
    targets.put(handle, item);

    // Add initial replacement to cache
    acceptReplacement(item, replace(item));

    // Return a replacement registration that the caller can use to remove the
    // tracking
    return () -> targets.remove(handle);
  }

  @EventHandler
  public void onSelectorEvent(final SelectorEvent e) {
    scheduleUpdate();
  }

  @EventHandler
  public void onChapterReplacementEvent(final ChapterReplacementEvent e) {
    scheduleUpdate();
  }

  @EventHandler
  public void onStoryReplacementEvent(final StoryReplacementEvent e) {
    scheduleUpdate();
  }

  @EventHandler
  public void onInfoLocationChangeEvent(final InfoLocationChangeEvent e) {
    scheduleUpdate();
  }

  @EventHandler
  public void onStoryLoadedEvent(final StoryLoadedEvent e) {
    scheduleUpdate();
  }

  @EventHandler
  public void onActivatorChangeEvent(final ActivatorActiveEvent e) {
    scheduleUpdate();
  }

  private void scheduleUpdate() {
    if (scheduled) {
      return;
    }

    scheduled = true;
    Scheduler.get().scheduleDeferred(() -> {
      updateNow();
      scheduled = false;
    });
  }

  private void updateNow() {
    for (final ReplacementItem item : targets.values()) {
      acceptReplacementCached(item, replace(item));
    }
  }

  private Optional<String> replace(final ReplacementItem item) {
    try {
      final String replaced = item.strict ? replacer.replaceStrict(item.template) : replacer.replace(item.template);

      return Optional.of(replaced);
    } catch (final IllegalStateException e) {
      return Optional.empty();
    }
  }

  private void acceptReplacementCached(final ReplacementItem item, final Optional<String> replacement) {
    if (cache.containsKey(item.consumer) && cache.get(item.consumer).equals(replacement.orElse(null))) {
      return;
    }

    acceptReplacement(item, replacement);
  }

  private void acceptReplacement(final ReplacementItem item, final Optional<String> replacement) {
    item.consumer.accept(replacement.orElse(null));
    if (replacement.isPresent()) {
      cache.put(item.consumer, replacement.get());
    } else {
      cache.remove(item.consumer);
    }
  }
}
