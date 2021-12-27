package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.PackageValues.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.EcosystemValue.ECOSYSTEM_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameValue.NAME_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.PurlValue.PURL_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.impl.fields.affected.PackageValues.PackageValueBuilder;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import org.junit.jupiter.api.Test;

class PackageValuesTest {

  private static final EcosystemValue LINUX_ECOSYSTEM = EcosystemValue.LINUX;
  private static final NameValue NAME = NameValue.fromString("aName");
  private static final NameValue ANOTHER_NAME = NameValue.fromString("anotherName");
  private static final String PURL_STRING = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  private static final String OTHER_PURL_STRING = "pkg:docker/cassandra@sha256:244fd47e07d1004f9c";
  private static final PurlValue PURL = PurlValue.fromString(PURL_STRING);
  private static final PurlValue OTHER_PURL = PurlValue.fromString(OTHER_PURL_STRING);

  @Test
  void testJsonKey() {
    assertThat(PACKAGE_KEY).isEqualTo("package");
  }

  private static PackageValueBuilder builder(EcosystemValue ecosystem, NameValue name) {
    return new PackageValueBuilder(ecosystem, name);
  }

  @Test
  void testOfEcosystemNull() {
    assertThatThrownBy(() -> builder(null, NAME))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystem must not be null");
  }

  @Test
  void testOfNameNull() {
    assertThatThrownBy(() -> builder(LINUX_ECOSYSTEM, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument name must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    PackageValues pckg1 = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    PackageValues pckg2 = builder(pckg1.ecosystem(), pckg1.name()).purl(pckg1.purl().get()).build();
    assertThat(pckg1).isEqualTo(pckg2);
  }

  @Test
  void testValidMinimalPackage() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).build();
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
    });
  }

  @Test
  void testValidPackage() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    assertThat(pckg).satisfies(p -> {
      assertThat(p.ecosystem()).isEqualTo(LINUX_ECOSYSTEM);
      assertThat(p.name()).isEqualTo(NAME);
      assertThat(p.purl()).contains(PURL);
    });
  }

  @Test
  void testEquality() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    PackageValues otherPckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    assertThat(pckg).satisfies(p -> {
      assertThat(p).isEqualTo(pckg);
      assertThat(p).isEqualTo(otherPckg);
    });
  }

  @Test
  void testNonEquality() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    PackageValues otherPckg = builder(LINUX_ECOSYSTEM, ANOTHER_NAME).purl(OTHER_PURL).build();
    assertThat(pckg).satisfies(p -> {
      assertThat(p).isNotEqualTo(null);
      assertThat(p).isNotEqualTo(otherPckg);
    });
  }

  @Test
  void testHashCode() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    PackageValues otherPckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
    assertThat(pckg).hasSameHashCodeAs(otherPckg);
  }

  @Test
  void testToString() {
    PackageValues pckg = builder(LINUX_ECOSYSTEM, NAME).purl(PURL).build();
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
