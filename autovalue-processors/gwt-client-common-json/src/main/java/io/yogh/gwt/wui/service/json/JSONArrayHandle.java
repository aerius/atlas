package io.yogh.gwt.wui.service.json;

import java.util.Optional;
import java.util.function.Consumer;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class JSONArrayHandle {
  private final JSONArray inner;

  public JSONArrayHandle(final JSONArray inner) {
    this.inner = inner;
  }

  public void forEach(final Consumer<JSONObjectHandle> consumer) {
    for (int i = 0; i < size(); i++) {
      consumer.accept(getObject(i));
    }
  }

  public void forEachString(final Consumer<String> consumer) {
    for (int i = 0; i < size(); i++) {
      consumer.accept(getString(i));
    }
  }

  public void forEachInteger(final Consumer<Integer> consumer) {
    for (int i = 0; i < size(); i++) {
      consumer.accept(getInteger(i));
    }
  }

  public void forEachNumber(final Consumer<Double> consumer) {
    for (int i = 0; i < size(); i++) {
      consumer.accept(getNumber(i));
    }
  }

  public JSONValueHandle get(final int idx) {
    final JSONValue object = inner.get(idx);

    if (object == null) {
      throw new IllegalStateException("Did not encounter required item in array: " + idx + " from " + inner);
    }

    return new JSONValueHandle(object);
  }

  public JSONObjectHandle getObject(final int i) {
    final JSONValueHandle value = get(i);
    if (!value.isObject()) {
      return null;
    }

    return value.asObjectHandle();
  }

  public Optional<JSONObjectHandle> getObjectOptional(final int i) {
    try {
      final JSONValueHandle value = get(i);
      if (value.isObject()) {
        return Optional.of(value.asObjectHandle());
      } else {
        return Optional.empty();
      }
    } catch (final IllegalStateException e) {
      return Optional.empty();
    }

  }

  public String getString(final int i) {
    return get(i).asString();
  }

  public Integer getInteger(final int i) {
    return get(i).asInteger();
  }

  public Double getNumber(final int i) {
    return get(i).asNumber();
  }

  public JSONArray getInner() {
    return inner;
  }

  public int size() {
    return inner.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public static JSONArrayHandle fromJson(final JSONValue json) {
    return new JSONArrayHandle(json.isArray());
  }

  public static JSONArrayHandle fromText(final String text) {
    return fromJson(JSONParser.parseStrict(text));
  }
}