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
package nl.overheid.aerius.shared.domain;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AutoValueSelector {
  public static Builder builder() {
    return new AutoValue_AutoValueSelector.Builder();
  }

  public abstract String type();

  public abstract String title();

  public abstract String value();

  public abstract ArrayList<AutoValueSelector> selectors();

  public abstract HashMap<String, String> tags();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder type(String value);

    public abstract Builder title(String value);

    public abstract Builder value(String value);

    public abstract Builder selectors(ArrayList<AutoValueSelector> value);

    public abstract Builder tags(HashMap<String, String> value);

    public abstract AutoValueSelector build();
  }

}