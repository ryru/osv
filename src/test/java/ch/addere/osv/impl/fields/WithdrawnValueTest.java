package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.WithdrawnValue.WITHDRAWN_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class WithdrawnValueTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(WITHDRAWN_KEY).isEqualTo("withdrawn");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> WithdrawnValue.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument value must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    WithdrawnValue ref1 = WithdrawnValue.of(NOW);
    WithdrawnValue ref2 = WithdrawnValue.of(ref1.value());
    assertThat(ref1).isEqualTo(ref2);
  }

  @Test
  void testValidWithdrawn() {
    WithdrawnValue withdrawn = WithdrawnValue.of(NOW);
    assertThat(withdrawn.value()).isEqualTo(NOW);
  }

  @Test
  void testEquality() {
    WithdrawnValue withdrawn = WithdrawnValue.of(NOW);
    WithdrawnValue otherWithdrawn = WithdrawnValue.of(NOW);
    assertThat(withdrawn).satisfies(w -> {
      assertThat(w).isEqualTo(withdrawn);
      assertThat(w).isEqualTo(otherWithdrawn);
    });
  }

  @Test
  void testNonEquality() {
    WithdrawnValue withdrawn = WithdrawnValue.of(NOW);
    WithdrawnValue otherWithdrawn = WithdrawnValue.of(NOT_NOW);
    assertThat(withdrawn).satisfies(w -> {
      assertThat(w).isNotEqualTo(null);
      assertThat(w).isNotEqualTo(otherWithdrawn);
    });
  }

  @Test
  void testHashCode() {
    WithdrawnValue withdrawn = WithdrawnValue.of(NOW);
    WithdrawnValue otherWithdrawn = WithdrawnValue.of(NOW);
    assertThat(withdrawn).hasSameHashCodeAs(otherWithdrawn);
  }

  @Test
  void testToString() {
    WithdrawnValue withdrawn = WithdrawnValue.of(NOW);
    assertThat(withdrawn).hasToString(WITHDRAWN_KEY + ": " + NOW.toString());
  }
}
