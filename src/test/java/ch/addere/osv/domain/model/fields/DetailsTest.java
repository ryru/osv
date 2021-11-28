package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Details.DETAILS_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DetailsTest {

  private static final String DETAILS = "details";
  private static final String OTHER_DETAILS = "some other details";

  @Test
  void testJsonKey() {
    assertThat(DETAILS_KEY).isEqualTo("details");
  }

  @Test
  void testValidDetails() {
    Details details = Details.of(DETAILS);
    assertThat(details.value()).isEqualTo(DETAILS);
  }

  @Test
  void testSameness() {
    Details details = Details.of(DETAILS);
    Details otherDetails = details;
    assertThat(details).isEqualTo(otherDetails);
  }

  @Test
  void testEquality() {
    Details details = Details.of(DETAILS);
    Details otherDetails = Details.of(DETAILS);
    assertThat(details).isEqualTo(otherDetails);
  }

  @Test
  void testNonEquality() {
    Details details = Details.of(DETAILS);
    Details otherDetails = Details.of(OTHER_DETAILS);
    assertThat(details).isNotEqualTo(otherDetails);
  }

  @Test
  void testHashCode() {
    Details details = Details.of(DETAILS);
    Details otherDetails = Details.of(DETAILS);
    assertThat(details.hashCode()).isEqualTo(otherDetails.hashCode());
  }

  @Test
  void testToString() {
    Details details = Details.of(DETAILS);
    assertThat(details.toString())
        .isEqualTo(DETAILS_KEY + ": " + DETAILS);
  }
}
