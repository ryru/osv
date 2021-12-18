package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeImpl.TYPE_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.REPO_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.of;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.RangeType;
import ch.addere.osv.fields.affected.ranges.Repo;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeSemVerImplTest {


  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> TypeSemVerImpl.of(repo(), (SemVerEvent[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Ranges ranges1 = TypeSemVerImpl.of(repo(), introducedEvent());
    Ranges ranges2 = TypeSemVerImpl.of(
        ranges1.repo().get(),
        ranges1.events().toArray(new SemVerEvent[0]));
    assertThat(ranges1).isEqualTo(ranges2);
  }

  @Test
  void testType() {
    Ranges typeSemVer = TypeSemVerImpl.of(introducedEvent());
    RangeType type = typeSemVer.type();
    assertThat(type).isEqualTo(RangeTypeImpl.SEMVER);
  }

  @Test
  void testWitRepo() {
    Ranges typeSemVer = TypeSemVerImpl.of(repo(), introducedEvent());
    Optional<Repo> repo = typeSemVer.repo();
    assertThat(repo).contains(of("https://osv.dev"));
  }

  @Test
  void testWithoutRepo() {
    Ranges typeSemVer = TypeSemVerImpl.of(introducedEvent());
    Optional<Repo> repo = typeSemVer.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    Ranges typeSemVer = TypeSemVerImpl.of(introducedEvent());
    List<? extends Event> events = typeSemVer.events();
    assertThat(events.toArray()).containsExactly(
        SemVerEvent.of(EventSpecifierImpl.INTRODUCED, "1.0.0"));
  }

  @Test
  void testEquality() {
    Ranges type = TypeSemVerImpl.of(introducedEvent());
    Ranges otherType = TypeSemVerImpl.of(introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    Ranges type = TypeSemVerImpl.of(introducedEvent());
    Ranges otherType = TypeSemVerImpl.of(fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    Ranges type = TypeSemVerImpl.of(introducedEvent());
    Ranges otherType = TypeSemVerImpl.of(introducedEvent());
    assertThat(type).hasSameHashCodeAs(otherType);
  }

  @Test
  void testWithRepoToString() {
    Ranges type = TypeSemVerImpl.of(repo(), introducedEvent());
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    Ranges type = TypeSemVerImpl.of(introducedEvent());
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  private static Repo repo() {
    return of("https://osv.dev");
  }

  private static SemVerEvent introducedEvent() {
    return SemVerEvent.of(EventSpecifierImpl.INTRODUCED, "1.0.0");
  }

  private static SemVerEvent fixedEvent() {
    return SemVerEvent.of(EventSpecifierImpl.FIXED, "1.0.1");
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
