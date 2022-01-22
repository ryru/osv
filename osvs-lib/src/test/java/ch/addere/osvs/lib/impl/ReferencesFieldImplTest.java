package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesField;
import ch.addere.osvs.lib.field.ReferencesTypeField;
import ch.addere.osvs.lib.field.ReferencesUrlField;
import ch.addere.osvs.lib.field.types.Reference;
import ch.addere.osvs.lib.field.types.ReferencesEntry;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static ch.addere.osvs.lib.field.types.Reference.FIX;
import static ch.addere.osvs.lib.field.types.Reference.REPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReferencesFieldImplTest {

  @Test
  void testOfReferencesEntryNull() {
    assertThatThrownBy(() -> ReferencesField.of((ReferencesEntry[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument referencesEntries must not be null");
  }

  @Test
  void testKeyValue() throws MalformedURLException {
    ReferencesEntry ref = referencesEntry();
    ReferencesField reference = ReferencesField.of(referencesEntry());
    assertThat(reference)
        .satisfies(
            r -> {
              assertThat(r.getKey()).isEqualTo("references");
              assertThat(r.getValue()).containsExactly(ref);
            });
  }

  @Test
  void testEquality() throws MalformedURLException {
    ReferencesField reference1 = ReferencesField.of(referencesEntry());
    ReferencesField reference2 = ReferencesField.of(referencesEntry());
    assertThat(reference1)
        .satisfies(
            r -> {
              assertThat(r).isEqualTo(reference1);
              assertThat(r).isEqualTo(reference2);
            });
  }

  @Test
  void testNonEquality() throws MalformedURLException {
    ReferencesField reference1 = ReferencesField.of(referencesEntry());
    ReferencesField reference2 =
        ReferencesField.of(
            referencesEntry(typeReference(REPORT), urlReference("https://example.org")));
    assertThat(reference1)
        .satisfies(
            r -> {
              assertThat(r).isNotEqualTo(null);
              assertThat(r).isNotEqualTo(reference2);
            });
  }

  @Test
  void testHashCode() throws MalformedURLException {
    ReferencesField reference1 = ReferencesField.of(referencesEntry());
    ReferencesField reference2 = ReferencesField.of(referencesEntry());
    assertThat(reference1).hasSameHashCodeAs(reference2);
  }

  @Test
  void testToString() throws MalformedURLException {
    ReferencesField reference = ReferencesField.of(referencesEntry());
    assertThat(reference).hasToString("references: [ (type: FIX, url: https://osv.dev) ]");
  }

  private static ReferencesEntry referencesEntry() throws MalformedURLException {
    return ReferencesEntry.of(typeReference(), urlReference());
  }

  private static ReferencesEntry referencesEntry(
      ReferencesTypeField typeReference, ReferencesUrlField urlReference) {
    return ReferencesEntry.of(typeReference, urlReference);
  }

  private static ReferencesTypeField typeReference() {
    return ReferencesTypeField.of(FIX);
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
