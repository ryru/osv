package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.SummaryImpl.SUMMARY_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.Summary;
import org.junit.jupiter.api.Test;

class SummaryImplTest {

  private static final String SUMMARY = "a summary";
  private static final String OTHER_SUMMARY = "another summary";

  @Test
  void testJsonKey() {
    assertThat(SUMMARY_KEY).isEqualTo("summary");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> SummaryImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument summary must not be null");
  }

  @Test
  void testInvalidSummaryLength() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a"
        + " interdum lobortis, ante tortor gravid";
    assertThatThrownBy(() -> SummaryImpl.of(text))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("summary must not exceed 120 characters, was 121");
  }

  @Test
  void testValidSummary() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a"
        + " interdum lobortis, ante tortor gravi";
    Summary summary = SummaryImpl.of(text);
    assertThat(summary).satisfies(s -> {
      assertThat(s).isNotNull();
      assertThat(s.value()).isEqualTo(text);
    });
  }

  @Test
  void testSameness() {
    Summary summary = SummaryImpl.of(SUMMARY);
    assertThat(summary).isEqualTo(summary);
  }

  @Test
  void testEquality() {
    Summary summary = SummaryImpl.of(SUMMARY);
    Summary otherSummary = SummaryImpl.of(SUMMARY);
    assertThat(summary).isEqualTo(otherSummary);
  }

  @Test
  void testNonEquality() {
    Summary summary = SummaryImpl.of(SUMMARY);
    Summary otherSummary = SummaryImpl.of(OTHER_SUMMARY);
    assertThat(summary).isNotEqualTo(otherSummary);
  }

  @Test
  void testHashCode() {
    Summary summary = SummaryImpl.of(SUMMARY);
    Summary otherSummary = SummaryImpl.of(SUMMARY);
    assertThat(summary).hasSameHashCodeAs(otherSummary);
  }

  @Test
  void testToString() {
    Summary summary = SummaryImpl.of(SUMMARY);
    assertThat(summary.toString()).isEqualTo(
        SUMMARY_KEY + ": " + SUMMARY);
  }
}
