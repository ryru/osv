package ch.addere.osv.domain.model;

import static ch.addere.osv.domain.model.fields.Id.Database.GO;
import static ch.addere.osv.domain.model.fields.Id.Database.OSV;
import static ch.addere.osv.domain.model.fields.Id.Database.PYSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.RUSTSEC;
import static ch.addere.osv.domain.model.fields.Id.Database.UVI;
import static ch.addere.osv.domain.model.fields.Id.create;
import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.domain.model.fields.Id;
import org.junit.jupiter.api.Test;

class IdTest {

  @Test
  void testGoToJson() {
    Id goId = new Id(GO, "2020-111");
    String id = goId.toJson();
    assertThat(id).isEqualTo("GO-2020-111");
  }

  @Test
  void testOsvToJson() {
    Id osvId = new Id(OSV, "2020-111");
    String id = osvId.toJson();
    assertThat(id).isEqualTo("OSV-2020-111");
  }

  @Test
  void testPysecToJson() {
    Id pysecId = new Id(PYSEC, "2020-111");
    String id = pysecId.toJson();
    assertThat(id).isEqualTo("PYSEC-2020-111");
  }

  @Test
  void testRustsecToJson() {
    Id rustsecId = new Id(RUSTSEC, "2020-111");
    String id = rustsecId.toJson();
    assertThat(id).isEqualTo("RUSTSEC-2020-111");
  }

  @Test
  void testUviToJson() {
    Id uviId = new Id(UVI, "2020-111");
    String id = uviId.toJson();
    assertThat(id).isEqualTo("UVI-2020-111");
  }

  @Test
  void testFromString() {
    String stringId = "OSV-2020-111";
    Id sut = create("OSV-2020-111");
    assertThat(sut).satisfies(i -> {
      assertThat(i.database().toString()).isEqualTo("OSV");
      assertThat(i.entryId()).isEqualTo("2020-111");
      assertThat(i.toJson()).isEqualTo(stringId);
    });
  }
/*

  @Test
  void testFromStringWithAdditionalQuotes() {
    String stringId = "OSV-2020-111";
    Id sut = create("\"OSV-2020-111\"");
    assertThat(sut).satisfies(i -> {
      assertThat(i.getDb().name()).isEqualTo("OSV");
      assertThat(i.getEntryId()).isEqualTo("2020-111");
      assertThat(i.toString()).isEqualTo(stringId);
    });
  }
*/

/*
  @Test
  void testCveFromString() {
    String stringId = "CVE-2021-3114";
    Id sut = create(stringId);
    assertThat(sut).satisfies(id -> {
      assertThat(id.getDb().name()).isEqualTo("CVE");
      assertThat(id.getEntryId()).isEqualTo("2021-3114");
      assertThat(id.toString()).isEqualTo(stringId);
    });
  }
*/

/*  @Test
  void testGhsaFromString() {
    String stringId = "GHSA-vp9c-fpxx-744v";
    Id sut = create(stringId);
    assertThat(sut).satisfies(id -> {
      assertThat(id.getDb().name()).isEqualTo("GHSA");
      assertThat(id.getEntryId()).isEqualTo("vp9c-fpxx-744v");
      assertThat(id.toString()).isEqualTo(stringId);
    });
  }*/
}