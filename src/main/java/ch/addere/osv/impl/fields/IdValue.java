package ch.addere.osv.impl.fields;

import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.Value;
import java.util.Objects;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
public final class IdValue implements Value<String> {

  public static final String ID_KEY = "id";

  private final IdDatabaseValue database;
  private final String entryId;

  private IdValue(IdDatabaseValue database, String entryId) {
    Objects.requireNonNull(database, "argument database must not be null");
    Objects.requireNonNull(entryId, "argument entryId must not be null");
    this.database = database;
    this.entryId = entryId;
  }

  /**
   * CreateID from Database and EntryID property.
   *
   * @param database Database property
   * @param entryId  EntryID property
   * @return a valid ID property
   */
  public static IdValue of(IdDatabaseValue database, String entryId) {
    return new IdValue(database, entryId);
  }

  /**
   * Create ID from an ID string.
   *
   * @param id ID string
   * @return valid ID property
   */
  public static IdValue fromString(String id) {
    String separator = "-";
    String[] inputId = id.split(separator);
    IdDatabaseValue database = IdDatabaseValue.of(inputId[0]);
    String entryId = stream(inputId).skip(1).collect(joining(separator));
    return new IdValue(database, entryId);
  }

  public IdDatabaseValue getDatabase() {
    return database;
  }

  public String getEntryId() {
    return entryId;
  }

  @Override
  public String value() {
    return database.toString() + '-' + entryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdValue id = (IdValue) o;
    return database.equals(id.database) && Objects.equals(entryId, id.entryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(database, entryId);
  }

  @Override
  public String toString() {
    return ID_KEY + ": " + join(", ",
        database.toString(),
        entryId);
  }
}
