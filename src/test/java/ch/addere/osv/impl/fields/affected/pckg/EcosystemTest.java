package ch.addere.osv.impl.fields.affected.pckg;

import static ch.addere.osv.impl.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EcosystemTest {

  @Test
  void testJsonKey() {
    assertThat(ECOSYSTEM_KEY).isEqualTo("ecosystem");
  }

  @Test
  void testValidEcosystem() {
    Ecosystem ecosystem = Ecosystem.Go;
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testValidValueGo() {
    Ecosystem ecosystem = Ecosystem.value("Go");
    assertThat(ecosystem.value()).isEqualTo("Go");
  }

  @Test
  void testInvalidValueNonExisting() {
    assertThatThrownBy(() -> Ecosystem.value("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid ecosystem");
  }

  @Test
  void testGoToString() {
    Ecosystem ecosystem = Ecosystem.Go;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Go");
  }

  @Test
  void testNpmToString() {
    Ecosystem ecosystem = Ecosystem.npm;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": npm");
  }

  @Test
  void testOssFuzzToString() {
    Ecosystem ecosystem = Ecosystem.OSS_FUZZ;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": OSS-Fuzz");
  }

  @Test
  void testPyPiToString() {
    Ecosystem ecosystem = Ecosystem.PYPI;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": PyPI");
  }

  @Test
  void testRubyGemsToString() {
    Ecosystem ecosystem = Ecosystem.RUBY_GEMS;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": RubyGems");
  }

  @Test
  void testCratesIdToString() {
    Ecosystem ecosystem = Ecosystem.CRATES_IO;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": crates.io");
  }

  @Test
  void testPackagistToString() {
    Ecosystem ecosystem = Ecosystem.PACKAGIST;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": packagist");
  }

  @Test
  void testMavenToString() {
    Ecosystem ecosystem = Ecosystem.MAVEN;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Maven");
  }

  @Test
  void testNuGetToString() {
    Ecosystem ecosystem = Ecosystem.NU_GET;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": NuGet");
  }

  @Test
  void testLinuxToString() {
    Ecosystem ecosystem = Ecosystem.LINUX;
    String eco = ecosystem.toString();
    assertThat(eco).isEqualTo(ECOSYSTEM_KEY + ": Linux");
  }
}
