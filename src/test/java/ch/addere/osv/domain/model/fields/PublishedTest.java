package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Published.PUBLISHED_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class PublishedTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(PUBLISHED_KEY).isEqualTo("published");
  }

  @Test
  void testValidPublished() {
    Published published = Published.of(NOW);
    assertThat(published.value()).isEqualTo(NOW);
  }

  @Test
  void testSameness() {
    Published published = Published.of(NOW);
    Published otherPublished = published;
    assertThat(published).isEqualTo(otherPublished);
  }

  @Test
  void testEquality() {
    Published published = Published.of(NOW);
    Published otherPublished = Published.of(NOW);
    assertThat(published).isEqualTo(otherPublished);
  }

  @Test
  void testNonEquality() {
    Published published = Published.of(NOW);
    Published otherPublished = Published.of(NOT_NOW);
    assertThat(published).isNotEqualTo(otherPublished);
  }

  @Test
  void testHashCode() {
    Published published = Published.of(NOW);
    Published otherPublished = Published.of(NOW);
    assertThat(published.hashCode()).isEqualTo(otherPublished.hashCode());
  }

  @Test
  void testToString() {
    Published published = Published.of(NOW);
    assertThat(published.toString()).isEqualTo(PUBLISHED_KEY + ": " + NOW.toString());
  }
}
