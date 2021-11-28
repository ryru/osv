package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Modified.MODIFIED_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class ModifiedTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(MODIFIED_KEY).isEqualTo("modified");
  }

  @Test
  void testValidModified() {
    Modified modified = Modified.of(NOW);
    assertThat(modified.value()).isEqualTo(NOW);
  }

  @Test
  void testSameness() {
    Modified modified = Modified.of(NOW);
    Modified otherModified = modified;
    assertThat(modified).isEqualTo(otherModified);
  }

  @Test
  void testEquality() {
    Modified modified = Modified.of(NOW);
    Modified otherModified = Modified.of(NOW);
    assertThat(modified).isEqualTo(otherModified);
  }

  @Test
  void testNonEquality() {
    Modified modified = Modified.of(NOW);
    Modified otherModified = Modified.of(NOT_NOW);
    assertThat(modified).isNotEqualTo(otherModified);
  }

  @Test
  void testHashCode() {
    Modified modified = Modified.of(NOW);
    Modified otherModified = Modified.of(NOW);
    assertThat(modified.hashCode()).isEqualTo(otherModified.hashCode());
  }

  @Test
  void testToString() {
    Modified modified = Modified.of(NOW);
    assertThat(modified.toString())
        .isEqualTo(MODIFIED_KEY + ": " + NOW.toString());
  }
}
