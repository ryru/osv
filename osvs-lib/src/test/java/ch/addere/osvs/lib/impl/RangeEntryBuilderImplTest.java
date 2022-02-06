package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RangeEntryBuilderImplTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final RangeTypeField TYPE = RangeTypeField.of(RangeType.SEMVER);
  private static final EventsEntry EVENT_1 = EventsEntry.of(EventType.INTRODUCED, "1.0.0");
  private static final EventsEntry EVENT_2 = EventsEntry.of(EventType.FIXED, "1.2.4");
  private static final EventsEntry EVENT_3 = EventsEntry.of(EventType.LIMIT, "2.3.4");
  private static final EventsField EVENTS_1 = EventsField.of(EVENT_1, EVENT_2);
  private static final EventsField EVENTS_2 = EventsField.of(EVENT_1, EVENT_3);

  private static RepoField repo;

  @BeforeAll
  static void init() throws MalformedURLException {
    repo = RepoField.of(new URL(VALID_URL_STRING));
  }

  @Test
  void testOfTypeNull() {
    assertThatThrownBy(() -> RangeEntryBuilder.builder(null, EVENTS_1))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument type must not be null");
  }

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> RangeEntryBuilder.builder(TYPE, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testKeyValueWithRepo() {
    RangeEntry rangeEntry =
        RangeEntryBuilder.builder(TYPE, EVENTS_1)
            .setRepo(repo)
            .build();
    assertThat(rangeEntry)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(TYPE);
              assertThat(semVer.repo()).contains(repo);
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueWithoutRepo() {
    RangeEntry rangeEntry = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    assertThat(rangeEntry)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(TYPE);
              assertThat(semVer.repo()).isEmpty();
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueTypeGit() {
    RangeEntry rangeEntry =
        RangeEntryBuilder.builder(RangeTypeField.of(RangeType.GIT), EVENTS_1)
            .setRepo(repo)
            .build();
    assertThat(rangeEntry)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(RangeTypeField.of(RangeType.GIT));
              assertThat(semVer.repo()).contains(repo);
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueTypeEcosystem() {
    RangeEntry rangeEntry =
        RangeEntryBuilder.builder(RangeTypeField.of(RangeType.ECOSYSTEM), EVENTS_1)
            .setRepo(repo)
            .build();
    assertThat(rangeEntry)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(RangeTypeField.of(RangeType.ECOSYSTEM));
              assertThat(semVer.repo()).contains(repo);
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueTypeSemVer() {
    RangeEntry rangeEntry =
        RangeEntryBuilder.builder(RangeTypeField.of(RangeType.SEMVER), EVENTS_1)
            .setRepo(repo)
            .build();
    assertThat(rangeEntry)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(RangeTypeField.of(RangeType.SEMVER));
              assertThat(semVer.repo()).contains(repo);
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testEquality() {
    RangeEntry rangeEntry1 = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    RangeEntry rangeEntry2 = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    assertThat(rangeEntry1)
        .satisfies(
            g -> {
              assertThat(g).isEqualTo(rangeEntry1);
              assertThat(g).isEqualTo(rangeEntry2);
            });
  }

  @Test
  void testNonEquality() {
    RangeEntry rangeEntry1 = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    RangeEntry rangeEntry2 = RangeEntryBuilder.builder(TYPE, EVENTS_2).build();
    assertThat(rangeEntry1)
        .satisfies(
            g -> {
              assertThat(g).isNotEqualTo(null);
              assertThat(g).isNotEqualTo(rangeEntry2);
            });
  }

  @Test
  void testHashCode() {
    RangeEntry rangeEntry1 = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    RangeEntry rangeEntry2 = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    assertThat(rangeEntry1).hasSameHashCodeAs(rangeEntry2);
  }

  @Test
  void testToStringWithRepo() {
    RangeEntry rangeEntry = RangeEntryBuilder.builder(TYPE, EVENTS_1).setRepo(repo).build();
    assertThat(rangeEntry)
        .hasToString(
            "type: SEMVER\nrepo: https://osv.dev/\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }

  @Test
  void testToStringWithoutRepo() {
    RangeEntry rangeEntry = RangeEntryBuilder.builder(TYPE, EVENTS_1).build();
    assertThat(rangeEntry)
        .hasToString("type: SEMVER\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }
}
