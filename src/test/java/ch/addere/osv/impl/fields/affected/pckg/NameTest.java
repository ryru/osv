package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.pckg.Name;
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
    Name name = NameImpl.of("aName");
    assertThat(name.value()).isEqualTo("aName");
  }

  @Test
  void testSameness() {
    Name name = NameImpl.of(NAME);
    Name otherName = name;
    assertThat(name).isEqualTo(otherName);
  }

  @Test
  void testEquality() {
    Name name = NameImpl.of(NAME);
    Name otherName = NameImpl.of(NAME);
    assertThat(name).isEqualTo(otherName);
  }

  @Test
  void testNonEquality() {
    Name name = NameImpl.of(NAME);
    Name otherName = NameImpl.of(OTHER_NAME);
    assertThat(name).isNotEqualTo(otherName);
  }

  @Test
  void testHashCode() {
    Name name = NameImpl.of(NAME);
    Name otherName = NameImpl.of(NAME);
    assertThat(name.hashCode()).isEqualTo(otherName.hashCode());
  }

  @Test
  void testToString() {
    Name name = NameImpl.of(NAME);
    assertThat(name.toString()).isEqualTo(NAME_KEY + ": " + name.value());
  }
}
