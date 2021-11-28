package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.WithdrawnImpl.WITHDRAWN_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.Withdrawn;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class WithdrawnImplTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(WITHDRAWN_KEY).isEqualTo("withdrawn");
  }

  @Test
  void testValidWithdrawn() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    assertThat(withdrawn.value()).isEqualTo(NOW);
  }

  @Test
  void testSameness() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    Withdrawn otherWithdrawn = withdrawn;
    assertThat(withdrawn).isEqualTo(otherWithdrawn);
  }

  @Test
  void testEquality() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    Withdrawn otherWithdrawn = WithdrawnImpl.of(NOW);
    assertThat(withdrawn).isEqualTo(otherWithdrawn);
  }

  @Test
  void testNonEquality() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    Withdrawn otherWithdrawn = WithdrawnImpl.of(NOT_NOW);
    assertThat(withdrawn).isNotEqualTo(otherWithdrawn);
  }

  @Test
  void testHashCode() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    Withdrawn otherWithdrawn = WithdrawnImpl.of(NOW);
    assertThat(withdrawn.hashCode()).isEqualTo(otherWithdrawn.hashCode());
  }

  @Test
  void testToString() {
    Withdrawn withdrawn = WithdrawnImpl.of(NOW);
    assertThat(withdrawn.toString()).isEqualTo(WITHDRAWN_KEY + ": " + NOW.toString());
  }
}
