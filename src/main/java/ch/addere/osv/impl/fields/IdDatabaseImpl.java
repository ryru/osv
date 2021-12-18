package ch.addere.osv.impl.fields;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.IdDatabase;

/**
 * IdDatabase describes a concrete ID database this vulnerability applies to.
 */
public enum IdDatabaseImpl implements IdDatabase {

  CVE("CVE"),
  GDD("GSD"),
  GHSA("GHSA"),
  GO("GO"),
  NPM("NPM"),
  OSV("OSV"),
  PYSEC("PYSEC"),
  RUSTSEC("RUSTSEC"),
  SNYK("SNYK"),
  UVI("UVI");

  private final String database;

  IdDatabaseImpl(String databaseName) {
    database = databaseName;
  }

  @Override
  public String value() {
    return this.database;
  }

  /**
   * Get ID database from id database string.
   *
   * @param idDatabase string value of an ID database
   * @return           valid IdDatabase
   */
  public static IdDatabaseImpl of(String idDatabase) {
    return stream(IdDatabaseImpl.values())
        .filter(values -> values.value().equals(idDatabase))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid id database", idDatabase)));
  }

  @Override
  public String toString() {
    return this.database;
  }
}
