package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.IdDb;
import ch.addere.osvs.lib.impl.IdFieldImpl;

/**
 * The {@code id} field is a unique identifier for the vulnerability entry. It is a string of the
 * format {@code <DB>-<ENTRYID>}, where {@code DB} names the database and {@code ENTRYID} is in the
 * format used by the database.
 */
public interface IdField extends EntryField<String> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param db database
   * @param entryId entry ID
   * @return valid {@code IdField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static IdField of(IdDb db, String entryId) {
    return IdFieldImpl.of(db, entryId);
  }

  /**
   * Database this vulnerability entry belongs.
   *
   * @return database typ
   */
  IdDb getDb();

  /**
   * Unique entry ID this vulnerability entry describes.
   *
   * @return entry ID
   */
  String getEntryId();
}
