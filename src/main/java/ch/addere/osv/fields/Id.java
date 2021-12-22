package ch.addere.osv.fields;

import ch.addere.osv.impl.fields.IdDatabaseValue;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
public interface Id {

  String value();

  IdDatabaseValue getDatabase();

  String getEntryId();

}
