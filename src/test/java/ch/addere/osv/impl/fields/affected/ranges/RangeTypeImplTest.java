package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeImpl.TYPE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.RangeType;
import org.junit.jupiter.api.Test;

class RangeTypeImplTest {

  private static final String ECOSYSTEM = "ECOSYSTEM";

  @Test
  void testJsonKey() {
    assertThat(TYPE_KEY).isEqualTo("type");
  }

  @Test
  void testValidRangeType() {
    RangeType rangeType = RangeTypeImpl.ECOSYSTEM;
    assertThat(rangeType.value()).isEqualTo(ECOSYSTEM);
  }

  @Test
  void testValidOfEcosystem() {
    RangeType rangeType = RangeTypeImpl.of(ECOSYSTEM);
    assertThat(rangeType.value()).isEqualTo(ECOSYSTEM);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> RangeTypeImpl.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid range type");
  }

  @Test
  void testOfValueObjectCreation() {
    RangeType rangeType1 = RangeTypeImpl.ECOSYSTEM;
    RangeType rangeType2 = RangeTypeImpl.of(rangeType1.value());
    assertThat(rangeType1).isEqualTo(rangeType2);
  }

  @Test
  void testSemVerToString() {
    RangeType typeSemVer = RangeTypeImpl.SEMVER;
    String semVer = typeSemVer.toString();
    assertThat(semVer).isEqualTo(TYPE_KEY + ": " + "SEMVER");
  }

  @Test
  void testGitToString() {
    RangeType typeGit = RangeTypeImpl.GIT;
    String git = typeGit.toString();
    assertThat(git).isEqualTo(TYPE_KEY + ": " + "GIT");
  }

  @Test
  void testEcosystemToString() {
    RangeType typeEcosystem = RangeTypeImpl.ECOSYSTEM;
    String ecosystem = typeEcosystem.toString();
    assertThat(ecosystem).isEqualTo(TYPE_KEY + ": " + ECOSYSTEM);
  }
}
