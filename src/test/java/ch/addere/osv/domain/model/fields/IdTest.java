package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Id.Database.GO;
import static ch.addere.osv.domain.model.fields.Id.Database.OSV;
import static ch.addere.osv.domain.model.fields.Id.Database.PYSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.RUSTSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.UVI;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdTest {

  @Test
  void testGoToJson() {
    Id goId = new Id(GO, "2020-111");
    String id = goId.toString();
    assertThat(id).isEqualTo("GO-2020-111");
  }

  @Test
  void testOsvToJson() {
    Id osvId = new Id(OSV, "2020-111");
    String id = osvId.toString();
    assertThat(id).isEqualTo("OSV-2020-111");
  }

  @Test
  void testPysecToJson() {
    Id pysecId = new Id(PYSEC, "2020-111");
    String id = pysecId.toString();
    assertThat(id).isEqualTo("PYSEC-2020-111");
  }

  @Test
  void testRustsecToJson() {
    Id rustsecId = new Id(RUSTSEC, "2020-111");
    String id = rustsecId.toString();
    assertThat(id).isEqualTo("RUSTSEC-2020-111");
  }

  @Test
  void testUviToJson() {
    Id uviId = new Id(UVI, "2020-111");
    String id = uviId.toString();
    assertThat(id).isEqualTo("UVI-2020-111");
  }
}