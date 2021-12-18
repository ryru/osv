package ch.addere.osv.impl.fields.references;

import static ch.addere.osv.impl.fields.references.ReferenceUrlImpl.REFERENCE_URL_KEY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.references.ReferenceUrl;
import java.net.URI;
import org.junit.jupiter.api.Test;

class ReferenceUrlImplTest {

  private static final String VALID_URL = "https://osv.dev/";
  private static final String OTHER_VALID_URL = "https://github.com/";

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_URL_KEY).isEqualTo("url");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> ReferenceUrlImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument url must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    ReferenceUrl referenceUrl1 = ReferenceUrlImpl.of(URI.create(VALID_URL));
    ReferenceUrl referenceUrl2 = ReferenceUrlImpl.of(URI.create(referenceUrl1.value()));
    assertThat(referenceUrl1).isEqualTo(referenceUrl2);
  }

  @Test
  void testValidUrl() {
    ReferenceUrl referenceUrl = ReferenceUrlImpl.of(URI.create(VALID_URL));
    String url = referenceUrl.value();
    assertThat(url).isEqualTo(VALID_URL);
  }

  @Test
  void testEquality() {
    ReferenceUrl url = ReferenceUrlImpl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrlImpl.of(URI.create(VALID_URL));
    assertThat(url).isEqualTo(otherUrl);
  }

  @Test
  void testNonEquality() {
    ReferenceUrl url = ReferenceUrlImpl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrlImpl.of(URI.create(OTHER_VALID_URL));
    assertThat(url).isNotEqualTo(otherUrl);
  }

  @Test
  void testHashCode() {
    ReferenceUrl url = ReferenceUrlImpl.of(URI.create(VALID_URL));
    ReferenceUrl otherUrl = ReferenceUrlImpl.of(URI.create(VALID_URL));
    assertThat(url).hasSameHashCodeAs(otherUrl);
  }

  @Test
  void testToString() {
    ReferenceUrl url = ReferenceUrlImpl.of(URI.create(VALID_URL));
    assertThat(url).hasToString(REFERENCE_URL_KEY + ": " + url.value());
  }
}
