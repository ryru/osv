package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.PackageImpl.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.of;
import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.pckg.Name;
import ch.addere.osv.fields.affected.pckg.Purl;
import ch.addere.osv.impl.fields.affected.pckg.Ecosystem;
import ch.addere.osv.impl.fields.affected.pckg.PurlImpl;
import org.junit.jupiter.api.Test;

class PackageImplTest {

  private static final Ecosystem LINUX_ECOSYSTEM = Ecosystem.LINUX;
  private static final Name NAME = of("aName");
  private static final Name ANOTHER_NAME = of("anotherName");
  private static final Purl PURL = PurlImpl.of("aPurl");
  private static final Purl ANOTHER_PURL = PurlImpl.of("anotherPurl");

  @Test
  void testJsonKey() {
    assertThat(PACKAGE_KEY).isEqualTo("package");
  }

  @Test
  void testValidMinimalPackage() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME);
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
    });
  }

  @Test
  void testValidPackage() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
      assertThat(p.purl().get()).isEqualTo(PURL);
    });
  }

  @Test
  void testSameness() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = pckg;
    assertThat(pckg).isEqualTo(otherPckg);
  }

  @Test
  void testEquality() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).isEqualTo(otherPckg);
  }

  @Test
  void testNonEquality() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = PackageImpl.of(LINUX_ECOSYSTEM, ANOTHER_NAME, ANOTHER_PURL);
    assertThat(pckg).isNotEqualTo(otherPckg);
  }

  @Test
  void testHashCode() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg.hashCode()).isEqualTo(otherPckg.hashCode());
  }

  @Test
  void testToString() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
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
