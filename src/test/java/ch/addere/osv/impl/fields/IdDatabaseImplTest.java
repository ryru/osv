package ch.addere.osv.impl.fields;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.IdDatabase;
import org.junit.jupiter.api.Test;

class IdDatabaseImplTest {

  private static final String CVE = "CVE";

  @Test
  void testValidIdDatabase() {
    IdDatabase idDatabase = IdDatabaseImpl.CVE;
    assertThat(idDatabase.value()).isEqualTo(CVE);
  }

  @Test
  void testValidOfCve() {
    IdDatabase idDatabase = IdDatabaseImpl.of(CVE);
    assertThat(idDatabase.value()).isEqualTo(CVE);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> IdDatabaseImpl.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid id database");
  }

  @Test
  void testOfValueObjectCreation() {
    IdDatabase idDatabase1 = IdDatabaseImpl.CVE;
    IdDatabase idDatabase2 = IdDatabaseImpl.of(idDatabase1.value());
    assertThat(idDatabase1).isEqualTo(idDatabase2);
  }

  @Test
  void testToString() {
    IdDatabase idDatabase = IdDatabaseImpl.CVE;
    assertThat(idDatabase.toString()).isEqualTo(CVE);
  }
}
