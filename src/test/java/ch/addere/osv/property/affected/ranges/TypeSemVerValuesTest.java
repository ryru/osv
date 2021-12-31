package ch.addere.osv.property.affected.ranges;

import static ch.addere.osv.property.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.property.affected.ranges.RepoValue.REPO_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.ranges.TypeSemVerValues.TypeSemVerBuilder;
import ch.addere.osv.property.affected.ranges.events.EventSpecifierValue;
import ch.addere.osv.property.affected.ranges.events.SemVerEventValues;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeSemVerValuesTest {

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> new TypeSemVerBuilder((SemVerEventValues[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testMissingIntroducedEvent() {
    assertThatThrownBy(() -> new TypeSemVerBuilder(fixedEvent()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }

  @Test
  void testOfValueObjectCreation() {
    Ranges ranges1 = new TypeSemVerBuilder(introducedEvent()).repo(repo()).build();
    Ranges ranges2 = new TypeSemVerBuilder(ranges1.events().toArray(new SemVerEventValues[0]))
        .repo(ranges1.repo().get())
        .build();
    assertThat(ranges1).isEqualTo(ranges2);
  }

  @Test
  void testType() {
    Ranges typeSemVer = new TypeSemVerBuilder(introducedEvent()).build();
    RangeTypeValue type = typeSemVer.type();
    assertThat(type).isEqualTo(RangeTypeValue.SEMVER);
  }

  @Test
  void testEquality() {
    Ranges type = new TypeSemVerBuilder(introducedEvent()).build();
    Ranges otherType = new TypeSemVerBuilder(introducedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isEqualTo(type);
      assertThat(t).isEqualTo(otherType);
    });
  }

  @Test
  void testHashCode() {
    Ranges type = new TypeSemVerBuilder(introducedEvent()).build();
    Ranges otherType = new TypeSemVerBuilder(introducedEvent()).build();
    assertThat(type).hasSameHashCodeAs(otherType);
  }

  @Test
  void testWithRepoToString() {
    Ranges type = new TypeSemVerBuilder(introducedEvent()).repo(repo()).build();
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    Ranges type = new TypeSemVerBuilder(introducedEvent()).build();
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  @Test
  void testWitRepo() {
    Ranges typeSemVer = new TypeSemVerBuilder(introducedEvent()).repo(repo()).build();
    Optional<RepoValue> repo = typeSemVer.repo();
    assertThat(repo).contains(RepoValue.fromString("https://osv.dev"));
  }

  @Test
  void testWithoutRepo() {
    Ranges typeSemVer = new TypeSemVerBuilder(introducedEvent()).build();
    Optional<RepoValue> repo = typeSemVer.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    Ranges typeSemVer = new TypeSemVerBuilder(introducedEvent()).build();
    List<? extends Event> events = typeSemVer.events();
    assertThat(events.toArray()).containsExactly(
        SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0"));
  }

  @Test
  void testNonEquality() {
    Ranges type = new TypeSemVerBuilder(introducedEvent()).build();
    Ranges otherType = new TypeSemVerBuilder(introducedEvent(), fixedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isNotEqualTo(null);
      assertThat(t).isNotEqualTo(otherType);
    });
  }

  private static SemVerEventValues introducedEvent() {
    return SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0");
  }

  private static RepoValue repo() {
    return RepoValue.fromString("https://osv.dev");
  }

  private static SemVerEventValues fixedEvent() {
    return SemVerEventValues.of(EventSpecifierValue.FIXED, "1.0.1");
  }

  private static String typeToString() {
    return TYPE_KEY + ": " + "SEMVER";
  }

  private static String repoToString() {
    return REPO_KEY + ": " + "https://osv.dev";
  }

  private static String eventToString() {
    return EVENTS_KEY + ": " + "introduced, 1.0.0";
  }

}
