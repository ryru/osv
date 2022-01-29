package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackageTypeField;
import org.junit.jupiter.api.Test;

import static ch.addere.osvs.lib.field.types.Ecosystem.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackageTypeFieldImplTest {

  @Test
  void testOfEcosystemNull() {
    assertThatThrownBy(() -> PackageTypeField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystemType must not be null");
  }

  @Test
  void testKeyValue() {
    PackageTypeField ecosystem = PackageTypeField.of(RUBY_GEMS);
    assertThat(ecosystem)
        .satisfies(
            e -> {
              assertThat(e.getKey()).isEqualTo("ecosystem");
              assertThat(e.getValue()).isEqualTo(RUBY_GEMS);
            });
  }

  @Test
  void testEquality() {
    PackageTypeField ecosystem1 = PackageTypeField.of(CRATES_IO);
    PackageTypeField ecosystem2 = PackageTypeField.of(CRATES_IO);
    assertThat(ecosystem1)
        .satisfies(
            u -> {
              assertThat(u).isEqualTo(ecosystem1);
              assertThat(u).isEqualTo(ecosystem2);
            });
  }

  @Test
  void testNonEquality() {
    PackageTypeField ecosystem1 = PackageTypeField.of(NPM);
    PackageTypeField ecosystem2 = PackageTypeField.of(LINUX);
    assertThat(ecosystem1)
        .satisfies(
            u -> {
              assertThat(u).isNotEqualTo(null);
              assertThat(u).isNotEqualTo(ecosystem2);
            });
  }

  @Test
  void testHashCode() {
    PackageTypeField ecosystem1 = PackageTypeField.of(CRATES_IO);
    PackageTypeField ecosystem2 = PackageTypeField.of(CRATES_IO);
    assertThat(ecosystem1).hasSameHashCodeAs(ecosystem2);
  }

  @Test
  void testToString() {
    PackageTypeField reference = PackageTypeField.of(DEBIAN);
    assertThat(reference).hasToString("ecosystem: DEBIAN");
  }
}
