package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesTypeField;
import org.junit.jupiter.api.Test;

import static ch.addere.osvs.lib.field.types.Reference.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReferencesTypeFieldImplTest {

  @Test
  void testOfTypeNull() {
    assertThatThrownBy(() -> ReferencesTypeField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument referenceType must not be null");
  }

  @Test
  void testKeyValue() {
    ReferencesTypeField type = ReferencesTypeField.of(REPORT);
    assertThat(type)
        .satisfies(
            t -> {
              assertThat(t.getKey()).isEqualTo("type");
              assertThat(t.getValue()).isEqualTo(REPORT);
            });
  }

  @Test
  void testEquality() {
    ReferencesTypeField type1 = ReferencesTypeField.of(ADVISORY);
    ReferencesTypeField type2 = ReferencesTypeField.of(ADVISORY);
    assertThat(type1)
        .satisfies(
            t -> {
              assertThat(t).isEqualTo(type1);
              assertThat(t).isEqualTo(type2);
            });
  }

  @Test
  void testNonEquality() {
    ReferencesTypeField type1 = ReferencesTypeField.of(WEB);
    ReferencesTypeField type2 = ReferencesTypeField.of(ADVISORY);
    assertThat(type1)
        .satisfies(
            t -> {
              assertThat(t).isNotEqualTo(null);
              assertThat(t).isNotEqualTo(type2);
            });
  }

  @Test
  void testHashCode() {
    ReferencesTypeField type1 = ReferencesTypeField.of(REPORT);
    ReferencesTypeField type2 = ReferencesTypeField.of(REPORT);
    assertThat(type1).hasSameHashCodeAs(type2);
  }

  @Test
  void testToString() {
    ReferencesTypeField type = ReferencesTypeField.of(WEB);
    assertThat(type).hasToString("type: WEB");
  }
}
