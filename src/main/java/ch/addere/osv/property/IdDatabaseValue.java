package ch.addere.osv.property;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.Value;

/**
 * IdDatabase describes a concrete ID database this vulnerability applies to.
 */
public enum IdDatabaseValue implements Value<String> {

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

  private final String value;

  IdDatabaseValue(String databaseName) {
    value = databaseName;
  }

  /**
   * Get ID database from id database string.
   *
   * @param idDatabase string value of an ID database
   * @return valid IdDatabase
   */
  public static IdDatabaseValue of(String idDatabase) {
    return stream(IdDatabaseValue.values())
        .filter(values -> values.value().equals(idDatabase))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid id database", idDatabase)));
  }

  @Override
  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
