package ch.addere.osv.impl.fields.references;

import static ch.addere.osv.impl.fields.references.ReferenceTypeImpl.REFERENCE_TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.references.ReferenceType;
import org.junit.jupiter.api.Test;

class ReferenceTypeImplTest {

  private static final String ADVISORY = "ADVISORY";

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testValidReferenceType() {
    ReferenceType referenceType = ReferenceTypeImpl.ADVISORY;
    assertThat(referenceType.value()).isEqualTo(ADVISORY);
  }

  @Test
  void testValidOfAdvisory() {
    ReferenceType referenceType = ReferenceTypeImpl.of(ADVISORY);
    assertThat(referenceType.value()).isEqualTo(ADVISORY);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> ReferenceTypeImpl.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid reference type");
  }

  @Test
  void testOfValueObjectCreation() {
    ReferenceType referenceType1 = ReferenceTypeImpl.ADVISORY;
    ReferenceType referenceType2 = ReferenceTypeImpl.of(referenceType1.value());
    assertThat(referenceType1).isEqualTo(referenceType2);
  }

  @Test
  void testAdvisoryToString() {
    ReferenceType type = ReferenceTypeImpl.ADVISORY;
    String advisory = type.toString();
    assertThat(advisory).isEqualTo(REFERENCE_TYPE_KEY + ": " + ADVISORY);
  }

  @Test
  void testArticleToString() {
    ReferenceType type = ReferenceTypeImpl.ARTICLE;
    String article = type.toString();
    assertThat(article).isEqualTo(REFERENCE_TYPE_KEY + ": " + "ARTICLE");
  }

  @Test
  void testReportToString() {
    ReferenceType type = ReferenceTypeImpl.REPORT;
    String report = type.toString();
    assertThat(report).isEqualTo(REFERENCE_TYPE_KEY + ": " + "REPORT");
  }

  @Test
  void testFixToString() {
    ReferenceType type = ReferenceTypeImpl.FIX;
    String fix = type.toString();
    assertThat(fix).isEqualTo(REFERENCE_TYPE_KEY + ": " + "FIX");
  }

  @Test
  void testPackageToString() {
    ReferenceType type = ReferenceTypeImpl.PACKAGE;
    String pckg = type.toString();
    assertThat(pckg).isEqualTo(REFERENCE_TYPE_KEY + ": " + "PACKAGE");
  }

  @Test
  void testWebToString() {
    ReferenceType type = ReferenceTypeImpl.WEB;
    String web = type.toString();
    assertThat(web).isEqualTo(REFERENCE_TYPE_KEY + ": " + "WEB");
  }
}
