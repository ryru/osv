package ch.addere.osvs.lib.field.types;

import ch.addere.osvs.lib.impl.VersionImpl;

/** A single affected version in whatever version syntax is used by the given package ecosystem. */
public interface Version {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param version a {@link ch.addere.osvs.lib.field.types.Version}
   * @return valid {@code VersionsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static Version of(String version) {
    return VersionImpl.of(version);
  }

  /**
   * Retunrs a version string.
   *
   * @return version string
   */
  String getVersion();
}
