package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.VersionsField;
import ch.addere.osvs.lib.field.types.Version;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VersionsFieldImplTest {

  private static final Version VERSION_1 = Version.of("a version entry");
  private static final Version VERSION_2 = Version.of("another version entry");

  @Test
  void testOfVersionsNull() {
    assertThatThrownBy(() -> VersionsField.of((Version[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument versions must not be null");
  }

  @Test
  void testOfVersionsEmpty() {
    assertThatThrownBy(VersionsField::of)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("value must not be empty");
  }

  @Test
  void testKeyValue() {
    VersionsField version = VersionsField.of(VERSION_1);
    assertThat(version)
        .satisfies(
            v -> {
              assertThat(v.getKey()).isEqualTo("versions");
              assertThat(v.getValue()).hasSize(1);
              assertThat(v.getValue().get(0)).isEqualTo(VERSION_1);
            });
  }

  @Test
  void testKeyValues() {
    VersionsField version = VersionsField.of(VERSION_1, VERSION_2);
    assertThat(version)
        .satisfies(
            v -> {
              assertThat(v.getKey()).isEqualTo("versions");
              assertThat(v.getValue()).hasSize(2);
              assertThat(v.getValue().get(0)).isEqualTo(VERSION_1);
              assertThat(v.getValue().get(1)).isEqualTo(VERSION_2);
            });
  }

  @Test
  void testEquality() {
    VersionsField version1 = VersionsField.of(VERSION_1);
    VersionsField version2 = VersionsField.of(VERSION_1);
    assertThat(version1)
        .satisfies(
            v -> {
              assertThat(v).isEqualTo(version1);
              assertThat(v).isEqualTo(version2);
            });
  }

  @Test
  void testNonEquality() {
    VersionsField version1 = VersionsField.of(VERSION_1);
    VersionsField version2 = VersionsField.of(VERSION_2);
    assertThat(version1)
        .satisfies(
            v -> {
              assertThat(v).isNotEqualTo(null);
              assertThat(v).isNotEqualTo(version2);
            });
  }

  @Test
  void testHashCode() {
    VersionsField version1 = VersionsField.of(VERSION_1);
    VersionsField version2 = VersionsField.of(VERSION_1);
    assertThat(version1).hasSameHashCodeAs(version2);
  }

  @Test
  void testToString() {
    VersionsField version = VersionsField.of(VERSION_1, VERSION_2);
    assertThat(version).hasToString("versions: [ a version entry, another version entry ]");
  }
}
