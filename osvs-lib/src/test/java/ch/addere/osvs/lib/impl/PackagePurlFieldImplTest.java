package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackagePurlField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackagePurlFieldImplTest {

  private static final String PURL = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  private static final String OTHER_PURL = "pkg:docker/cassandra@sha256:244fd47e07d1004f0aed9c";

  @Test
  void testOfPurlNull() {
    assertThatThrownBy(() -> PackagePurlField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument purl must not be null");
  }

  @Test
  void testInvalidPurl() {
    assertThatThrownBy(() -> PackagePurlField.of("not-a-purl"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("validation error");
  }

  @Test
  void testKeyValue() {
    PackagePurlField purl = PackagePurlField.of(PURL);
    assertThat(purl)
        .satisfies(
            p -> {
              assertThat(p.getKey()).isEqualTo("purl");
              assertThat(p.getValue()).isEqualTo(PURL);
            });
  }

  @Test
  void testEquality() {
    PackagePurlField purl1 = PackagePurlField.of(PURL);
    PackagePurlField purl2 = PackagePurlField.of(PURL);
    assertThat(purl1)
        .satisfies(
            p -> {
              assertThat(p).isEqualTo(purl1);
              assertThat(p).isEqualTo(purl2);
            });
  }

  @Test
  void testNonEquality() {
    PackagePurlField purl1 = PackagePurlField.of(PURL);
    PackagePurlField purl2 = PackagePurlField.of(OTHER_PURL);
    assertThat(purl1)
        .satisfies(
            p -> {
              assertThat(p).isNotEqualTo(null);
              assertThat(p).isNotEqualTo(purl2);
            });
  }

  @Test
  void testHashCode() {
    PackagePurlField purl1 = PackagePurlField.of(PURL);
    PackagePurlField purl2 = PackagePurlField.of(PURL);
    assertThat(purl1).hasSameHashCodeAs(purl2);
  }

  @Test
  void testToString() {
    PackagePurlField purl = PackagePurlField.of(PURL);
    assertThat(purl).hasToString("purl: pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie");
  }
}
