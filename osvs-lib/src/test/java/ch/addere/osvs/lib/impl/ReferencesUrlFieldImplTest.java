package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesUrlField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReferencesUrlFieldImplTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final String OTHER_VALID_URL_STRING = "https://github.com/";

  private static URL validUrl;
  private static URL otherValidUrl;

  @BeforeAll
  static void init() throws MalformedURLException {
    validUrl = new URL(VALID_URL_STRING);
    otherValidUrl = new URL(OTHER_VALID_URL_STRING);
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> ReferencesUrlField.of(null, validUrl))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testOfUrlNull() {
    assertThatThrownBy(() -> ReferencesUrlField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument url must not be null");
  }

  @Test
  void testKeyValue() {
    ReferencesUrlField reference = ReferencesUrlField.of(validUrl);
    assertThat(reference)
        .satisfies(
            ref -> {
              assertThat(ref.getKey()).isEqualTo("url");
              assertThat(ref.getValue()).isEqualTo(validUrl);
            });
  }

  @Test
  void testOfValueObjectCreation() {
    ReferencesUrlField referenceUrl1 = ReferencesUrlField.of(validUrl);
    ReferencesUrlField referenceUrl2 = ReferencesUrlField.of(referenceUrl1.getValue());
    assertThat(referenceUrl1).isEqualTo(referenceUrl2);
  }

  @Test
  void testValidUrl() {
    ReferencesUrlField referenceUrl = ReferencesUrlField.of(validUrl);
    String url = referenceUrl.getValue().toString();
    assertThat(url).isEqualTo(VALID_URL_STRING);
  }

  @Test
  void testEquality() {
    ReferencesUrlField url = ReferencesUrlField.of(validUrl);
    ReferencesUrlField otherUrl = ReferencesUrlField.of(validUrl);
    assertThat(url)
        .satisfies(
            u -> {
              assertThat(u).isEqualTo(url);
              assertThat(u).isEqualTo(otherUrl);
            });
  }

  @Test
  void testNonEquality() {
    ReferencesUrlField url = ReferencesUrlField.of(validUrl);
    ReferencesUrlField otherUrl = ReferencesUrlField.of(otherValidUrl);
    assertThat(url)
        .satisfies(
            u -> {
              assertThat(u).isNotEqualTo(null);
              assertThat(u).isNotEqualTo(otherUrl);
            });
  }

  @Test
  void testHashCode() {
    ReferencesUrlField url = ReferencesUrlField.of(validUrl);
    ReferencesUrlField otherUrl = ReferencesUrlField.of(validUrl);
    assertThat(url).hasSameHashCodeAs(otherUrl);
  }

  @Test
  void testToString() {
    ReferencesUrlField url = ReferencesUrlField.of(validUrl);
    assertThat(url).hasToString("url: https://osv.dev/");
  }
}
