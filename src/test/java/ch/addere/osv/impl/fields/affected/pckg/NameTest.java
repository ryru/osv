package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
  void testOfNull() {
    assertThatThrownBy(() -> NameImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument name must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Name name1 = NameImpl.of(NAME);
    Name name2 = NameImpl.of(name1.value());
    assertThat(name1).isEqualTo(name2);
  }

  @Test
  void testSetNewName() {
    Name name = NameImpl.of(NAME);
    assertThat(name.value()).isEqualTo(NAME);
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
    assertThat(name).hasSameHashCodeAs(otherName);
  }

  @Test
  void testToString() {
    Name name = NameImpl.of(NAME);
    assertThat(name).hasToString(NAME_KEY + ": " + name.value());
  }
}
