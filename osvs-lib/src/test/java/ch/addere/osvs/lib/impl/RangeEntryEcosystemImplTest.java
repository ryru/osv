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

class RangeEntryEcosystemImplTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final RangeTypeField TYPE = RangeTypeField.of(RangeType.ECOSYSTEM);
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
    assertThatThrownBy(() -> RangeEntryEcosystemImpl.of(null, repo, EVENTS_1))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument type must not be null");
  }

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> RangeEntryEcosystemImpl.of(TYPE, repo, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testKeyValueWithRepo() {
    RangeEntry ecosystemEvent = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    assertThat(ecosystemEvent)
        .satisfies(
            ecosystem -> {
              assertThat(ecosystem.type()).isEqualTo(TYPE);
              assertThat(ecosystem.repo()).contains(repo);
              assertThat(ecosystem.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testKeyValueWithoutRepo() {
    RangeEntry ecosystemEvent = RangeEntryEcosystemImpl.of(TYPE, null, EVENTS_1);
    assertThat(ecosystemEvent)
        .satisfies(
            ecosystem -> {
              assertThat(ecosystem.type()).isEqualTo(TYPE);
              assertThat(ecosystem.repo()).isEmpty();
              assertThat(ecosystem.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testEquality() {
    RangeEntry ecosystemEvent1 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry ecosystemEvent2 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    assertThat(ecosystemEvent1)
        .satisfies(
            ecosystem -> {
              assertThat(ecosystem).isEqualTo(ecosystemEvent1);
              assertThat(ecosystem).isEqualTo(ecosystemEvent2);
            });
  }

  @Test
  void testNonEquality() {
    RangeEntry ecosystemEvent1 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry ecosystemEvent2 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_2);
    assertThat(ecosystemEvent1)
        .satisfies(
            ecosystem -> {
              assertThat(ecosystem).isNotEqualTo(null);
              assertThat(ecosystem).isNotEqualTo(ecosystemEvent2);
            });
  }

  @Test
  void testHashCode() {
    RangeEntry ecosystemEvent1 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry ecosystemEvent2 = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    assertThat(ecosystemEvent1).hasSameHashCodeAs(ecosystemEvent2);
  }

  @Test
  void testToStringWithRepo() {
    RangeEntry ecosystemEvent = RangeEntryEcosystemImpl.of(TYPE, repo, EVENTS_1);
    assertThat(ecosystemEvent)
        .hasToString(
            "type: ECOSYSTEM\nrepo: https://osv.dev/\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }

  @Test
  void testToStringWithoutRepo() {
    RangeEntry ecosystemEvent = RangeEntryEcosystemImpl.of(TYPE, null, EVENTS_1);
    assertThat(ecosystemEvent)
        .hasToString("type: ECOSYSTEM\nevents: [ (introduced: 1.0.0), (fixed: 1.2.4) ]");
  }
}
