package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.Reference;
import ch.addere.osvs.lib.impl.ReferencesTypeFieldImpl;

/** Specifies what kind of reference the URL is. */
public interface ReferencesTypeField extends EntryField<Reference> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param referenceType reference type
   * @return valid {@code ReferencesField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static ReferencesTypeField of(Reference referenceType) {
    return ReferencesTypeFieldImpl.of(referenceType);
  }
}
