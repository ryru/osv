package ch.addere.osv.spec;

import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.FIXED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.property.PublishedValue;
import ch.addere.osv.property.SchemaVersionValue;
import ch.addere.osv.property.WithdrawnValue;
import ch.addere.osv.property.affected.ranges.RepoValue;
import ch.addere.osv.property.affected.ranges.TypeEcosystemValues.TypeEcosystemBuilder;
import ch.addere.osv.property.affected.ranges.TypeGitValues.TypeGitBuilder;
import ch.addere.osv.property.affected.ranges.TypeSemVerValues;
import ch.addere.osv.property.affected.ranges.TypeSemVerValues.TypeSemVerBuilder;
import ch.addere.osv.property.affected.ranges.events.EcosystemEventValues;
import ch.addere.osv.property.affected.ranges.events.GitEventValues;
import ch.addere.osv.property.affected.ranges.events.SemVerEventValues;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * This test class address additional constrains, not already covered by the value property created,
 * that are required by the specification as of version 1.0.0 (September 8, 2021).
 */
final class SpecVersion100Test {

  public static final String INVALID_VERSION_MUST_NOT_BE_0_WITH_NON_INTRODUCED_EVENTS =
      "invalid version, must not be 0 with non 'introduced' events";
  public static final String INVALID_VERSION_MUST_NOT_BE_WITH_NON_LIMITED_EVENTS =
      "invalid version, must not be * with non 'limited' events";
  public static final String VER_001 = "0.0.1";
  public static final String VER_010 = "0.1.0";
  public static final String VER_100 = "1.0.0";

  //------------------------------------------------------------------------------------------------
  // schema_version field
  //------------------------------------------------------------------------------------------------

