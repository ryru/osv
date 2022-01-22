package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.ReferencesEntry;
import ch.addere.osvs.lib.impl.ReferencesFieldImpl;

import java.util.List;

/**
 * The {@code references} field contains a list of {@link
 * ch.addere.osvs.lib.field.types.ReferencesEntry} describing references. A reference links to
 * additional information, advisories, issue tracker entries, and so on about the vulnerability
 * itself.
 */
public interface ReferencesField extends EntryField<List<ReferencesEntry>> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param referencesEntries reference entry
   * @return valid {@code ReferencesField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static ReferencesField of(ReferencesEntry... referencesEntries) {
    return ReferencesFieldImpl.of(referencesEntries);
  }

}
