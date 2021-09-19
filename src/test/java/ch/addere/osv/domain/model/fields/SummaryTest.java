package ch.addere.osv.domain.model.fields;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SummaryTest {

  @Test
  void testInvalidSummaryLength() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a interdum lobortis, ante tortor gravid";
    assertThatThrownBy(() -> new Summary(text))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("summary must not exceed 120 characters, was 121");
  }

  @Test
  void testValidSummary() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a interdum lobortis, ante tortor gravi";
    var summary = new Summary(text);
    assertThat(summary).satisfies(s -> {
      assertThat(s).isNotNull();
      assertThat(s.summary()).isEqualTo(text);
      assertThat(s.toJson()).isEqualTo(text);
    });
  }

}