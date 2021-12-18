package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl.ECOSYSTEM_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.pckg.Ecosystem;
import org.junit.jupiter.api.Test;

class EcosystemImplTest {

  @Test
  void testJsonKey() {
    assertThat(ECOSYSTEM_KEY).isEqualTo("ecosystem");
  }

  @Test
  void testValidEcosystem() {
    Ecosystem ecosystem = EcosystemImpl.GO;
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testValidOfGo() {
    Ecosystem ecosystem = EcosystemImpl.of("Go");
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> EcosystemImpl.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid ecosystem");
  }

  @Test
  void testOfValueObjectCreation() {
    Ecosystem ecosystem1 = EcosystemImpl.GO;
    Ecosystem ecosystem2 = EcosystemImpl.of(ecosystem1.value());
    assertThat(ecosystem1).isEqualTo(ecosystem2);
  }

  @Test
  void testGoToString() {
    Ecosystem ecosystem = EcosystemImpl.GO;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Go");
  }

  @Test
  void testNpmToString() {
    Ecosystem ecosystem = EcosystemImpl.NPM;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": npm");
  }

  @Test
  void testOssFuzzToString() {
    Ecosystem ecosystem = EcosystemImpl.OSS_FUZZ;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": OSS-Fuzz");
  }

  @Test
  void testPyPiToString() {
    Ecosystem ecosystem = EcosystemImpl.PYPI;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": PyPI");
  }

  @Test
  void testRubyGemsToString() {
    Ecosystem ecosystem = EcosystemImpl.RUBY_GEMS;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": RubyGems");
  }

  @Test
  void testCratesIdToString() {
    Ecosystem ecosystem = EcosystemImpl.CRATES_IO;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": crates.io");
  }

  @Test
  void testPackagistToString() {
    Ecosystem ecosystem = EcosystemImpl.PACKAGIST;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Packagist");
  }

  @Test
  void testMavenToString() {
    Ecosystem ecosystem = EcosystemImpl.MAVEN;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Maven");
  }

  @Test
  void testNuGetToString() {
    Ecosystem ecosystem = EcosystemImpl.NU_GET;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": NuGet");
  }

  @Test
  void testLinuxToString() {
    Ecosystem ecosystem = EcosystemImpl.LINUX;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Linux");
  }

  @Test
  void testDebianToString() {
    Ecosystem ecosystem = EcosystemImpl.DEBIAN;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Debian");
  }

  @Test
  void testHexToString() {
    Ecosystem ecosystem = EcosystemImpl.HEX;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Hex");
  }
}
