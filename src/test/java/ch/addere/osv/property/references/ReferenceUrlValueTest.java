package ch.addere.osv.property.references;

import static ch.addere.osv.property.references.ReferenceUrlValue.REFERENCE_URL_KEY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReferenceUrlValueTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final String INVALID_URL_STRING = "not-a-valid-url";
  private static final String OTHER_VALID_URL_STRING = "https://github.com/";

  private static URL validUrl;
  private static URL otherValidUrl;

  @BeforeAll
  static void init() throws MalformedURLException {
    validUrl = new URL(VALID_URL_STRING);
    otherValidUrl = new URL(OTHER_VALID_URL_STRING);
  }

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_URL_KEY).isEqualTo("url");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> ReferenceUrlValue.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument value must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    ReferenceUrlValue referenceUrl1 = ReferenceUrlValue.of(validUrl);
    ReferenceUrlValue referenceUrl2 = ReferenceUrlValue.of(referenceUrl1.value());
    assertThat(referenceUrl1).isEqualTo(referenceUrl2);
  }

  @Test
  void testInvalidFromStringValueObjectCreation() {
    assertThatThrownBy(() -> ReferenceUrlValue.fromString(INVALID_URL_STRING))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("argument reference url is not a valid URL");
  }

  @Test
  void testFromStringValueObjectCreation() {
    ReferenceUrlValue referenceUrl1 = ReferenceUrlValue.fromString(VALID_URL_STRING);
    ReferenceUrlValue referenceUrl2 = ReferenceUrlValue.of(validUrl);
    assertThat(referenceUrl1).isEqualTo(referenceUrl2);
  }


  @Test
  void testValidUrl() {
    ReferenceUrlValue referenceUrl = ReferenceUrlValue.of(validUrl);
    String url = referenceUrl.value().toString();
    assertThat(url).isEqualTo(VALID_URL_STRING);
  }

  @Test
  void testEquality() {
    ReferenceUrlValue url = ReferenceUrlValue.of(validUrl);
    ReferenceUrlValue otherUrl = ReferenceUrlValue.of(validUrl);
    assertThat(url).satisfies(u -> {
      assertThat(u).isEqualTo(url);
      assertThat(u).isEqualTo(otherUrl);
    });
  }

  @Test
  void testNonEquality() {
    ReferenceUrlValue url = ReferenceUrlValue.of(validUrl);
    ReferenceUrlValue otherUrl = ReferenceUrlValue.of(otherValidUrl);
    assertThat(url).satisfies(u -> {
      assertThat(u).isNotEqualTo(null);
      assertThat(u).isNotEqualTo(otherUrl);
    });
  }

  @Test
  void testHashCode() {
    ReferenceUrlValue url = ReferenceUrlValue.of(validUrl);
    ReferenceUrlValue otherUrl = ReferenceUrlValue.of(validUrl);
    assertThat(url).hasSameHashCodeAs(otherUrl);
  }

  @Test
  void testToString() {
    ReferenceUrlValue url = ReferenceUrlValue.of(validUrl);
    assertThat(url).hasToString(REFERENCE_URL_KEY + ": " + url.value());
  }
}
