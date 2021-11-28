package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class WithdrawnTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(WITHDRAWN_KEY).isEqualTo("withdrawn");
  }

  @Test
  void testValidWithdrawn() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    assertThat(withdrawn.value()).isEqualTo(NOW);
  }

  @Test
  void testSameness() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    Withdrawn otherWithdrawn = withdrawn;
    assertThat(withdrawn).isEqualTo(otherWithdrawn);
  }

  @Test
  void testEquality() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    Withdrawn otherWithdrawn = Withdrawn.of(NOW);
    assertThat(withdrawn).isEqualTo(otherWithdrawn);
  }

  @Test
  void testNonEquality() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    Withdrawn otherWithdrawn = Withdrawn.of(NOT_NOW);
    assertThat(withdrawn).isNotEqualTo(otherWithdrawn);
  }

  @Test
  void testHashCode() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    Withdrawn otherWithdrawn = Withdrawn.of(NOW);
    assertThat(withdrawn.hashCode()).isEqualTo(otherWithdrawn.hashCode());
  }

  @Test
  void testToString() {
    Withdrawn withdrawn = Withdrawn.of(NOW);
    assertThat(withdrawn.toString()).isEqualTo(WITHDRAWN_KEY + ": " + NOW.toString());
  }
}
