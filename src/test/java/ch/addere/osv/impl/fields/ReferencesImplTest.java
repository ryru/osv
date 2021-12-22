package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.ReferencesImpl.REFERENCES_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.References;
import ch.addere.osv.impl.fields.references.ReferenceTypeValue;
import ch.addere.osv.impl.fields.references.ReferenceUrlValue;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;

class ReferencesImplTest {

  private static final String VALID_URL = "https://osv.dev/";

  @Test
  void testJsonKey() {
    assertThat(REFERENCES_KEY).isEqualTo("references");
  }

  @Test
  void testOfReferenceTypeNull() {
    assertThatThrownBy(() -> ReferencesImpl.of(null, url()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument referenceType must not be null");
  }

  @Test
  void testOfReferenceUrlNull() {
    assertThatThrownBy(() -> ReferencesImpl.of(type(), null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument referenceUrl must not be null");
  }

  private static ReferenceTypeValue type() {
    return ReferenceTypeValue.ARTICLE;
  }

  private static ReferenceUrlValue url() throws MalformedURLException {
    return ReferenceUrlValue.of(new URL(VALID_URL));
  }

  @Test
  void testOfValueObjectCreation() throws MalformedURLException {
    References ref1 = ReferencesImpl.of(type(), url());
    References ref2 = ReferencesImpl.of(ref1.referenceType(), ref1.referenceUrl());
    assertThat(ref1).isEqualTo(ref2);
  }

  @Test
  void testEquality() throws MalformedURLException {
    References references = ReferencesImpl.of(type(), url());
    References otherReferences = ReferencesImpl.of(type(), url());
    assertThat(references).satisfies(r -> {
      assertThat(r).isEqualTo(references);
      assertThat(r).isEqualTo(otherReferences);
    });
  }

  @Test
  void testNonEquality() throws MalformedURLException {
    References references = ReferencesImpl.of(type(), url());
    References otherReferences = ReferencesImpl.of(ReferenceTypeValue.FIX, url());
    assertThat(references).satisfies(r -> {
      assertThat(references).isNotEqualTo(null);
      assertThat(references).isNotEqualTo(otherReferences);
    });
  }

  @Test
  void testHashCode() throws MalformedURLException {
    References references = ReferencesImpl.of(type(), url());
    References otherReferences = ReferencesImpl.of(type(), url());
    assertThat(references).hasSameHashCodeAs(otherReferences);
  }

  @Test
  void testToString() throws MalformedURLException {
    References references = ReferencesImpl.of(type(), url());
    assertThat(references).hasToString(REFERENCES_KEY + ": " + join(", ",
        typeToString(),
        urlToString()));
  }

  @Test
  void testValidReference() throws MalformedURLException {
    References references = ReferencesImpl.of(type(), url());
    assertThat(references).satisfies(ref -> {
      assertThat(ref.referenceType()).isEqualTo(ReferenceTypeValue.ARTICLE);
      assertThat(ref.referenceUrl().value()).hasToString(VALID_URL);
    });
  }

  private static String typeToString() {
    return "type: ARTICLE";
  }

  private static String urlToString() {
    return "url: " + VALID_URL;
  }
}
