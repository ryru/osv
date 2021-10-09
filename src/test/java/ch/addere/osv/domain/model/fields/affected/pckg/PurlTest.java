package ch.addere.osv.domain.model.fields.affected.pckg;

import static ch.addere.osv.domain.model.fields.affected.pckg.Purl.PURL_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class PurlTest {

  private static final String PURL = "aPurl";
  private static final String OTHER_PURL = "otherPurl";

  @Test
  void testJsonKey() {
    assertThat(PURL_KEY).isEqualTo("purl");
  }

  @Test
  void testSetNewPurl() {
    Purl purl = Purl.of(PURL);
    assertThat(purl.value()).isEqualTo(PURL);
  }

  @Test
  void testSameness() {
    Purl purl = Purl.of(PURL);
    Purl otherPurl = purl;
    assertThat(purl).isEqualTo(otherPurl);
  }

  @Test
  void testEquality() {
    Purl purl = Purl.of(PURL);
    Purl otherPurl = Purl.of(PURL);
    assertThat(purl).isEqualTo(otherPurl);
  }

  @Test
  void testNonEquality() {
    Purl purl = Purl.of(PURL);
    Purl otherPurl = Purl.of(OTHER_PURL);
    assertThat(purl).isNotEqualTo(otherPurl);
  }

  @Test
  void testHashCode() {
    Purl purl = Purl.of(PURL);
    Purl otherPurl = Purl.of(PURL);
    assertThat(purl.hashCode()).isEqualTo(otherPurl.hashCode());
  }

  @Test
  void testToString() {
    Purl purl = Purl.of(PURL);
    assertThat(purl.toString()).isEqualTo(PURL_KEY + ": " + purl.value());
  }
}
