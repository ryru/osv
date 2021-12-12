package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.pckg.Purl;
import org.junit.jupiter.api.Test;

class PurlImplTest {

  private static final String PURL = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  private static final String OTHER_PURL = "pkg:docker/cassandra@sha256:244fd47e07d1004f0aed9c";

  @Test
  void testJsonKey() {
    assertThat(PURL_KEY).isEqualTo("purl");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> PurlImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument purl must not be null");
  }

  @Test
  void testInvalidPurl() {
    assertThatThrownBy(() -> PurlImpl.of("not-valid-purl"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid purl: 'not-valid-purl'");
  }

  @Test
  void testSetNewPurl() {
    Purl purl = PurlImpl.of(PURL);
    assertThat(purl.value()).isEqualTo(PURL);
  }

  @Test
  void testSameness() {
    Purl purl = PurlImpl.of(PURL);
    assertThat(purl).isEqualTo(purl);
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
    assertThat(purl).hasSameHashCodeAs(otherPurl);
  }

  @Test
  void testToString() {
    Purl purl = PurlImpl.of(PURL);
    assertThat(purl.toString()).isEqualTo(PURL_KEY + ": " + purl.value());
  }
}
