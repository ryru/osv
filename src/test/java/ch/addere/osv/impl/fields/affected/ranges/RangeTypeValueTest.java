package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeValue.TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RangeTypeValueTest {

  private static final String ECOSYSTEM = "ECOSYSTEM";

  @Test
  void testJsonKey() {
    assertThat(TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testValidRangeType() {
    RangeTypeValue rangeType = RangeTypeValue.ECOSYSTEM;
    assertThat(rangeType.value()).isEqualTo(ECOSYSTEM);
  }

  @Test
  void testValidOfEcosystem() {
    RangeTypeValue rangeType = RangeTypeValue.of(ECOSYSTEM);
    assertThat(rangeType.value()).isEqualTo(ECOSYSTEM);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> RangeTypeValue.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid range type");
  }

  @Test
  void testOfValueObjectCreation() {
    RangeTypeValue rangeType1 = RangeTypeValue.ECOSYSTEM;
    RangeTypeValue rangeType2 = RangeTypeValue.of(rangeType1.value());
    assertThat(rangeType1).isEqualTo(rangeType2);
  }

  @Test
  void testSemVerToString() {
    RangeTypeValue typeSemVer = RangeTypeValue.SEMVER;
    String semVer = typeSemVer.toString();
    assertThat(semVer).isEqualTo(TYPE_KEY + ": " + "SEMVER");
  }

  @Test
  void testGitToString() {
    RangeTypeValue typeGit = RangeTypeValue.GIT;
    String git = typeGit.toString();
    assertThat(git).isEqualTo(TYPE_KEY + ": " + "GIT");
  }

  @Test
  void testEcosystemToString() {
    RangeTypeValue typeEcosystem = RangeTypeValue.ECOSYSTEM;
    String ecosystem = typeEcosystem.toString();
    assertThat(ecosystem).isEqualTo(TYPE_KEY + ": " + ECOSYSTEM);
  }
}
