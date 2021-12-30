package ch.addere.osv.property;

import static ch.addere.osv.property.SchemaVersionValue.SCHEMA_VERSION_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SchemaVersionValueTest {

  public static final String SCHEMA_VERSION = "1.0.0";

  @Test
  void testJsonKey() {
    assertThat(SCHEMA_VERSION_KEY).isEqualTo("schema_version");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> SchemaVersionValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument schema version must not be null");
  }

  @Test
  void testOfInvalidLeadingV() {
    assertThatThrownBy(() -> SchemaVersionValue.fromString("v1.0.0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid schema version, must not start with 'v'");
  }

  @Test
  void testOfInvalidSemanticVersion() {
    assertThatThrownBy(() -> SchemaVersionValue.fromString("not-a-semantic-version"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version: 'not-a-semantic-version'");
  }

  @Test
  void testOfValueObjectCreation() {
    SchemaVersionValue schemaVersion1 = SchemaVersionValue.fromString(SCHEMA_VERSION);
    SchemaVersionValue schemaVersion2 = SchemaVersionValue.fromString(schemaVersion1.value());
    assertThat(schemaVersion1).isEqualTo(schemaVersion2);
  }

  @Test
  void testValidModified() {
    SchemaVersionValue schemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    assertThat(schemaVersion.value()).isEqualTo(SCHEMA_VERSION);
  }

  @Test
  void testEquality() {
    SchemaVersionValue schemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    SchemaVersionValue otherSchemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    assertThat(schemaVersion).satisfies(m -> {
      assertThat(m).isEqualTo(schemaVersion);
      assertThat(m).isEqualTo(otherSchemaVersion);
    });
  }

  @Test
  void testNonEquality() {
    SchemaVersionValue schemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    SchemaVersionValue otherSchemaVersion = SchemaVersionValue.fromString("1.0.1");
    assertThat(schemaVersion).satisfies(m -> {
      assertThat(m).isNotEqualTo(null);
      assertThat(m).isNotEqualTo(otherSchemaVersion);
    });
  }

  @Test
  void testHashCode() {
    SchemaVersionValue schemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    SchemaVersionValue otherSchemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    assertThat(schemaVersion).hasSameHashCodeAs(otherSchemaVersion);
  }

  @Test
  void testToString() {
    SchemaVersionValue schemaVersion = SchemaVersionValue.fromString(SCHEMA_VERSION);
    assertThat(schemaVersion).hasToString(SCHEMA_VERSION_KEY + ": " + SCHEMA_VERSION);
  }
}
