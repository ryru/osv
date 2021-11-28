package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl.ECOSYSTEM_SPECIFIC_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.EcosystemSpecific;
import org.junit.jupiter.api.Test;

class EcosystemSpecificImplTest {

  private static final String ECOSYSTEM_SPECIFIC1 = "someEcosystemSpecificValues";
  private static final String ECOSYSTEM_SPECIFIC2 =
      "{\"functions\":[\"http::header::HeaderMap::reserve\"],"
          + "\"keywords\":[\"http\",\"integer-overflow\",\"DoS\"],"
          + "\"categories\":[\"denial-of-service\"],\"severity\":\"HIGH\"}";

  @Test
  void testJsonKey() {
    assertThat(ECOSYSTEM_SPECIFIC_KEY)
        .isEqualTo("ecosystem_specific");
  }

  @Test
  void testValidEcosystemSpecific() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific.value()).isEqualTo(ECOSYSTEM_SPECIFIC1);
  }

  @Test
  void testSameness() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = ecosystemSpecific;
    assertThat(ecosystemSpecific).isEqualTo(otherEcosystemSpecific);
  }

  @Test
  void testEquality() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific).isEqualTo(otherEcosystemSpecific);
  }

  @Test
  void testNonEquality() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC2);
    assertThat(ecosystemSpecific).isNotEqualTo(otherEcosystemSpecific);
  }

  @Test
  void testHashCode() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific.hashCode()).isEqualTo(otherEcosystemSpecific.hashCode());
  }

  @Test
  void testToString() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC2);
    assertThat(ecosystemSpecific.toString()).isEqualTo(
        ECOSYSTEM_SPECIFIC_KEY + ": " + ECOSYSTEM_SPECIFIC2);
  }
}
