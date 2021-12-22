package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.NameValue.NAME_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NameValueTest {

  private static final String NAME = "aName";
  private static final String OTHER_NAME = "otherName";

  @Test
  void testJsonKey() {
    assertThat(NAME_KEY).isEqualTo("name");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> NameValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument name must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    NameValue name1 = NameValue.fromString(NAME);
    NameValue name2 = NameValue.fromString(name1.value());
    assertThat(name1).isEqualTo(name2);
  }

  @Test
  void testSetNewName() {
    NameValue name = NameValue.fromString(NAME);
    assertThat(name.value()).isEqualTo(NAME);
  }

  @Test
  void testEquality() {
    NameValue name = NameValue.fromString(NAME);
    NameValue otherName = NameValue.fromString(NAME);
    assertThat(name).satisfies(n -> {
      assertThat(n).isEqualTo(name);
      assertThat(n).isEqualTo(otherName);
    });
  }

  @Test
  void testNonEquality() {
    NameValue name = NameValue.fromString(NAME);
    NameValue otherName = NameValue.fromString(OTHER_NAME);
    assertThat(name).satisfies(n -> {
      assertThat(n).isNotEqualTo(null);
      assertThat(n).isNotEqualTo(otherName);
    });
  }

  @Test
  void testHashCode() {
    NameValue name = NameValue.fromString(NAME);
    NameValue otherName = NameValue.fromString(NAME);
    assertThat(name).hasSameHashCodeAs(otherName);
  }

  @Test
  void testToString() {
    NameValue name = NameValue.fromString(NAME);
    assertThat(name).hasToString(NAME_KEY + ": " + name.value());
  }
}
