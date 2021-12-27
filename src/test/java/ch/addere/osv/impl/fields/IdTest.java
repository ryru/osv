package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.Id.ID_KEY;
import static ch.addere.osv.impl.fields.IdDatabaseValue.CVE;
import static ch.addere.osv.impl.fields.IdDatabaseValue.GO;
import static ch.addere.osv.impl.fields.IdDatabaseValue.NPM;
import static ch.addere.osv.impl.fields.IdDatabaseValue.OSV;
import static ch.addere.osv.impl.fields.IdDatabaseValue.PYSEC;
import static ch.addere.osv.impl.fields.IdDatabaseValue.RUSTSEC;
import static ch.addere.osv.impl.fields.IdDatabaseValue.SNYK;
import static ch.addere.osv.impl.fields.IdDatabaseValue.UVI;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IdTest {

  private static final String ENTRY_ID = "2020-111";

  @Test
  void testJsonKey() {
    assertThat(ID_KEY).isEqualTo("id");
  }

  @Test
  void testOfDatabaseNull() {
    assertThatThrownBy(() -> Id.of(null, ENTRY_ID))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument database must not be null");
  }

  @Test
  void testOfEntryIdNull() {
    assertThatThrownBy(() -> Id.of(GO, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument entryId must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Id id1 = Id.of(GO, ENTRY_ID);
    Id id2 = Id.of(id1.getDatabase(), id1.getEntryId());
    assertThat(id1).satisfies(i -> {
      assertThat(i).isEqualTo(id2);
      assertThat(i.value()).isEqualTo("GO-2020-111");
    });
  }

  @Test
  void testGoToJson() {
    Id id1 = Id.of(GO, ENTRY_ID);
    Id id2 = Id.fromString("GO-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testOsvToJson() {
    Id id1 = Id.of(OSV, ENTRY_ID);
    Id id2 = Id.fromString("OSV-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testPysecToJson() {
    Id id1 = Id.of(PYSEC, ENTRY_ID);
    Id id2 = Id.fromString("PYSEC-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testRustsecToJson() {
    Id id1 = Id.of(RUSTSEC, ENTRY_ID);
    Id id2 = Id.fromString("RUSTSEC-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testCveToJson() {
    Id id1 = Id.of(CVE, ENTRY_ID);
    Id id2 = Id.fromString("CVE-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testNpmToJson() {
    Id id1 = Id.of(NPM, ENTRY_ID);
    Id id2 = Id.fromString("NPM-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testSnykToJson() {
    Id id1 = Id.of(SNYK, ENTRY_ID);
    Id id2 = Id.fromString("SNYK-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testUviToJson() {
    Id id1 = Id.of(UVI, ENTRY_ID);
    Id id2 = Id.fromString("UVI-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testEquality() {
    Id id = Id.of(UVI, ENTRY_ID);
    Id otherId = Id.of(UVI, ENTRY_ID);
    assertThat(id).satisfies(i -> {
      assertThat(i).isEqualTo(id);
      assertThat(i).isEqualTo(otherId);
    });
  }

  @Test
  void testNonEquality() {
    Id id = Id.of(UVI, ENTRY_ID);
    Id otherId = Id.of(NPM, ENTRY_ID);
    assertThat(id).satisfies(i -> {
      assertThat(id).isNotEqualTo(null);
      assertThat(id).isNotEqualTo(otherId);
    });
  }

  @Test
  void testHashCode() {
    Id id = Id.of(UVI, ENTRY_ID);
    Id otherId = Id.of(UVI, ENTRY_ID);
    assertThat(id).hasSameHashCodeAs(otherId);
  }

  @Test
  void testToString() {
    Id id = Id.of(UVI, ENTRY_ID);
    assertThat(id).hasToString(ID_KEY + ": " + join(", ", "UVI", "2020-111"));
  }
}
