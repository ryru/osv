package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Id.Database.CVE;
import static ch.addere.osv.domain.model.fields.Id.Database.GO;
import static ch.addere.osv.domain.model.fields.Id.Database.NPM;
import static ch.addere.osv.domain.model.fields.Id.Database.OSV;
import static ch.addere.osv.domain.model.fields.Id.Database.PYSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.RUSTSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.SNYK;
import static ch.addere.osv.domain.model.fields.Id.Database.UVI;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdTest {

  private static final String ENTRY_ID = "2020-111";

  @Test
  void testGoToJson() {
    Id goId = new Id(GO, ENTRY_ID);
    String id = goId.toString();
    assertThat(id).isEqualTo("GO-2020-111");
  }

  @Test
  void testOsvToJson() {
    Id osvId = new Id(OSV, ENTRY_ID);
    String id = osvId.toString();
    assertThat(id).isEqualTo("OSV-2020-111");
  }

  @Test
  void testPysecToJson() {
    Id pysecId = new Id(PYSEC, ENTRY_ID);
    String id = pysecId.toString();
    assertThat(id).isEqualTo("PYSEC-2020-111");
  }

  @Test
  void testRustsecToJson() {
    Id rustsecId = new Id(RUSTSEC, ENTRY_ID);
    String id = rustsecId.toString();
    assertThat(id).isEqualTo("RUSTSEC-2020-111");
  }

  @Test
  void testCveToJson() {
    Id cveId = new Id(CVE, ENTRY_ID);
    String id = cveId.toString();
    assertThat(id).isEqualTo("CVE-2020-111");
  }

  @Test
  void testNpmToJson() {
    Id npmId = new Id(NPM, ENTRY_ID);
    String id = npmId.toString();
    assertThat(id).isEqualTo("NPM-2020-111");
  }

  @Test
  void testSnykToJson() {
    Id snykId = new Id(SNYK, ENTRY_ID);
    String id = snykId.toString();
    assertThat(id).isEqualTo("SNYK-2020-111");
  }

  @Test
  void testUviToJson() {
    Id uviId = new Id(UVI, ENTRY_ID);
    String id = uviId.toString();
    assertThat(id).isEqualTo("UVI-2020-111");
  }
}
