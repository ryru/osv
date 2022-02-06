package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.RepoField;

import java.net.URL;
import java.util.Objects;

public final class RepoFieldImpl implements RepoField {

  private static final String JSON_KEY = "repo";

  private final URL url;

  private RepoFieldImpl(URL url) {
    Objects.requireNonNull(url, "argument url must not be null");

    this.url = url;
  }

  public static RepoField of(URL url) {
    return new RepoFieldImpl(url);
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

    RepoFieldImpl repoField = (RepoFieldImpl) o;

    return Objects.equals(url, repoField.url);
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
