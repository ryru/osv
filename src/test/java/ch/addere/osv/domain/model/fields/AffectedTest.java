package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Affected.AFFECTED_KEY;
import static ch.addere.osv.domain.model.fields.affected.DatabaseSpecific.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.EcosystemSpecific.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.Package.PACKAGE_KEY;
import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.Versions.VERSIONS_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Name.NAME_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Purl.PURL_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.domain.model.fields.Affected.AffectedBuilder;
import ch.addere.osv.domain.model.fields.affected.DatabaseSpecific;
import ch.addere.osv.domain.model.fields.affected.EcosystemSpecific;
import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.Versions;
import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeSemVer;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.SemVerEvent;
import org.junit.jupiter.api.Test;

class AffectedTest {

  private static final String VERSION = "aVersion";

  @Test
  void testJsonKey() {
    assertThat(AFFECTED_KEY).isEqualTo("affected");
  }

  @Test
  void testSameness() {
    Affected affected = new AffectedBuilder(pckg()).build();
    Affected otherAffected = affected;
    assertThat(affected).isEqualTo(otherAffected);
  }

  @Test
  void testEquality() {
    Affected affected = new AffectedBuilder(pckg()).build();
    Affected otherAffected = new AffectedBuilder(pckg()).build();
    assertThat(affected).isEqualTo(otherAffected);
  }

  @Test
  void testNonEquality() {
    Affected affected = new AffectedBuilder(pckg()).versions(Versions.of(VERSION)).build();
    Affected otherAffected = new AffectedBuilder(pckg()).versions(Versions.of("anotherVersion"))
        .build();
    assertThat(affected).isNotEqualTo(otherAffected);
  }

  @Test
  void testHashCode() {
    Affected affected = new AffectedBuilder(pckg()).ranges(ranges()).versions(Versions.of(VERSION))
        .build();
    Affected otherAffected = new AffectedBuilder(pckg()).ranges(ranges())
        .versions(Versions.of(VERSION)).build();
    assertThat(affected.hashCode()).isEqualTo(otherAffected.hashCode());
  }

  @Test
  void testToString() {
    Affected affected = new AffectedBuilder(pckg())
        .ranges(ranges())
        .versions(Versions.of(VERSION))
        .ecosystemSpecific(EcosystemSpecific.of("{\"some\":\"ecosystem specific properties\"}"))
        .databaseSpecific(DatabaseSpecific.of("{\"some\":\"database specific properties\"}"))
        .build();
    assertThat(affected.toString()).isEqualTo(AFFECTED_KEY + ": "
        + join(", ",
        packageToString(),
        rangesToString(),
        versionsToString(),
        ecosystemSpecificToString(),
        databaseSpecificToString()));
  }

  private static Package pckg() {
    return Package.of(Ecosystem.Go, Name.of("aName"), Purl.of("aPurl"));
  }

  private static Ranges ranges() {
    return TypeSemVer.of(SemVerEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  private static String packageToString() {
    return PACKAGE_KEY + ": "
        + join(", ",
        packageEcosystemToString(),
        packageNameToString(),
        packagePurlToString());
  }

  private static String packageEcosystemToString() {
    return ECOSYSTEM_KEY + ": " + "Go";
  }

  private static String packageNameToString() {
    return NAME_KEY + ": " + "aName";
  }

  private static String packagePurlToString() {
    return PURL_KEY + ": " + "aPurl";
  }

  private static String rangesToString() {
    return RANGES_KEY + ": " + join(", ",
        rangesTypeToString(),
        rangesEventsToString());
  }

  private static String rangesTypeToString() {
    return TYPE_KEY + ": " + "SEMVER";
  }

  private static String rangesEventsToString() {
    return EVENTS_KEY + ": " + join(", ", "introduced", "1.0.0");
  }

  private static String versionsToString() {
    return VERSIONS_KEY + ": " + VERSION;
  }

  private static String ecosystemSpecificToString() {
    return ECOSYSTEM_SPECIFIC_KEY + ": " + "{\"some\":\"ecosystem specific properties\"}";
  }

  private static String databaseSpecificToString() {
    return DATABASE_SPECIFIC_KEY + ": " + "{\"some\":\"database specific properties\"}";
  }
}
