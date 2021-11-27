package ch.addere.osv.domain.model.fields;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
@Value
@Accessors(fluent = true)
public class Id {

  public static final String ID_KEY = "id";

  Database database;
  String entryId;

  @Override
  public String toString() {
    return database.name() + "-" + entryId;
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
