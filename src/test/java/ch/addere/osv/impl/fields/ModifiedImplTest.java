package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.ModifiedImpl.MODIFIED_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.Modified;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ModifiedImplTest {

  private static final Instant NOW = Instant.now();
  private static final Instant NOT_NOW = Instant.now().minusSeconds(42);

  @Test
  void testJsonKey() {
    assertThat(MODIFIED_KEY).isEqualTo("modified");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> ModifiedImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument date must not be null");
  }

  @Test
  void testValidModified() {
    Modified modified = ModifiedImpl.of(NOW);
    assertThat(modified.value()).isEqualTo(NOW);
  }

  @Test
  void testSameness() {
    Modified modified = ModifiedImpl.of(NOW);
    assertThat(modified).isEqualTo(modified);
  }

  @Test
  void testEquality() {
    Modified modified = ModifiedImpl.of(NOW);
    Modified otherModified = ModifiedImpl.of(NOW);
    assertThat(modified).isEqualTo(otherModified);
  }

  @Test
  void testNonEquality() {
    Modified modified = ModifiedImpl.of(NOW);
    Modified otherModified = ModifiedImpl.of(NOT_NOW);
    assertThat(modified).isNotEqualTo(otherModified);
  }

  @Test
  void testHashCode() {
    Modified modified = ModifiedImpl.of(NOW);
    Modified otherModified = ModifiedImpl.of(NOW);
    assertThat(modified).hasSameHashCodeAs(otherModified);
  }

  @Test
  void testToString() {
    Modified modified = ModifiedImpl.of(NOW);
    assertThat(modified.toString())
        .isEqualTo(MODIFIED_KEY + ": " + NOW.toString());
  }
}
