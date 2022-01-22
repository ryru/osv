package ch.addere.osvs.lib.field.types;

import ch.addere.osvs.lib.field.ReferencesTypeField;
import ch.addere.osvs.lib.field.ReferencesUrlField;
import ch.addere.osvs.lib.impl.ReferencesEntryImpl;

/** A reference entry used in {@link ch.addere.osvs.lib.field.ReferencesField}. */
public interface ReferencesEntry {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param referencesTypeField reference type of this reference entry
   * @param referencesUrlField reference URL of this reference entry
   * @return
   */
  static ReferencesEntry of(
      ReferencesTypeField referencesTypeField, ReferencesUrlField referencesUrlField) {
    return ReferencesEntryImpl.of(referencesTypeField, referencesUrlField);
  }

  /**
   * Get reference entry type.
   *
   * @return type of this reference entry
   */
  ReferencesTypeField getType();

  /**
   * Get reference entry URL.
   *
   * @return URL of this reference entry
   */
  ReferencesUrlField getUrl();
}
