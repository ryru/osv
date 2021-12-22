package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.SummaryValue.SUMMARY_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SummaryValueTest {

  private static final String SUMMARY = "a summary";
  private static final String OTHER_SUMMARY = "another summary";

  @Test
  void testJsonKey() {
    assertThat(SUMMARY_KEY).isEqualTo("summary");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> SummaryValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument summary must not be null");
  }

  @Test
  void testInvalidSummaryLength() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a"
        + " interdum lobortis, ante tortor gravid";
    assertThatThrownBy(() -> SummaryValue.fromString(text))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("summary must not exceed 120 characters, was 121");
  }

  @Test
  void testValidSummary() {
    var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque condimentum, est a"
        + " interdum lobortis, ante tortor gravi";
    SummaryValue summary = SummaryValue.fromString(text);
    assertThat(summary).satisfies(s -> {
      assertThat(s).isNotNull();
      assertThat(s.value()).isEqualTo(text);
    });
  }

  @Test
  void testOfValueObjectCreation() {
    SummaryValue ref1 = SummaryValue.fromString(SUMMARY);
    SummaryValue ref2 = SummaryValue.fromString(ref1.value());
    assertThat(ref1).isEqualTo(ref2);
  }

  @Test
  void testEquality() {
    SummaryValue summary = SummaryValue.fromString(SUMMARY);
    SummaryValue otherSummary = SummaryValue.fromString(SUMMARY);
    assertThat(summary).satisfies(s -> {
      assertThat(s).isEqualTo(summary);
      assertThat(s).isEqualTo(otherSummary);
    });
  }

  @Test
  void testNonEquality() {
    SummaryValue summary = SummaryValue.fromString(SUMMARY);
    SummaryValue otherSummary = SummaryValue.fromString(OTHER_SUMMARY);
    assertThat(summary).satisfies(s -> {
      assertThat(s).isNotEqualTo(null);
      assertThat(s).isNotEqualTo(otherSummary);
    });
  }

  @Test
  void testHashCode() {
    SummaryValue summary = SummaryValue.fromString(SUMMARY);
    SummaryValue otherSummary = SummaryValue.fromString(SUMMARY);
    assertThat(summary).hasSameHashCodeAs(otherSummary);
  }

  @Test
  void testToString() {
    SummaryValue summary = SummaryValue.fromString(SUMMARY);
    assertThat(summary).hasToString(SUMMARY_KEY + ": " + SUMMARY);
  }
}
