package ch.addere.osv.domain.model.fields.affected.ranges;

import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class TypeTest {

  @Test
  void testJsonKey() {
    assertThat(TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testSemVerToString() {
    Type typeSemVer = Type.SEMVER;
    String semVer = typeSemVer.toString();
    assertThat(semVer).isEqualTo(TYPE_KEY + ": " + "SEMVER");
  }

  @Test
  void testGitToString() {
    Type typeGit = Type.GIT;
    String git = typeGit.toString();
    assertThat(git).isEqualTo(TYPE_KEY + ": " + "GIT");
  }

  @Test
  void testEcosystemToString() {
    Type typeEcosystem = Type.ECOSYSTEM;
    String ecosystem = typeEcosystem.toString();
    assertThat(ecosystem).isEqualTo(TYPE_KEY + ": " + "ECOSYSTEM");
  }
}
