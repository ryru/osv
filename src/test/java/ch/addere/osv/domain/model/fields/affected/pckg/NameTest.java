package ch.addere.osv.domain.model.fields.affected.pckg;

import static ch.addere.osv.domain.model.fields.affected.pckg.Name.NAME_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class NameTest {

  private static final String NAME = "aName";
  private static final String OTHER_NAME = "otherName";

  @Test
  void testJsonKey() {
    assertThat(NAME_KEY).isEqualTo("name");
  }

  @Test
  void testSetNewName() {
    Name name = Name.of("aName");
    assertThat(name.value()).isEqualTo("aName");
  }

  @Test
  void testSameness() {
    Name name = Name.of(NAME);
    Name otherName = name;
    assertThat(name).isEqualTo(otherName);
  }

  @Test
  void testEquality() {
    Name name = Name.of(NAME);
    Name otherName = Name.of(NAME);
    assertThat(name).isEqualTo(otherName);
  }

  @Test
  void testNonEquality() {
    Name name = Name.of(NAME);
    Name otherName = Name.of(OTHER_NAME);
    assertThat(name).isNotEqualTo(otherName);
  }

  @Test
  void testHashCode() {
    Name name = Name.of(NAME);
    Name otherName = Name.of(NAME);
    assertThat(name.hashCode()).isEqualTo(otherName.hashCode());
  }

  @Test
  void testToString() {
    Name name = Name.of(NAME);
    assertThat(name.toString()).isEqualTo(NAME_KEY + ": " + name.value());
  }
}
