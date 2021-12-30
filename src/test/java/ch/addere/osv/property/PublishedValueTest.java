package ch.addere.osv.property;

import static ch.addere.osv.property.PublishedValue.PUBLISHED_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class PublishedValueTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(PUBLISHED_KEY).isEqualTo("published");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> PublishedValue.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument value must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    PublishedValue modified1 = PublishedValue.of(NOW);
    PublishedValue modified2 = PublishedValue.of(modified1.value());
    assertThat(modified1).isEqualTo(modified2);
  }

  @Test
  void testValidPublished() {
    PublishedValue published = PublishedValue.of(NOW);
    assertThat(published.value()).isEqualTo(NOW);
  }

  @Test
  void testEquality() {
    PublishedValue published = PublishedValue.of(NOW);
    PublishedValue otherPublished = PublishedValue.of(NOW);
    assertThat(published).satisfies(p -> {
      assertThat(p).isEqualTo(published);
      assertThat(p).isEqualTo(otherPublished);
    });
  }

  @Test
  void testNonEquality() {
    PublishedValue published = PublishedValue.of(NOW);
    PublishedValue otherPublished = PublishedValue.of(NOT_NOW);
    assertThat(published).satisfies(p -> {
      assertThat(p).isNotEqualTo(null);
      assertThat(p).isNotEqualTo(otherPublished);
    });
  }

  @Test
  void testHashCode() {
    PublishedValue published = PublishedValue.of(NOW);
    PublishedValue otherPublished = PublishedValue.of(NOW);
    assertThat(published).hasSameHashCodeAs(otherPublished);
  }

  @Test
  void testToString() {
    PublishedValue published = PublishedValue.of(NOW);
    assertThat(published).hasToString(PUBLISHED_KEY + ": " + NOW.toString());
  }
}
