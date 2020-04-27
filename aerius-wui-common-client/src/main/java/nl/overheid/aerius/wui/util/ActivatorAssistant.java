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

import java.util.List;
import java.util.function.Consumer;

import nl.overheid.aerius.wui.activators.IsActivatorInfo;
import nl.overheid.aerius.wui.widget.HasEventBus;

public interface ActivatorAssistant extends HasEventBus {
  <T extends IsActivatorInfo> ReplacementRegistration register(List<T> activators, Consumer<List<T>> consumer);
}
