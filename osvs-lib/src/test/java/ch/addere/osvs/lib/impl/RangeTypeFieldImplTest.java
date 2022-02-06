package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.types.RangeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RangeTypeFieldImplTest {

  private static final RangeType TYPE_1 = RangeType.GIT;
  private static final RangeType TYPE_2 = RangeType.SEMVER;

  @Test
  void testOfRangeTypeFieldNull() {
    assertThatThrownBy(() -> RangeTypeField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument rangeType must not be null");
  }

  @Test
  void testKeyValue() {
    RangeTypeField type = RangeTypeField.of(TYPE_1);
    assertThat(type)
        .satisfies(
            t -> {
              assertThat(t.getKey()).isEqualTo("type");
              assertThat(t.getValue()).isEqualTo(TYPE_1);
            });
  }

  @Test
  void testEquality() {
    RangeTypeField type1 = RangeTypeField.of(TYPE_1);
    RangeTypeField type2 = RangeTypeField.of(TYPE_1);
    assertThat(type1)
        .satisfies(
            t -> {
              assertThat(t).isEqualTo(type1);
              assertThat(t).isEqualTo(type2);
            });
  }

  @Test
  void testNonEquality() {
    RangeTypeField type1 = RangeTypeField.of(TYPE_1);
    RangeTypeField type2 = RangeTypeField.of(TYPE_2);
    assertThat(type1)
        .satisfies(
            t -> {
              assertThat(t).isNotEqualTo(null);
              assertThat(t).isNotEqualTo(type2);
            });
  }

  @Test
  void testHashCode() {
    RangeTypeField type1 = RangeTypeField.of(TYPE_1);
    RangeTypeField type2 = RangeTypeField.of(TYPE_1);
    assertThat(type1).hasSameHashCodeAs(type2);
  }

  @Test
  void testToString() {
    RangeTypeField type = RangeTypeField.of(TYPE_1);
    assertThat(type).hasToString("type: GIT");
  }
}
