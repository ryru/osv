package ch.addere.osv.impl.fields;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IdDatabaseValueTest {

  private static final String CVE = "CVE";

  @Test
  void testValidIdDatabase() {
    IdDatabaseValue idDatabase = IdDatabaseValue.CVE;
    assertThat(idDatabase.value()).isEqualTo(CVE);
  }

  @Test
  void testValidOfCve() {
    IdDatabaseValue idDatabase = IdDatabaseValue.of(CVE);
    assertThat(idDatabase.value()).isEqualTo(CVE);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> IdDatabaseValue.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid id database");
  }

  @Test
  void testOfValueObjectCreation() {
    IdDatabaseValue idDatabase1 = IdDatabaseValue.CVE;
    IdDatabaseValue idDatabase2 = IdDatabaseValue.of(idDatabase1.value());
    assertThat(idDatabase1).isEqualTo(idDatabase2);
  }

  @Test
  void testToString() {
    IdDatabaseValue idDatabase = IdDatabaseValue.CVE;
    assertThat(idDatabase).hasToString(CVE);
  }
}
