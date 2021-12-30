package ch.addere.osv.property.affected.pckg;

import static ch.addere.osv.property.affected.pckg.EcosystemValue.ECOSYSTEM_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EcosystemValueTest {

  @Test
  void testJsonKey() {
    assertThat(ECOSYSTEM_KEY).isEqualTo("ecosystem");
  }

  @Test
  void testValidEcosystem() {
    EcosystemValue ecosystem = EcosystemValue.GO;
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testValidOfGo() {
    EcosystemValue ecosystem = EcosystemValue.of("Go");
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> EcosystemValue.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid ecosystem");
  }

  @Test
  void testOfValueObjectCreation() {
    EcosystemValue ecosystem1 = EcosystemValue.GO;
    EcosystemValue ecosystem2 = EcosystemValue.of(ecosystem1.value());
    assertThat(ecosystem1).isEqualTo(ecosystem2);
  }

  @Test
  void testGoToString() {
    EcosystemValue ecosystem = EcosystemValue.GO;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Go");
  }

  @Test
  void testNpmToString() {
    EcosystemValue ecosystem = EcosystemValue.NPM;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": npm");
  }

  @Test
  void testOssFuzzToString() {
    EcosystemValue ecosystem = EcosystemValue.OSS_FUZZ;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": OSS-Fuzz");
  }

  @Test
  void testPyPiToString() {
    EcosystemValue ecosystem = EcosystemValue.PYPI;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": PyPI");
  }

  @Test
  void testRubyGemsToString() {
    EcosystemValue ecosystem = EcosystemValue.RUBY_GEMS;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": RubyGems");
  }

  @Test
  void testCratesIdToString() {
    EcosystemValue ecosystem = EcosystemValue.CRATES_IO;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": crates.io");
  }

  @Test
  void testPackagistToString() {
    EcosystemValue ecosystem = EcosystemValue.PACKAGIST;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Packagist");
  }

  @Test
  void testMavenToString() {
    EcosystemValue ecosystem = EcosystemValue.MAVEN;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Maven");
  }

  @Test
  void testNuGetToString() {
    EcosystemValue ecosystem = EcosystemValue.NU_GET;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": NuGet");
  }

  @Test
  void testLinuxToString() {
    EcosystemValue ecosystem = EcosystemValue.LINUX;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Linux");
  }

  @Test
  void testDebianToString() {
    EcosystemValue ecosystem = EcosystemValue.DEBIAN;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Debian");
  }

  @Test
  void testHexToString() {
    EcosystemValue ecosystem = EcosystemValue.HEX;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Hex");
  }
}
