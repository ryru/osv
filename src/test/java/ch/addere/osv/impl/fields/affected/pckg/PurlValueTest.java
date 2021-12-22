package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.PurlValue.PURL_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PurlValueTest {

  private static final String PURL = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  private static final String OTHER_PURL = "pkg:docker/cassandra@sha256:244fd47e07d1004f0aed9c";

  @Test
  void testJsonKey() {
    assertThat(PURL_KEY).isEqualTo("purl");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> PurlValue.fromString(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument purl must not be null");
  }

  @Test
  void testInvalidPurl() {
    assertThatThrownBy(() -> PurlValue.fromString("not-valid-purl"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid purl: 'not-valid-purl'");
  }

  @Test
  void testOfValueObjectCreation() {
    PurlValue purl1 = PurlValue.fromString(PURL);
    PurlValue purl2 = PurlValue.fromString(purl1.value());
    assertThat(purl1).isEqualTo(purl2);
  }

  @Test
  void testSetNewPurl() {
    PurlValue purl = PurlValue.fromString(PURL);
    assertThat(purl.value()).isEqualTo(PURL);
  }

  @Test
  void testEquality() {
    PurlValue purl = PurlValue.fromString(PURL);
    PurlValue otherPurl = PurlValue.fromString(PURL);
    assertThat(purl).satisfies(p -> {
      assertThat(p).isEqualTo(purl);
      assertThat(p).isEqualTo(otherPurl);
    });
  }

  @Test
  void testNonEquality() {
    PurlValue purl = PurlValue.fromString(PURL);
    PurlValue otherPurl = PurlValue.fromString(OTHER_PURL);
    assertThat(purl).satisfies(p -> {
      assertThat(p).isNotEqualTo(null);
      assertThat(p).isNotEqualTo(otherPurl);
    });
  }

  @Test
  void testHashCode() {
    PurlValue purl = PurlValue.fromString(PURL);
    PurlValue otherPurl = PurlValue.fromString(PURL);
    assertThat(purl).hasSameHashCodeAs(otherPurl);
  }

  @Test
  void testToString() {
    PurlValue purl = PurlValue.fromString(PURL);
    assertThat(purl).hasToString(PURL_KEY + ": " + purl.value());
  }
}
