package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.Ecosystem;
import ch.addere.osvs.lib.impl.PackageTypeFieldImpl;

/** The ecosystem identifies the overall library ecosystem. */
public interface PackageTypeField extends EntryField<Ecosystem> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param ecosystemType type of ecosystem
   * @return valid {@code PackageTypeField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static PackageTypeField of(Ecosystem ecosystemType) {
    return PackageTypeFieldImpl.of(ecosystemType);
  }
}
