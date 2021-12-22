package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.DetailsValue.DETAILS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DetailsValueTest {

  private static final String DETAILS = "details";
  private static final String OTHER_DETAILS = "some other details";

  @Test
  void testJsonKey() {
    assertThat(DETAILS_KEY).isEqualTo("details");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> DetailsValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument details must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    DetailsValue detailsValue1 = DetailsValue.fromString(DETAILS);
    DetailsValue detailsValue2 = DetailsValue.fromString(detailsValue1.value());
    assertThat(detailsValue1).isEqualTo(detailsValue2);
  }

  @Test
  void testValidDetails() {
    DetailsValue detailsValue = DetailsValue.fromString(DETAILS);
    assertThat(detailsValue.value()).isEqualTo(DETAILS);
  }

  @Test
  void testEquality() {
    DetailsValue detailsValue = DetailsValue.fromString(DETAILS);
    DetailsValue otherDetailsValue = DetailsValue.fromString(DETAILS);
    assertThat(detailsValue).satisfies(d -> {
      assertThat(d).isEqualTo(detailsValue);
      assertThat(d).isEqualTo(otherDetailsValue);
    });
  }

  @Test
  void testNonEquality() {
    DetailsValue detailsValue = DetailsValue.fromString(DETAILS);
    DetailsValue otherDetailsValue = DetailsValue.fromString(OTHER_DETAILS);
    assertThat(detailsValue).satisfies(d -> {
      assertThat(d).isNotEqualTo(null);
      assertThat(d).isNotEqualTo(otherDetailsValue);
    });
  }

  @Test
  void testHashCode() {
    DetailsValue detailsValue = DetailsValue.fromString(DETAILS);
    DetailsValue otherDetailsValue = DetailsValue.fromString(DETAILS);
    assertThat(detailsValue).hasSameHashCodeAs(otherDetailsValue);
  }

  @Test
  void testToString() {
    DetailsValue detailsValue = DetailsValue.fromString(DETAILS);
    assertThat(detailsValue).hasToString(DETAILS_KEY + ": " + DETAILS);
  }
}
