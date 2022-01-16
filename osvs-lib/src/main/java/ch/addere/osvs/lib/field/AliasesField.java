package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.AliasesFieldImpl;

import java.util.List;

/**
 * The {@code aliases} field gives a list of IDs of the same vulnerability in other databases, in
 * the form of the {@code id} field. This allows one database to claim that its own entry describes
 * the same vulnerability as one or more entries in other databases. Or if one database entry has
 * been deduplicated into another in the same database, the duplicate entry could be written using
 * only the {@code id}, {@code modified}, and {@code aliases} field, to point to the canonical one.
 */
public interface AliasesField extends EntryField<List<IdField>> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param idFields entry IDs
   * @return valid {@code AliasesField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static AliasesField of(IdField... idFields) {
    return AliasesFieldImpl.of(idFields);
  }
}
