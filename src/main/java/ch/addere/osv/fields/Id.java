package ch.addere.osv.fields;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
public interface Id {

  String value();

  IdDatabase getDatabase();

  String getEntryId();

}
