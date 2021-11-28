package ch.addere.osv.domain.model.fields;

import static java.lang.String.join;

import java.util.Objects;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
public final class Id {

  public static final String ID_KEY = "id";

  private final Database database;
  private final String entryId;

  private Id(Database database, String entryId) {
    this.database = database;
    this.entryId = entryId;
  }

  public static Id of(Database database, String entryId) {
    return new Id(database, entryId);
  }

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
    Id id = (Id) o;
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
