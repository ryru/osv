package ch.addere.osv.impl.fields.references;

import static ch.addere.osv.impl.fields.references.ReferenceTypeImpl.REFERENCE_TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.references.ReferenceType;
import org.junit.jupiter.api.Test;

class ReferenceTypeImplTest {

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testAdvisotyToString() {
    ReferenceType type = ReferenceTypeImpl.ADVISORY;
    String advisory = type.toString();
    assertThat(advisory).isEqualTo(REFERENCE_TYPE_KEY + ": " + "ADVISORY");
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
