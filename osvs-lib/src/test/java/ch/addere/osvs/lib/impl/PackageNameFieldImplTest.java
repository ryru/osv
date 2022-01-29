package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackageNameField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackageNameFieldImplTest {

  private static final String NAME_1 = "a name";
  private static final String NAME_2 = "another name";

  @Test
  void testOfNameNull() {
    assertThatThrownBy(() -> PackageNameField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystemName must not be null");
  }

  @Test
  void testKeyValue() {
    PackageNameField name = PackageNameField.of(NAME_1);
    assertThat(name)
        .satisfies(
            p -> {
              assertThat(p.getKey()).isEqualTo("name");
              assertThat(p.getValue()).isEqualTo(NAME_1);
            });
  }

  @Test
  void testEquality() {
    PackageNameField name1 = PackageNameField.of(NAME_1);
    PackageNameField name2 = PackageNameField.of(NAME_1);
    assertThat(name1)
        .satisfies(
            p -> {
              assertThat(p).isEqualTo(name1);
              assertThat(p).isEqualTo(name2);
            });
  }

  @Test
  void testNonEquality() {
    PackageNameField name1 = PackageNameField.of(NAME_1);
    PackageNameField name2 = PackageNameField.of(NAME_2);
    assertThat(name1)
        .satisfies(
            p -> {
              assertThat(p).isNotEqualTo(null);
              assertThat(p).isNotEqualTo(name2);
            });
  }

  @Test
  void testHashCode() {
    PackageNameField name1 = PackageNameField.of(NAME_1);
    PackageNameField name2 = PackageNameField.of(NAME_1);
    assertThat(name1).hasSameHashCodeAs(name2);
  }

  @Test
  void testToString() {
    PackageNameField name = PackageNameField.of(NAME_1);
    assertThat(name).hasToString("name: a name");
  }
}
