package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.PackageImpl.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl.ECOSYSTEM_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.of;
import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.pckg.Name;
import ch.addere.osv.fields.affected.pckg.Purl;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl;
import ch.addere.osv.impl.fields.affected.pckg.PurlImpl;
import org.junit.jupiter.api.Test;

class PackageImplTest {

  private static final EcosystemImpl LINUX_ECOSYSTEM = EcosystemImpl.LINUX;
  private static final Name NAME = of("aName");
  private static final Name ANOTHER_NAME = of("anotherName");
  private static final String PURL_STRING = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  private static final String OTHER_PURL_STRING = "pkg:docker/cassandra@sha256:244fd47e07d1004f9c";
  private static final Purl PURL = PurlImpl.of(PURL_STRING);
  private static final Purl OTHER_PURL = PurlImpl.of(OTHER_PURL_STRING);

  @Test
  void testJsonKey() {
    assertThat(PACKAGE_KEY).isEqualTo("package");
  }

  @Test
  void testOfEcosystemNull() {
    assertThatThrownBy(() -> PackageImpl.of(null, NAME))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystem must not be null");
  }

  @Test
  void testOfNameNull() {
    assertThatThrownBy(() -> PackageImpl.of(LINUX_ECOSYSTEM, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument name must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Package pckg1 = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package pckg2 = PackageImpl.of(pckg1.ecosystem(), pckg1.name(), pckg1.purl().get());
    assertThat(pckg1).isEqualTo(pckg2);
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
      assertThat(p.purl()).contains(PURL);
    });
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
    Package otherPckg = PackageImpl.of(LINUX_ECOSYSTEM, ANOTHER_NAME, OTHER_PURL);
    assertThat(pckg).isNotEqualTo(otherPckg);
  }

  @Test
  void testHashCode() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    Package otherPckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).hasSameHashCodeAs(otherPckg);
  }

  @Test
  void testToString() {
    Package pckg = PackageImpl.of(LINUX_ECOSYSTEM, NAME, PURL);
    assertThat(pckg).hasToString(PACKAGE_KEY + ": " + join(", ",
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
    return PURL_KEY + ": " + PURL_STRING;
  }
}
