package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.RepoFieldImpl;

import java.net.URL;

/**
 * The {@code ranges} object’s {@code repo} field is the URL of the package’s code repository. The
 * value should be in a format that’s directly usable as an argument for the version control
 * system’s clone command (e.g. {@code git clone}). This field is required if {@link
 * ch.addere.osvs.lib.field.types.RangeType} is GIT.
 */
public interface RepoField extends EntryField<URL> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param url a valid URL to a code repository
   * @return valid {@code EventsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static RepoField of(URL url) {
    return RepoFieldImpl.of(url);
  }
}
