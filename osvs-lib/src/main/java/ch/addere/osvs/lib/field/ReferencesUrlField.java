package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.ReferencesUrlFieldImpl;

import java.net.URL;

/**
 * The fully-qualified URL (including the scheme, typically “https://”) linking to additional
 * information, advisories, issue tracker entries, and so on about the vulnerability itself.
 */
public interface ReferencesUrlField extends EntryField<URL> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param url fully-qualified URL
   * @return valid {@code ReferencesUrlField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static ReferencesUrlField of(URL url) {
    return ReferencesUrlFieldImpl.of(url);
  }

  /**
   * Create a new instance valid according the {@code version} specification version.
   *
   * @param version specification version this instance is validated against
   * @param url fully-qualified URL
   * @return valid {@code ReferencesUrlField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static ReferencesUrlField of(String version, URL url) {
    return ReferencesUrlFieldImpl.of(version, url);
  }
}
