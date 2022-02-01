package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.Version;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VersionImplTest {

  private static final String VERSION_1 = "a version entry";
  private static final String VERSION_2 = "another version entry";

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> Version.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testKeyValue() {
    Version version = Version.of(VERSION_1);
    assertThat(version.getVersion()).isEqualTo(VERSION_1);
  }

  @Test
  void testEquality() {
    Version version1 = Version.of(VERSION_1);
    Version version2 = Version.of(VERSION_1);
    assertThat(version1)
        .satisfies(
            v -> {
              assertThat(v).isEqualTo(version1);
              assertThat(v).isEqualTo(version2);
            });
  }

  @Test
  void testNonEquality() {
    Version version1 = Version.of(VERSION_1);
    Version version2 = Version.of(VERSION_2);
    assertThat(version1)
        .satisfies(
            v -> {
              assertThat(v).isNotEqualTo(null);
              assertThat(v).isNotEqualTo(version2);
            });
  }

  @Test
  void testHashCode() {
    Version version1 = Version.of(VERSION_1);
    Version version2 = Version.of(VERSION_1);
    assertThat(version1).hasSameHashCodeAs(version2);
  }

  @Test
  void testToString() {
    Version version = Version.of(VERSION_1);
    assertThat(version).hasToString(VERSION_1);
  }
}
