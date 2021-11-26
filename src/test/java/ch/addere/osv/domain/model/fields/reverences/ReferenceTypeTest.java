package ch.addere.osv.domain.model.fields.reverences;

import static ch.addere.osv.domain.model.fields.reverences.ReferenceType.REFERENCE_TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class ReferenceTypeTest {

  @Test
  void testJsonKey() {
    assertThat(REFERENCE_TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testAdvisotyToString() {
    ReferenceType type = ReferenceType.ADVISORY;
    String advisory = type.toString();
    assertThat(advisory).isEqualTo(REFERENCE_TYPE_KEY + ": " + "ADVISORY");
  }

  @Test
  void testArticleToString() {
    ReferenceType type = ReferenceType.ARTICLE;
    String article = type.toString();
    assertThat(article).isEqualTo(REFERENCE_TYPE_KEY + ": " + "ARTICLE");
  }

  @Test
  void testReportToString() {
    ReferenceType type = ReferenceType.REPORT;
    String report = type.toString();
    assertThat(report).isEqualTo(REFERENCE_TYPE_KEY + ": " + "REPORT");
  }

  @Test
  void testFixToString() {
    ReferenceType type = ReferenceType.FIX;
    String fix = type.toString();
    assertThat(fix).isEqualTo(REFERENCE_TYPE_KEY + ": " + "FIX");
  }

  @Test
  void testPackageToString() {
    ReferenceType type = ReferenceType.PACKAGE;
    String pckg = type.toString();
    assertThat(pckg).isEqualTo(REFERENCE_TYPE_KEY + ": " + "PACKAGE");
  }

  @Test
  void testWebToString() {
    ReferenceType type = ReferenceType.WEB;
    String web = type.toString();
    assertThat(web).isEqualTo(REFERENCE_TYPE_KEY + ": " + "WEB");
  }
}
