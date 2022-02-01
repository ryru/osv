package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.Version;
import ch.addere.osvs.lib.impl.VersionsFieldImpl;

import java.util.List;

/**
 * The {@code versions} field takes a list of {@link ch.addere.osvs.lib.field.types.Version}s. Each
 * string is a single affected version in whatever version syntax is used by the given package
 * ecosystem.
 */
public interface VersionsField extends EntryField<List<Version>> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param versions one or several {@link ch.addere.osvs.lib.field.types.Version}s
   * @return valid {@code VersionsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static VersionsField of(Version... versions) {
    return VersionsFieldImpl.of(versions);
  }
}
