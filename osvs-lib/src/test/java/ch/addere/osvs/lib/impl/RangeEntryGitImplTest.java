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

class RangeEntryGitImplTest {

  private static final String VALID_URL_STRING = "https://osv.dev/";
  private static final RangeTypeField TYPE = RangeTypeField.of(RangeType.GIT);
  private static final EventsEntry EVENT_1 = EventsEntry.of(EventType.INTRODUCED, "a git commit1");
  private static final EventsEntry EVENT_2 = EventsEntry.of(EventType.FIXED, "a git commit2");
  private static final EventsEntry EVENT_3 = EventsEntry.of(EventType.LIMIT, "a git commit3");
  private static final EventsField EVENTS_1 = EventsField.of(EVENT_1, EVENT_2);
  private static final EventsField EVENTS_2 = EventsField.of(EVENT_1, EVENT_3);

  private static RepoField repo;

  @BeforeAll
  static void init() throws MalformedURLException {
    repo = RepoField.of(new URL(VALID_URL_STRING));
  }

  @Test
  void testOfTypeNull() {
    assertThatThrownBy(() -> RangeEntryGitImpl.of(null, repo, EVENTS_1))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument type must not be null");
  }

  @Test
  void testOfRepoNull() {
    assertThatThrownBy(() -> RangeEntryGitImpl.of(TYPE, null, EVENTS_1))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument repo must not be null");
  }

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> RangeEntryGitImpl.of(TYPE, repo, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testKeyValue() {
    RangeEntry gitEvent = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    assertThat(gitEvent)
        .satisfies(
            git -> {
              assertThat(git.type()).isEqualTo(TYPE);
              assertThat(git.repo()).contains(repo);
              assertThat(git.events()).isEqualTo(EVENTS_1);
            });
  }

  @Test
  void testEquality() {
    RangeEntry gitEvent1 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry gitEvent2 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    assertThat(gitEvent1)
        .satisfies(
            g -> {
              assertThat(g).isEqualTo(gitEvent1);
              assertThat(g).isEqualTo(gitEvent2);
            });
  }

  @Test
  void testNonEquality() {
    RangeEntry gitEvent1 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry gitEvent2 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_2);
    assertThat(gitEvent1)
        .satisfies(
            g -> {
              assertThat(g).isNotEqualTo(null);
              assertThat(g).isNotEqualTo(gitEvent2);
            });
  }

  @Test
  void testHashCode() {
    RangeEntry gitEvent1 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    RangeEntry gitEvent2 = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    assertThat(gitEvent1).hasSameHashCodeAs(gitEvent2);
  }

  @Test
  void testToString() {
    RangeEntry gitEvent = RangeEntryGitImpl.of(TYPE, repo, EVENTS_1);
    assertThat(gitEvent)
        .hasToString(
            "type: GIT\nrepo: https://osv.dev/\nevents: [ (introduced: a git commit1), (fixed: a git commit2) ]");
  }
}
