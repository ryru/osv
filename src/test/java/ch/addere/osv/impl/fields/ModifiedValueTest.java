package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.ModifiedValue.MODIFIED_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class ModifiedValueTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(MODIFIED_KEY).isEqualTo("modified");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> ModifiedValue.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument value must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    ModifiedValue modified1 = ModifiedValue.of(NOW);
    ModifiedValue modified2 = ModifiedValue.of(modified1.value());
    assertThat(modified1).isEqualTo(modified2);
  }

  @Test
  void testValidModified() {
    ModifiedValue modified = ModifiedValue.of(NOW);
    assertThat(modified.value()).isEqualTo(NOW);
  }

  @Test
  void testEquality() {
    ModifiedValue modified = ModifiedValue.of(NOW);
    ModifiedValue otherModified = ModifiedValue.of(NOW);
    assertThat(modified).satisfies(m -> {
      assertThat(m).isEqualTo(modified);
      assertThat(m).isEqualTo(otherModified);
    });
  }

  @Test
  void testNonEquality() {
    ModifiedValue modified = ModifiedValue.of(NOW);
    ModifiedValue otherModified = ModifiedValue.of(NOT_NOW);
    assertThat(modified).satisfies(m -> {
      assertThat(m).isNotEqualTo(null);
      assertThat(m).isNotEqualTo(otherModified);
    });
  }

  @Test
  void testHashCode() {
    ModifiedValue modified = ModifiedValue.of(NOW);
    ModifiedValue otherModified = ModifiedValue.of(NOW);
    assertThat(modified).hasSameHashCodeAs(otherModified);
  }

  @Test
  void testToString() {
    ModifiedValue modified = ModifiedValue.of(NOW);
    assertThat(modified).hasToString(MODIFIED_KEY + ": " + NOW.toString());
  }
}
