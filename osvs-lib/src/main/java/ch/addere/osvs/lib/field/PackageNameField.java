package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.PackageNameFieldImpl;

/** The {@code name} field is a string identifying the library within its ecosystem. */
public interface PackageNameField extends EntryField<String> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param ecosystemName name of the ecosystem
   * @return valid {@code PackageNameField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static PackageNameField of(String ecosystemName) {
    return PackageNameFieldImpl.of(ecosystemName);
  }
}
