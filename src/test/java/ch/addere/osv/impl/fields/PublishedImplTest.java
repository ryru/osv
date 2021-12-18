package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.PublishedImpl.PUBLISHED_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.Published;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class PublishedImplTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(PUBLISHED_KEY).isEqualTo("published");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> PublishedImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument date must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Published modified1 = PublishedImpl.of(NOW);
    Published modified2 = PublishedImpl.of(modified1.value());
    assertThat(modified1).isEqualTo(modified2);
  }

  @Test
  void testValidPublished() {
    Published published = PublishedImpl.of(NOW);
    assertThat(published.value()).isEqualTo(NOW);
  }

  @Test
  void testEquality() {
    Published published = PublishedImpl.of(NOW);
    Published otherPublished = PublishedImpl.of(NOW);
    assertThat(published).isEqualTo(otherPublished);
  }

  @Test
  void testNonEquality() {
    Published published = PublishedImpl.of(NOW);
    Published otherPublished = PublishedImpl.of(NOT_NOW);
    assertThat(published).isNotEqualTo(otherPublished);
  }

  @Test
  void testHashCode() {
    Published published = PublishedImpl.of(NOW);
    Published otherPublished = PublishedImpl.of(NOW);
    assertThat(published).hasSameHashCodeAs(otherPublished);
  }

  @Test
  void testToString() {
    Published published = PublishedImpl.of(NOW);
    assertThat(published).hasToString(PUBLISHED_KEY + ": " + NOW.toString());
  }
}
