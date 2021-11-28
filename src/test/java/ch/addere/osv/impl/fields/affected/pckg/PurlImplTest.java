package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.pckg.Purl;
import org.junit.jupiter.api.Test;

class PurlImplTest {

  private static final String PURL = "aPurl";
  private static final String OTHER_PURL = "otherPurl";

  @Test
  void testJsonKey() {
    assertThat(PURL_KEY).isEqualTo("purl");
  }

  @Test
  void testSetNewPurl() {
    Purl purl = PurlImpl.of(PURL);
    assertThat(purl.value()).isEqualTo(PURL);
  }

  @Test
  void testSameness() {
    Purl purl = PurlImpl.of(PURL);
    Purl otherPurl = purl;
    assertThat(purl).isEqualTo(otherPurl);
  }

  @Test
  void testEquality() {
    Purl purl = PurlImpl.of(PURL);
    Purl otherPurl = PurlImpl.of(PURL);
    assertThat(purl).isEqualTo(otherPurl);
  }

  @Test
  void testNonEquality() {
    Purl purl = PurlImpl.of(PURL);
    Purl otherPurl = PurlImpl.of(OTHER_PURL);
    assertThat(purl).isNotEqualTo(otherPurl);
  }

  @Test
  void testHashCode() {
    Purl purl = PurlImpl.of(PURL);
    Purl otherPurl = PurlImpl.of(PURL);
    assertThat(purl.hashCode()).isEqualTo(otherPurl.hashCode());
  }

  @Test
  void testToString() {
    Purl purl = PurlImpl.of(PURL);
    assertThat(purl.toString()).isEqualTo(PURL_KEY + ": " + purl.value());
  }
}
