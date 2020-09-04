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
package nl.overheid.aerius.shared.domain.properties;

import java.util.HashMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.LegendContentType;

public class LegendProperties extends Properties {
  private static final String CONTENT_TYPE = "content_type";

  public LegendProperties() {
    this(new HashMap<>());
  }

  public LegendProperties(final Map<String, Object> map) {
    super(map);
  }

  public LegendContentType getContentType() {
    return LegendContentType.valueOf(getRequiredString(CONTENT_TYPE).toUpperCase());
  }

  public void setContentType(final LegendContentType type) {
    put(CONTENT_TYPE, type.name());
  }

  public LegendComponentProperties asComponentProperties() {
    return new LegendComponentProperties(map);
  }

  public LegendTextProperties asTextProperties() {
    return new LegendTextProperties(map);
  }
}