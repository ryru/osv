package ch.addere.osv.property.affected.ranges;

import static ch.addere.osv.property.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.property.affected.ranges.RepoValue.REPO_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.ranges.TypeGitValues.TypeGitBuilder;
import ch.addere.osv.property.affected.ranges.events.EventSpecifierValue;
import ch.addere.osv.property.affected.ranges.events.GitEventValues;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeGitValuesTest {

  @Test
  void testOfRepoNull() {
    assertThatThrownBy(() -> new TypeGitBuilder(null, introducedEvent()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument repo must not be null");
  }

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> new TypeGitBuilder(repo(), (GitEventValues[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Ranges ranges1 = new TypeGitBuilder(repo(), introducedEvent()).build();
    Ranges ranges2 = new TypeGitBuilder(
        ranges1.repo().get(),
        ranges1.events().toArray(new GitEventValues[0])).build();
    assertThat(ranges1).isEqualTo(ranges2);
  }

  @Test
  void testEquality() {
    Ranges type = new TypeGitBuilder(repo(), introducedEvent()).build();
    Ranges otherType = new TypeGitBuilder(repo(), introducedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isEqualTo(type);
      assertThat(t).isEqualTo(otherType);
    });
  }

  @Test
  void testMissingIntroducedEvent() {
    assertThatThrownBy(() -> new TypeGitBuilder(repo(), fixedEvent()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }

  @Test
  void testHashCode() {
    Ranges type = new TypeGitBuilder(repo(), introducedEvent()).build();
    Ranges otherType = new TypeGitBuilder(repo(), introducedEvent()).build();
    assertThat(type).hasSameHashCodeAs(otherType);
  }

  @Test
  void testToString() {
    Ranges type = new TypeGitBuilder(repo(), introducedEvent()).build();
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testType() {
    Ranges typeEcosystem = new TypeGitBuilder(repo(), introducedEvent()).build();
    RangeTypeValue type = typeEcosystem.type();
    assertThat(type).isEqualTo(RangeTypeValue.GIT);
  }

  @Test
  void testWitRepo() {
    Ranges typeEcosystem = new TypeGitBuilder(repo(), introducedEvent()).build();
    Optional<RepoValue> repo = typeEcosystem.repo();
    assertThat(repo).contains(RepoValue.fromString("https://osv.dev"));
  }

  @Test
  void testEvents() {
    Ranges typeEcosystem = new TypeGitBuilder(repo(), introducedEvent()).build();
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        GitEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0"));
  }

  @Test
  void testNonEquality() {
    Ranges type = new TypeGitBuilder(repo(), introducedEvent()).build();
    Ranges otherType = new TypeGitBuilder(repo(), introducedEvent(), fixedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isNotEqualTo(null);
      assertThat(t).isNotEqualTo(otherType);
    });
  }

  private static RepoValue repo() {
    return RepoValue.fromString("https://osv.dev");
  }

  private static GitEventValues introducedEvent() {
    return GitEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0");
  }

  private static GitEventValues fixedEvent() {
    return GitEventValues.of(EventSpecifierValue.FIXED, "1.0.1");
  }

  private static String typeToString() {
    return TYPE_KEY + ": " + "GIT";
  }

  private static String repoToString() {
    return REPO_KEY + ": " + "https://osv.dev";
  }

  private static String eventToString() {
    return EVENTS_KEY + ": " + "introduced, 1.0.0";
  }

}
