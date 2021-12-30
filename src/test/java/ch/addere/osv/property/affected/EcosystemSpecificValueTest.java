package ch.addere.osv.property.affected;

import static ch.addere.osv.property.affected.EcosystemSpecificValue.ECOSYSTEM_SPECIFIC_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EcosystemSpecificValueTest {

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
  void testOfNull() {
    assertThatThrownBy(() -> EcosystemSpecificValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystem specific must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecificValue oes = EcosystemSpecificValue.fromString(es.value());
    assertThat(es).isEqualTo(oes);
  }

  @Test
  void testValidEcosystemSpecific() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    assertThat(es.value()).isEqualTo(ECOSYSTEM_SPECIFIC1);
  }

  @Test
  void testEquality() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecificValue oes = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    assertThat(es).satisfies(e -> {
      assertThat(e).isEqualTo(es);
      assertThat(e).isEqualTo(oes);
    });
  }

  @Test
  void testNonEquality() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecificValue oes = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC2);
    assertThat(es).satisfies(e -> {
      assertThat(e).isNotEqualTo(null);
      assertThat(e).isNotEqualTo(oes);
    });
  }

  @Test
  void testHashCode() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecificValue oes = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC1);
    assertThat(es).hasSameHashCodeAs(oes);
  }

  @Test
  void testToString() {
    EcosystemSpecificValue es = EcosystemSpecificValue.fromString(ECOSYSTEM_SPECIFIC2);
    assertThat(es).hasToString(ECOSYSTEM_SPECIFIC_KEY + ": " + ECOSYSTEM_SPECIFIC2);
  }
}
