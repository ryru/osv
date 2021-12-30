package ch.addere.osv.property;

import static ch.addere.osv.property.IdDatabaseValue.CVE;
import static ch.addere.osv.property.IdDatabaseValue.GO;
import static ch.addere.osv.property.IdDatabaseValue.NPM;
import static ch.addere.osv.property.IdDatabaseValue.OSV;
import static ch.addere.osv.property.IdDatabaseValue.PYSEC;
import static ch.addere.osv.property.IdDatabaseValue.RUSTSEC;
import static ch.addere.osv.property.IdDatabaseValue.SNYK;
import static ch.addere.osv.property.IdDatabaseValue.UVI;
import static ch.addere.osv.property.IdValue.ID_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IdValueTest {

  private static final String ENTRY_ID = "2020-111";

  @Test
  void testJsonKey() {
    assertThat(ID_KEY).isEqualTo("id");
  }

  @Test
  void testOfDatabaseNull() {
    assertThatThrownBy(() -> IdValue.of(null, ENTRY_ID))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument database must not be null");
  }

  @Test
  void testOfEntryIdNull() {
    assertThatThrownBy(() -> IdValue.of(GO, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument entryId must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    IdValue id1 = IdValue.of(GO, ENTRY_ID);
    IdValue id2 = IdValue.of(id1.getDatabase(), id1.getEntryId());
    assertThat(id1).satisfies(i -> {
      assertThat(i).isEqualTo(id2);
      assertThat(i.value()).isEqualTo("GO-2020-111");
    });
  }

  @Test
  void testGoToJson() {
    IdValue id1 = IdValue.of(GO, ENTRY_ID);
    IdValue id2 = IdValue.fromString("GO-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testOsvToJson() {
    IdValue id1 = IdValue.of(OSV, ENTRY_ID);
    IdValue id2 = IdValue.fromString("OSV-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testPysecToJson() {
    IdValue id1 = IdValue.of(PYSEC, ENTRY_ID);
    IdValue id2 = IdValue.fromString("PYSEC-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testRustsecToJson() {
    IdValue id1 = IdValue.of(RUSTSEC, ENTRY_ID);
    IdValue id2 = IdValue.fromString("RUSTSEC-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testCveToJson() {
    IdValue id1 = IdValue.of(CVE, ENTRY_ID);
    IdValue id2 = IdValue.fromString("CVE-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testNpmToJson() {
    IdValue id1 = IdValue.of(NPM, ENTRY_ID);
    IdValue id2 = IdValue.fromString("NPM-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testSnykToJson() {
    IdValue id1 = IdValue.of(SNYK, ENTRY_ID);
    IdValue id2 = IdValue.fromString("SNYK-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testUviToJson() {
    IdValue id1 = IdValue.of(UVI, ENTRY_ID);
    IdValue id2 = IdValue.fromString("UVI-2020-111");
    assertThat(id1).isEqualTo(id2);
  }

  @Test
  void testEquality() {
    IdValue id = IdValue.of(UVI, ENTRY_ID);
    IdValue otherId = IdValue.of(UVI, ENTRY_ID);
    assertThat(id).satisfies(i -> {
      assertThat(i).isEqualTo(id);
      assertThat(i).isEqualTo(otherId);
    });
  }

  @Test
  void testNonEquality() {
    IdValue id = IdValue.of(UVI, ENTRY_ID);
    IdValue otherId = IdValue.of(NPM, ENTRY_ID);
    assertThat(id).satisfies(i -> {
      assertThat(id).isNotEqualTo(null);
      assertThat(id).isNotEqualTo(otherId);
    });
  }

  @Test
  void testHashCode() {
    IdValue id = IdValue.of(UVI, ENTRY_ID);
    IdValue otherId = IdValue.of(UVI, ENTRY_ID);
    assertThat(id).hasSameHashCodeAs(otherId);
  }

  @Test
  void testToString() {
    IdValue id = IdValue.of(UVI, ENTRY_ID);
    assertThat(id).hasToString(ID_KEY + ": " + join(", ", "UVI", "2020-111"));
  }
}
