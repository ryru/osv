package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.References.REFERENCES_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.domain.model.fields.reverences.ReferenceType;
import ch.addere.osv.domain.model.fields.reverences.ReferenceUrl;
import java.net.URI;
import org.junit.jupiter.api.Test;

class ReferencesTest {

  private static final String VALID_URL = "https://osv.dev/";

  @Test
  void testJsonKey() {
    assertThat(REFERENCES_KEY).isEqualTo("references");
  }

  @Test
  void testValidReference() {
    References references = References.of(type(), url());
    assertThat(references).satisfies(ref -> {
      assertThat(ref.referenceType()).isEqualTo(ReferenceType.ARTICLE);
      assertThat(ref.referenceUrl().value()).isEqualTo(VALID_URL);
    });
  }

  @Test
  void testSameness() {
    References references = References.of(type(), url());
    References otherReferences = references;
    assertThat(references).isEqualTo(otherReferences);
  }

  @Test
  void testEquality() {
    References references = References.of(type(), url());
    References otherReferences = References.of(type(), url());
    assertThat(references).isEqualTo(otherReferences);
  }

  @Test
  void testNonEquality() {
    References references = References.of(type(), url());
    References otherReferences = References.of(ReferenceType.FIX, url());
    assertThat(references).isNotEqualTo(otherReferences);
  }

  @Test
  void testHashCode() {
    References references = References.of(type(), url());
    References otherReferences = References.of(type(), url());
    assertThat(references.hashCode()).isEqualTo(otherReferences.hashCode());
  }

  @Test
  void testToString() {
    References references = References.of(type(), url());
    assertThat(references.toString()).isEqualTo(REFERENCES_KEY + ": " + join(", ",
        typeToString(),
        urlToString()));
  }

  private static ReferenceType type() {
    return ReferenceType.ARTICLE;
  }

  private static ReferenceUrl url() {
    return ReferenceUrl.of(URI.create(VALID_URL));
  }

  private static String typeToString() {
    return "type: ARTICLE";
  }

  private static String urlToString() {
    return "url: " + VALID_URL;
  }
}
