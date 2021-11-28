package ch.addere.osv.impl.fields;

import static java.lang.String.join;

import ch.addere.osv.fields.Id;
import java.util.Objects;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
public final class IdImpl implements Id {

  public static final String ID_KEY = "id";

  private final Database database;
  private final String entryId;

  private IdImpl(Database database, String entryId) {
    this.database = database;
    this.entryId = entryId;
  }

  public static IdImpl of(Database database, String entryId) {
    return new IdImpl(database, entryId);
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
    IdImpl id = (IdImpl) o;
    return database == id.database && Objects.equals(entryId, id.entryId);
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

  /**
   * Vulnerability databases supported by the specification.
   */
  public enum Database {
    GO("GO"),
    OSV("OSV"),
    PYSEC("PYSEC"),
    RUSTSEC("RUSTSEC"),
    UVI("UVI"),
    CVE("CVE"),
    NPM("NPM"),
    SNYK("SNYK"),
    GHSA("GHSA");

    private final String database;

    Database(String databaseName) {
      database = databaseName;
    }

    @Override
    public String toString() {
      return database;
    }
  }
}
