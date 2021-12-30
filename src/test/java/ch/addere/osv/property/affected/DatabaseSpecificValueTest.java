package ch.addere.osv.property.affected;

import static ch.addere.osv.property.affected.DatabaseSpecificValue.DATABASE_SPECIFIC_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DatabaseSpecificValueTest {

  private static final String DATABASE_SPECIFIC1 = "someDatabaseSpecificValues";
  private static final String DATABASE_SPECIFIC2 = "{\"CWE\":\"CWE-327\","
      + "\"CVSS\":{\"Score\":\"6.8\",\"Severity\":\"Medium\","
      + "\"Code\":\"CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:N/A:N\"}}";

  @Test
  void testJsonKey() {
    assertThat(DATABASE_SPECIFIC_KEY)
        .isEqualTo("database_specific");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> DatabaseSpecificValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument database specific must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    DatabaseSpecificValue ds1 = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    DatabaseSpecificValue databaseSpecific2 = DatabaseSpecificValue.fromString(ds1.value());
    assertThat(ds1).isEqualTo(databaseSpecific2);
  }

  @Test
  void testValidDatabaseSpecific() {
    DatabaseSpecificValue ds = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    assertThat(ds.value()).isEqualTo(DATABASE_SPECIFIC1);
  }

  @Test
  void testEquality() {
    DatabaseSpecificValue databaseSpecific = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    DatabaseSpecificValue ods = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    assertThat(databaseSpecific).satisfies(d -> {
      assertThat(d).isEqualTo(databaseSpecific);
      assertThat(d).isEqualTo(ods);
    });
  }

  @Test
  void testNonEquality() {
    DatabaseSpecificValue ds = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    DatabaseSpecificValue ods = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC2);
    assertThat(ds).satisfies(d -> {
      assertThat(d).isNotEqualTo(null);
      assertThat(d).isNotEqualTo(ods);
    });
  }

  @Test
  void testHashCode() {
    DatabaseSpecificValue ds = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    DatabaseSpecificValue ods = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC1);
    assertThat(ds).hasSameHashCodeAs(ods);
  }

  @Test
  void testToString() {
    DatabaseSpecificValue databaseSpecific = DatabaseSpecificValue.fromString(DATABASE_SPECIFIC2);
    assertThat(databaseSpecific).hasToString(DATABASE_SPECIFIC_KEY + ": " + DATABASE_SPECIFIC2);
  }
}
