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

public class MainComponentProperties extends MainProperties implements ComponentProperties {
  private final BasicComponentProperties componentProperties;

  public MainComponentProperties() {
    this(new HashMap<>());
  }

  public MainComponentProperties(final Map<String, Object> map) {
    super(map);

    System.out.println("Creating basic component properties with: " + map);

    componentProperties = new BasicComponentProperties(map);
  }

  @Override
  public String getComponentSource() {
    return componentProperties.getComponentSource();
  }

  @Override
  public String getComponentName() {
    return componentProperties.getComponentName();
  }

  @Override
  public String getUrl() {
    return componentProperties.getUrl();
  }

  public int getVersion() {
    return componentProperties.getVersion();
  }

  @Override
  public Map<String, String> getParameters() {
    return componentProperties.getParameters();
  }

  @Override
  public String toString() {
    return "MainComponentProperties [componentProperties=" + componentProperties + "]";
  }
}