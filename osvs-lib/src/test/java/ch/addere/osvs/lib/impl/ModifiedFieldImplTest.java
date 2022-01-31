package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ModifiedField;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ModifiedFieldImplTest {

  private static final Instant NOW = Instant.now();
  private static final Instant PAST = Instant.now().minus(42, SECONDS);

  @Test
  void testOfNameNull() {
    assertThatThrownBy(() -> ModifiedField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument modified must not be null");
  }

  @Test
  void testKeyValue() {
    ModifiedField modified = ModifiedField.of(NOW);
    assertThat(modified)
        .satisfies(
            m -> {
              assertThat(m.getKey()).isEqualTo("modified");
              assertThat(m.getValue()).isEqualTo(NOW);
            });
  }

  @Test
  void testEquality() {
    ModifiedField modified1 = ModifiedField.of(NOW);
    ModifiedField modified2 = ModifiedField.of(NOW);
    assertThat(modified1)
        .satisfies(
            m -> {
              assertThat(m).isEqualTo(modified1);
              assertThat(m).isEqualTo(modified2);
            });
  }

  @Test
  void testNonEquality() {
    ModifiedField modified1 = ModifiedField.of(NOW);
    ModifiedField modified2 = ModifiedField.of(PAST);
    assertThat(modified1)
        .satisfies(
            m -> {
              assertThat(m).isNotEqualTo(null);
              assertThat(m).isNotEqualTo(modified2);
            });
  }

  @Test
  void testHashCode() {
    ModifiedField modified1 = ModifiedField.of(NOW);
    ModifiedField modified2 = ModifiedField.of(NOW);
    assertThat(modified1).hasSameHashCodeAs(modified2);
  }

  @Test
  void testToString() {
    ModifiedField modified = ModifiedField.of(NOW);
    assertThat(modified).hasToString("modified: " + modified.getValue());
  }
}
