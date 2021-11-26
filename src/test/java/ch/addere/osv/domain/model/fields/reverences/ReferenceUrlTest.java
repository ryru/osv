package ch.addere.osv.domain.model.fields.reverences;

import static ch.addere.osv.domain.model.fields.reverences.ReferenceUrl.REFERENCE_URL_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.URI;
import org.junit.jupiter.api.Test;

class ReferenceUrlTest {

  private static final String VALID_URL = "https://osv.dev/";
  private static final String OTHER_VALID_URL = "https://github.com/";

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_URL_KEY).isEqualTo("url");
  }

  @Test
  void testValidUrl() {
    ReferenceUrl referenceUrl = ReferenceUrl.of(URI.create(VALID_URL));
    String url = referenceUrl.value();
    assertThat(url).isEqualTo(VALID_URL);
  }

  @Test
  void testSameness() {
    ReferenceUrl url = ReferenceUrl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = url;
    assertThat(url).isEqualTo(otherUrl);
  }

  @Test
  void testEquality() {
    ReferenceUrl url = ReferenceUrl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrl.of(URI.create(VALID_URL));
    assertThat(url).isEqualTo(otherUrl);
  }

  @Test
  void testNonEquality() {
    ReferenceUrl url = ReferenceUrl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrl.of(URI.create(OTHER_VALID_URL));
    assertThat(url).isNotEqualTo(otherUrl);
  }

  @Test
  void testHashCode() {
    ReferenceUrl url = ReferenceUrl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrl.of(URI.create(VALID_URL));
    assertThat(url.hashCode()).isEqualTo(otherUrl.hashCode());
  }

  @Test
  void testToString() {
    ReferenceUrl url = ReferenceUrl.of(URI.create(VALID_URL));
    assertThat(url.toString()).isEqualTo(REFERENCE_URL_KEY + ": " + url.value());
  }
}
