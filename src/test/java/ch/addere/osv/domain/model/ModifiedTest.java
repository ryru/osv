package ch.addere.osv.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.domain.model.fields.Modified;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ModifiedTest {

  @Test
  void testModifiedToString() {
    var now = Instant.now();
    var modified = new Modified(now);
    assertThat(modified).satisfies(m -> {
      assertThat(m.getDate()).isEqualTo(now);
      assertThat(m.toString()).isEqualTo(now.toString());
    });
  }

  @Test
  void testDateAndTimeParsing() {
    var stringDate = "2021-03-10T23:20:53Z";
    var sut = Modified.create(stringDate);
    assertThat(sut.toString()).isEqualTo("2021-03-10T23:20:53Z");
  }
}