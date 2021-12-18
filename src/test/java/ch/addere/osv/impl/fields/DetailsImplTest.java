package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.DetailsImpl.DETAILS_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.Details;
import org.junit.jupiter.api.Test;

class DetailsImplTest {

  private static final String DETAILS = "details";
  private static final String OTHER_DETAILS = "some other details";

  @Test
  void testJsonKey() {
    assertThat(DETAILS_KEY).isEqualTo("details");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> DetailsImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument details must not be null");
  }

  @Test
  void testValidDetails() {
    Details details = DetailsImpl.of(DETAILS);
    assertThat(details.value()).isEqualTo(DETAILS);
  }

  @Test
  void testEquality() {
    Details details = DetailsImpl.of(DETAILS);
    Details otherDetails = DetailsImpl.of(DETAILS);
    assertThat(details).isEqualTo(otherDetails);
  }

  @Test
  void testNonEquality() {
    Details details = DetailsImpl.of(DETAILS);
    Details otherDetails = DetailsImpl.of(OTHER_DETAILS);
    assertThat(details).isNotEqualTo(otherDetails);
  }

  @Test
  void testHashCode() {
    Details details = DetailsImpl.of(DETAILS);
    Details otherDetails = DetailsImpl.of(DETAILS);
    assertThat(details).hasSameHashCodeAs(otherDetails);
  }

  @Test
  void testToString() {
    Details details = DetailsImpl.of(DETAILS);
    assertThat(details.toString())
        .isEqualTo(DETAILS_KEY + ": " + DETAILS);
  }
}
