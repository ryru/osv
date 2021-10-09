package ch.addere.osv.domain.model.fields.affected;

import static ch.addere.osv.domain.model.fields.affected.Package.PACKAGE_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Name.NAME_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Purl.PURL_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import org.junit.jupiter.api.Test;

class PackageTest {

  private static final Ecosystem LINUX_ECOSYSTEM = Ecosystem.LINUX;
  private static final Name NAME = Name.of("aName");
  private static final Name ANOTHER_NAME = Name.of("anotherName");
  private static final Purl PURL = Purl.of("aPurl");
  private static final Purl ANOTHER_PURL = Purl.of("anotherPurl");

  @Test
  void testJsonKey() {
    assertThat(PACKAGE_KEY).isEqualTo("package");
  }

  @Test
  void testValidMinimalPackage() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME);
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
    });
  }

  @Test
  void testValidPackage() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
      assertThat(p.purl().get()).isEqualTo(PURL);
    });
  }

  @Test
  void testSameness() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = pckg;
    assertThat(pckg).isEqualTo(otherPckg);
  }

  @Test
  void testEquality() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).isEqualTo(otherPckg);
  }

  @Test
  void testNonEquality() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = Package.of(LINUX_ECOSYSTEM, ANOTHER_NAME, ANOTHER_PURL);
    assertThat(pckg).isNotEqualTo(otherPckg);
  }

  @Test
  void testHashCode() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg.hashCode()).isEqualTo(otherPckg.hashCode());
  }

  @Test
  void testToString() {
    Package pckg = Package.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg.toString()).isEqualTo(PACKAGE_KEY + ": " + join(", ",
        ecosystemToString(),
        nameToString(),
        purlToString()));
  }

  private static String ecosystemToString() {
    return ECOSYSTEM_KEY + ": " + "Linux";
  }

  private static String nameToString() {
    return NAME_KEY + ": " + "aName";
  }

  private static String purlToString() {
    return PURL_KEY + ": " + "aPurl";
  }
}
