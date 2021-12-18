package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.IdDatabaseImpl.CVE;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.GO;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.NPM;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.OSV;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.PYSEC;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.RUSTSEC;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.SNYK;
import static ch.addere.osv.impl.fields.IdDatabaseImpl.UVI;
import static ch.addere.osv.impl.fields.IdImpl.ID_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.Id;
import org.junit.jupiter.api.Test;

class IdImplTest {

  private static final String ENTRY_ID = "2020-111";

  @Test
  void testJsonKey() {
    assertThat(ID_KEY).isEqualTo("id");
  }

  @Test
  void testOfDatabaseNull() {
    assertThatThrownBy(() -> IdImpl.of(null, ENTRY_ID))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument database must not be null");
  }

  @Test
  void testOfEntryIdNull() {
    assertThatThrownBy(() -> IdImpl.of(GO, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument entryId must not be null");
  }

  @Test
  void testGoToJson() {
    Id goId = IdImpl.of(GO, ENTRY_ID);
    String id = goId.value();
    assertThat(id).isEqualTo("GO-2020-111");
  }

  @Test
  void testOsvToJson() {
    Id osvId = IdImpl.of(OSV, ENTRY_ID);
    String id = osvId.value();
    assertThat(id).isEqualTo("OSV-2020-111");
  }

  @Test
  void testPysecToJson() {
    Id pysecId = IdImpl.of(PYSEC, ENTRY_ID);
    String id = pysecId.value();
    assertThat(id).isEqualTo("PYSEC-2020-111");
  }

  @Test
  void testRustsecToJson() {
    Id rustsecId = IdImpl.of(RUSTSEC, ENTRY_ID);
    String id = rustsecId.value();
    assertThat(id).isEqualTo("RUSTSEC-2020-111");
  }

  @Test
  void testCveToJson() {
    Id cveId = IdImpl.of(CVE, ENTRY_ID);
    String id = cveId.value();
    assertThat(id).isEqualTo("CVE-2020-111");
  }

  @Test
  void testNpmToJson() {
    Id npmId = IdImpl.of(NPM, ENTRY_ID);
    String id = npmId.value();
    assertThat(id).isEqualTo("NPM-2020-111");
  }

  @Test
  void testSnykToJson() {
    Id snykId = IdImpl.of(SNYK, ENTRY_ID);
    String id = snykId.value();
    assertThat(id).isEqualTo("SNYK-2020-111");
  }

  @Test
  void testUviToJson() {
    Id uviId = IdImpl.of(UVI, ENTRY_ID);
    String id = uviId.value();
    assertThat(id).isEqualTo("UVI-2020-111");
  }

  @Test
  void testEquality() {
    Id id = IdImpl.of(UVI, ENTRY_ID);
    Id otherId = IdImpl.of(UVI, ENTRY_ID);
    assertThat(id).isEqualTo(otherId);
  }

  @Test
  void testNonEquality() {
    Id id = IdImpl.of(UVI, ENTRY_ID);
    Id otherId = IdImpl.of(NPM, ENTRY_ID);
    assertThat(id).isNotEqualTo(otherId);
  }

  @Test
  void testHashCode() {
    Id id = IdImpl.of(UVI, ENTRY_ID);
    Id otherId = IdImpl.of(UVI, ENTRY_ID);
    assertThat(id).hasSameHashCodeAs(otherId);
  }

  @Test
  void testToString() {
    Id id = IdImpl.of(UVI, ENTRY_ID);
    assertThat(id).hasToString(ID_KEY + ": " + join(", ", "UVI", "2020-111"));
  }
}
