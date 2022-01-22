package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesUrlField;

import java.net.URL;
import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.ReferencesUrlField} implementation. */
public final class ReferencesUrlFieldImpl implements ReferencesUrlField {

  private static final String JSON_KEY = "url";

  private final URL url;

  private ReferencesUrlFieldImpl(String version, URL url) {
    Objects.requireNonNull(version, "argument version must not be null");
    Objects.requireNonNull(url, "argument url must not be null");

    this.url = url;
  }

  /** Create a new instance. */
  public static ReferencesUrlField of(URL url) {
    String latest = "1.1.0";
    return new ReferencesUrlFieldImpl(latest, url);
  }

  /** Create a new instance. */
  public static ReferencesUrlField of(String version, URL url) {
    return new ReferencesUrlFieldImpl(version, url);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public URL getValue() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReferencesUrlFieldImpl that = (ReferencesUrlFieldImpl) o;

    return Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(url);
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
