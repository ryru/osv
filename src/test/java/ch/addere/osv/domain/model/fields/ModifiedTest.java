package ch.addere.osv.domain.model.fields;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class ModifiedTest {

  @Test
  void testModifiedToString() {
    var now = Instant.now();
    var modified = new Modified(now);
    assertThat(modified.date()).isEqualTo(now);
  }
}