  // The value should be a string matching the OSV Schema version, which follows the SemVer 2.0.0
  // format, with no leading “v” prefix.
  @Test
  void testSchemaVersionNoLeadingV() {
    assertThatThrownBy(() -> SchemaVersionValue.fromString("v1.0.0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid schema version, must not start with 'v'");
  }

  //------------------------------------------------------------------------------------------------
  // published field
  //------------------------------------------------------------------------------------------------

  // The published field gives the time the entry should be considered to have been published, as
  // an RFC3339-formatted time stamp in UTC (ending in “Z”).
  @Test
  void testPublishedEndsWithZ() {
    PublishedValue published = PublishedValue.of(Instant.now());
    assertThat(published.value().toString()).endsWith("Z");
  }

  //------------------------------------------------------------------------------------------------
  // withdrawn field
  //------------------------------------------------------------------------------------------------

  // The withdrawn field gives the time the entry should be considered to have been withdrawn, as
  // an RFC3339-formatted timestamp in UTC (ending in “Z”).
  @Test
  void testWithdrawnEndsWithZ() {
    WithdrawnValue withdrawnValue = WithdrawnValue.of(Instant.now());
    assertThat(withdrawnValue.value().toString()).endsWith("Z");
  }

  //------------------------------------------------------------------------------------------------
  // affected field
  //------------------------------------------------------------------------------------------------

  // In short, each object in the `affected` array must contain either a non-empty `versions` list
  // or at least one range in the `ranges` list of type `SEMVER`.

  // TODO

  //------------------------------------------------------------------------------------------------
  // affected[].ranges[].type field
  //------------------------------------------------------------------------------------------------

  // The versions introduced and fixed are semantic versions as defined by SemVer 2.0.0, with no
  // leading “v” prefix.
  @Test
  void testAffectedRangesTypeSemVerNoLeadingV() {
    assertThatThrownBy(() -> new TypeSemVerBuilder(
        SemVerEventValues.of(INTRODUCED, "v1.0.0")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version, must not start with 'v'");
  }

  //------------------------------------------------------------------------------------------------
  // affected[].ranges[].events field
  //------------------------------------------------------------------------------------------------

  // "introduced" allows a version of the value "0" to represent a version that sorts before any
  // other version.
  @Test
  void testAffectedRangesEventsEcosystemIntroducedAllowVersion0() {
    EcosystemEventValues ecosystemEvent = EcosystemEventValues.of(INTRODUCED, "0");
    assertThat(ecosystemEvent.version()).isEqualTo("0");
  }

  @Test
  void testAffectedRangesEventsEcosystemFixedDenyVersion0() {
    assertThatThrownBy(() -> EcosystemEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_0_WITH_NON_INTRODUCED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsEcosystemLimitedDenyVersion0() {
    assertThatThrownBy(() -> EcosystemEventValues.of(LIMITED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_0_WITH_NON_INTRODUCED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsGitIntroducedAllowVersion0() {
    GitEventValues gitEvent = GitEventValues.of(INTRODUCED, "0");
    assertThat(gitEvent.version()).isEqualTo("0");
  }

  @Test
  void testAffectedRangesEventsGitFixedDenyVersion0() {
    assertThatThrownBy(() -> GitEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_0_WITH_NON_INTRODUCED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsGitLimitedDenyVersion0() {
    assertThatThrownBy(() -> GitEventValues.of(LIMITED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_0_WITH_NON_INTRODUCED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsSemVerIntroducedAllowVersion0() {
    SemVerEventValues semVerEvent = SemVerEventValues.of(INTRODUCED, "0");
    assertThat(semVerEvent.version()).isEqualTo("0");
  }

  @Test
  void testAffectedRangesEventsSemVerFixedDenyVersion0() {
    assertThatThrownBy(() -> SemVerEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "invalid semantic version, must not be 0 with non 'introduced' events");
  }

  @Test
  void testAffectedRangesEventsSemVerLimitedDenyVersion0() {
    assertThatThrownBy(() -> SemVerEventValues.of(LIMITED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "invalid semantic version, must not be 0 with non 'introduced' events");
  }

  // "limit" allows versions containing the string "*" to represent “infinity”.
  @Test
  void testAffectedRangesEventsEcosystemLimitedAllowVersionAsterisk() {
    EcosystemEventValues ecosystemEvent = EcosystemEventValues.of(LIMITED, "*");
    assertThat(ecosystemEvent.version()).isEqualTo("*");
  }

  @Test
  void testAffectedRangesEventsEcosystemIntroducedDenyVersionAsterisk() {
    assertThatThrownBy(() -> EcosystemEventValues.of(INTRODUCED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_WITH_NON_LIMITED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsEcosystemFixedDenyVersionAsterisk() {
    assertThatThrownBy(() -> EcosystemEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_WITH_NON_LIMITED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsGitLimitedAllowVersionAsterisk() {
    GitEventValues gitEvent = GitEventValues.of(LIMITED, "*");
    assertThat(gitEvent.version()).isEqualTo("*");
  }

  @Test
  void testAffectedRangesEventsGitIntroducedDenyVersionAsterisk() {
    assertThatThrownBy(() -> GitEventValues.of(INTRODUCED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_WITH_NON_LIMITED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsGitFixedDenyVersionAsterisk() {
    assertThatThrownBy(() -> GitEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(INVALID_VERSION_MUST_NOT_BE_WITH_NON_LIMITED_EVENTS);
  }

  @Test
  void testAffectedRangesEventsSemVerLimitedAllowVersionAsterisk() {
    SemVerEventValues semVerEvent = SemVerEventValues.of(LIMITED, "*");
    assertThat(semVerEvent.version()).isEqualTo("*");
  }

  @Test
  void testAffectedRangesEventsSemVerIntroducedDenyVersionAsterisk() {
    assertThatThrownBy(() -> SemVerEventValues.of(INTRODUCED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version, must not be * with non 'limited' events");
  }

  @Test
  void testAffectedRangesEventsSemVerFixedDenyVersionAsterisk() {
    assertThatThrownBy(() -> SemVerEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version, must not be * with non 'limited' events");
  }

  // Multiple "limit" events are allowed in the same range.
  @Test
  void testAffectedRangesEventsSemVerMultipleLimitedEvents() {
    SemVerEventValues semVerEvent1 = SemVerEventValues.of(INTRODUCED, VER_001);
    SemVerEventValues semVerEvent2 = SemVerEventValues.of(LIMITED, VER_010);
    SemVerEventValues semVerEvent3 = SemVerEventValues.of(LIMITED, VER_100);

    TypeSemVerValues semVer = new TypeSemVerBuilder(semVerEvent1, semVerEvent2, semVerEvent3)
        .build();

    assertThat(semVer.events()).containsExactly(semVerEvent1, semVerEvent2, semVerEvent3);
  }

  // There must be at least one "introduced" object in the events array.
  @Test
  void testAffectedRangesEventsEcosystemAtLeastOneIntroduced() {
    EcosystemEventValues ecosystemEvent1 = EcosystemEventValues.of(FIXED, VER_001);
    EcosystemEventValues ecosystemEvent2 = EcosystemEventValues.of(LIMITED, VER_010);
    EcosystemEventValues ecosystemEvent3 = EcosystemEventValues.of(LIMITED, VER_100);

    assertThatThrownBy(() -> new TypeEcosystemBuilder(
        ecosystemEvent1, ecosystemEvent2, ecosystemEvent3))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }

  @Test
  void testAffectedRangesEventsGitAtLeastOneIntroduced() {
    GitEventValues semVerEvent1 = GitEventValues.of(FIXED, VER_001);
    GitEventValues semVerEvent2 = GitEventValues.of(LIMITED, VER_010);
    GitEventValues semVerEvent3 = GitEventValues.of(LIMITED, VER_100);

    assertThatThrownBy(() -> new TypeGitBuilder(
        RepoValue.fromString("https://osv.dev/"), semVerEvent1, semVerEvent2, semVerEvent3))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }

  @Test
  void testAffectedRangesEventsSemVerAtLeastOneIntroduced() {
    SemVerEventValues semVerEvent1 = SemVerEventValues.of(FIXED, VER_001);
    SemVerEventValues semVerEvent2 = SemVerEventValues.of(LIMITED, VER_010);
    SemVerEventValues semVerEvent3 = SemVerEventValues.of(LIMITED, VER_100);

    assertThatThrownBy(() -> new TypeSemVerBuilder(semVerEvent1, semVerEvent2, semVerEvent3))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }
}
