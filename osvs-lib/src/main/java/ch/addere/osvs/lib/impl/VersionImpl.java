package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.Version;

import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.types.Version} implementation. */
public final class VersionImpl implements Version {

  private final String version;

  private VersionImpl(String version) {
    Objects.requireNonNull(version, "argument version must not be null");

    this.version = version;
  }

  /** Create a new instance. */
  public static Version of(String version) {
    return new VersionImpl(version);
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VersionImpl version1 = (VersionImpl) o;

    return version.equals(version1.version);
  }

  @Override
  public int hashCode() {
    return version.hashCode();
  }

  @Override
  public String toString() {
    return version;
  }
}
