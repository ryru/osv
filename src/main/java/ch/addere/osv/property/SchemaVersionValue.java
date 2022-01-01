package ch.addere.osv.property;

import static java.lang.String.format;

import ch.addere.osv.EntrySchemaVersion;
import ch.addere.osv.Value;
import de.skuzzle.semantic.Version;
import de.skuzzle.semantic.Version.VersionFormatException;
import java.util.Objects;

/**
 * Schema version of this vulnerability entry.
 */
public final class SchemaVersionValue implements Value<String> {

  public static final String SCHEMA_VERSION_KEY = "schema_version";

  private final Version schemaVersion;
  private final EntrySchemaVersion schemaVersion2;

  private SchemaVersionValue(String schemaVersion) {
    try {
      this.schemaVersion = Version.parseVersion(schemaVersion);
    } catch (VersionFormatException e) {
      throw new IllegalArgumentException(
          format("invalid semantic version: '%s'", schemaVersion), e);
    }
    schemaVersion2 = EntrySchemaVersion.fromVersionString(schemaVersion);
  }

  /**
   * Create SchemaVersionValue from semantic version string.
   *
   * @param schemaVersion semantic version string
   * @return valid SchemaVersionValue
   */
  public static SchemaVersionValue fromString(String schemaVersion) {
    Objects.requireNonNull(schemaVersion, "argument schema version must not be null");
    if (schemaVersion.startsWith("v")) {
      throw new IllegalArgumentException("invalid schema version, must not start with 'v'");
    }
    return new SchemaVersionValue(schemaVersion);
  }

  @Override
  public String value() {
    return schemaVersion.toString();
  }

  public EntrySchemaVersion value2() {
    return schemaVersion2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaVersionValue that = (SchemaVersionValue) o;
    return Objects.equals(schemaVersion, that.schemaVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schemaVersion);
  }

  @Override
  public String toString() {
    return SCHEMA_VERSION_KEY + ": " + schemaVersion;
  }
}
