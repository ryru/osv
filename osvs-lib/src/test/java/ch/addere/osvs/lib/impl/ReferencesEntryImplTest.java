package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesTypeField;
import ch.addere.osvs.lib.field.ReferencesUrlField;
import ch.addere.osvs.lib.field.types.Reference;
import ch.addere.osvs.lib.field.types.ReferencesEntry;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static ch.addere.osvs.lib.field.types.Reference.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReferencesEntryImplTest {

  @Test
  void testOfTypeNull() {
    assertThatThrownBy(() -> ReferencesEntry.of(null, urlReference()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument typeField must not be null");
  }

  @Test
  void testOfUrlNull() {
    assertThatThrownBy(() -> ReferencesEntry.of(typeReference(WEB), null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument urlField must not be null");
  }

  @Test
  void testKeyValue() throws MalformedURLException {
    URL url = new URL("https://osv.dev");
    ReferencesEntry reference = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    assertThat(reference)
        .satisfies(
            r -> {
              assertThat(r.getType().getValue()).isEqualTo(ADVISORY);
              assertThat(r.getUrl().getValue()).isEqualTo(url);
            });
  }

  @Test
  void testEquality() throws MalformedURLException {
    ReferencesEntry reference1 = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    ReferencesEntry reference2 = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    assertThat(reference1)
        .satisfies(
            u -> {
              assertThat(u).isEqualTo(reference1);
              assertThat(u).isEqualTo(reference2);
            });
  }

  @Test
  void testNonEquality() throws MalformedURLException {
    ReferencesEntry reference1 = ReferencesEntry.of(typeReference(REPORT), urlReference());
    ReferencesEntry reference2 =
        ReferencesEntry.of(typeReference(WEB), urlReference("https://example.org"));
    assertThat(reference1)
        .satisfies(
            u -> {
              assertThat(u).isNotEqualTo(null);
              assertThat(u).isNotEqualTo(reference2);
            });
  }

  @Test
  void testHashCode() throws MalformedURLException {
    ReferencesEntry reference1 = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    ReferencesEntry reference2 = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    assertThat(reference1).hasSameHashCodeAs(reference2);
  }

  @Test
  void testToString() throws MalformedURLException {
    ReferencesEntry reference = ReferencesEntry.of(typeReference(ADVISORY), urlReference());
    assertThat(reference).hasToString("(type: ADVISORY, url: https://osv.dev)");
  }

  private static ReferencesTypeField typeReference(Reference type) {
    return ReferencesTypeField.of(type);
  }

  private static ReferencesUrlField urlReference() throws MalformedURLException {
    return urlReference("https://osv.dev");
  }

  private static ReferencesUrlField urlReference(String url) throws MalformedURLException {
    return ReferencesUrlField.of(new URL(url));
  }
}
