package ch.addere.osv.property.references;

import static ch.addere.osv.property.references.ReferenceTypeValue.REFERENCE_TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ReferenceTypeValueTest {

  private static final String ADVISORY = "ADVISORY";

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testValidReferenceType() {
    ReferenceTypeValue referenceType = ReferenceTypeValue.ADVISORY;
    assertThat(referenceType.value()).isEqualTo(ADVISORY);
  }

  @Test
  void testValidOfAdvisory() {
    ReferenceTypeValue referenceType = ReferenceTypeValue.of(ADVISORY);
    assertThat(referenceType.value()).isEqualTo(ADVISORY);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> ReferenceTypeValue.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid reference type");
  }

  @Test
  void testOfValueObjectCreation() {
    ReferenceTypeValue referenceType1 = ReferenceTypeValue.ADVISORY;
    ReferenceTypeValue referenceType2 = ReferenceTypeValue.of(referenceType1.value());
    assertThat(referenceType1).isEqualTo(referenceType2);
  }

  @Test
  void testAdvisoryToString() {
    ReferenceTypeValue type = ReferenceTypeValue.ADVISORY;
    String advisory = type.toString();
    assertThat(advisory).isEqualTo(REFERENCE_TYPE_KEY + ": " + ADVISORY);
  }

  @Test
  void testArticleToString() {
    ReferenceTypeValue type = ReferenceTypeValue.ARTICLE;
    String article = type.toString();
    assertThat(article).isEqualTo(REFERENCE_TYPE_KEY + ": " + "ARTICLE");
  }

  @Test
  void testReportToString() {
    ReferenceTypeValue type = ReferenceTypeValue.REPORT;
    String report = type.toString();
    assertThat(report).isEqualTo(REFERENCE_TYPE_KEY + ": " + "REPORT");
  }

  @Test
  void testFixToString() {
    ReferenceTypeValue type = ReferenceTypeValue.FIX;
    String fix = type.toString();
    assertThat(fix).isEqualTo(REFERENCE_TYPE_KEY + ": " + "FIX");
  }

  @Test
  void testPackageToString() {
    ReferenceTypeValue type = ReferenceTypeValue.PACKAGE;
    String pckg = type.toString();
    assertThat(pckg).isEqualTo(REFERENCE_TYPE_KEY + ": " + "PACKAGE");
  }

  @Test
  void testWebToString() {
    ReferenceTypeValue type = ReferenceTypeValue.WEB;
    String web = type.toString();
    assertThat(web).isEqualTo(REFERENCE_TYPE_KEY + ": " + "WEB");
  }
}
