package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.EventType;
import ch.addere.osvs.lib.field.types.EventsEntry;
import ch.addere.osvs.lib.field.types.RangeEntry;
import ch.addere.osvs.lib.field.types.RangeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RangeEntrySemVerImplTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final RangeTypeField TYPE = RangeTypeField.of(RangeType.SEMVER);
  private static final EventsEntry EVENT_1 = EventsEntry.of(EventType.INTRODUCED, "1.0.0");
  private static final EventsEntry EVENT_2 = EventsEntry.of(EventType.FIXED, "1.2.4");
  private static final EventsEntry EVENT_3 = EventsEntry.of(EventType.LIMIT, "2.3.4");
  private static final EventsEntry EVENT_4 =
      EventsEntry.of(EventType.INTRODUCED, "not a semantic version");
  private static final EventsField EVENTS_1 = EventsField.of(EVENT_1, EVENT_2);
  private static final EventsField EVENTS_2 = EventsField.of(EVENT_1, EVENT_3);
  private static final EventsField EVENTS_3 = EventsField.of(EVENT_4);

  private static RepoField repo;

  @BeforeAll
  static void init() throws MalformedURLException {
    repo = RepoField.of(new URL(VALID_URL_STRING));
  }

  @Test
  void testOfTypeNull() {
    assertThatThrownBy(() -> RangeEntrySemVerImpl.of(null, repo, EVENTS_1))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument type must not be null");
  }

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> RangeEntrySemVerImpl.of(TYPE, repo, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testInvalidSemanticVersion() {
    assertThatThrownBy(() -> RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_3))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must be a valid semantic version");
  }

  @Test
  void testKeyValueWithRepo() {
    RangeEntry semVerEvent = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    assertThat(semVerEvent)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(TYPE);
              assertThat(semVer.repo()).contains(repo);
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueWithoutRepo() {
    RangeEntry semVerEvent = RangeEntrySemVerImpl.of(TYPE, null, EVENTS_1);
    assertThat(semVerEvent)
        .satisfies(
            semVer -> {
              assertThat(semVer.type()).isEqualTo(TYPE);
              assertThat(semVer.repo()).isEmpty();
              assertThat(semVer.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testEquality() {
    RangeEntry semVerEvent1 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry semVerEvent2 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    assertThat(semVerEvent1)
        .satisfies(
            g -> {
              assertThat(g).isEqualTo(semVerEvent1);
              assertThat(g).isEqualTo(semVerEvent2);
            });
  }

  @Test
  void testNonEquality() {
    RangeEntry semVerEvent1 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry semVerEvent2 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_2);
    assertThat(semVerEvent1)
        .satisfies(
            g -> {
              assertThat(g).isNotEqualTo(null);
              assertThat(g).isNotEqualTo(semVerEvent2);
            });
  }

  @Test
  void testHashCode() {
    RangeEntry semVerEvent1 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry semVerEvent2 = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    assertThat(semVerEvent1).hasSameHashCodeAs(semVerEvent2);
  }

  @Test
  void testToStringWithRepo() {
    RangeEntry semVerEvent = RangeEntrySemVerImpl.of(TYPE, repo, EVENTS_1);
    assertThat(semVerEvent)
        .hasToString(
            "type: SEMVER\nrepo: https://osv.dev/\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }

  @Test
  void testToStringWithoutRepo() {
    RangeEntry semVerEvent = RangeEntrySemVerImpl.of(TYPE, null, EVENTS_1);
    assertThat(semVerEvent)
        .hasToString("type: SEMVER\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }
}
