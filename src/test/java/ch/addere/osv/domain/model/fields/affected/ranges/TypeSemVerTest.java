package ch.addere.osv.domain.model.fields.affected.ranges;

import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.SemVerEvent;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeSemVerTest {

  @Test
  void testType() {
    TypeSemVer typeSemVer = TypeSemVer.of(introducedEvent());
    Type type = typeSemVer.type();
    assertThat(type).isEqualTo(Type.SEMVER);
  }

  @Test
  void testWitRepo() {
    TypeSemVer typeSemVer = TypeSemVer.of(repo(), introducedEvent());
    Optional<Repo> repo = typeSemVer.repo();
    assertThat(repo).contains(Repo.of("https://osv.dev"));
  }

  @Test
  void testWitoutRepo() {
    TypeSemVer typeSemVer = TypeSemVer.of(introducedEvent());
    Optional<Repo> repo = typeSemVer.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    TypeSemVer typeSemVer = TypeSemVer.of(introducedEvent());
    List<? extends Event> events = typeSemVer.events();
    assertThat(events.toArray()).containsExactly(
        SemVerEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  @Test
  void testSameness() {
    TypeSemVer type = TypeSemVer.of(introducedEvent());
    TypeSemVer otherType = type;
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testEquality() {
    TypeSemVer type = TypeSemVer.of(introducedEvent());
    TypeSemVer otherType = TypeSemVer.of(introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    TypeSemVer type = TypeSemVer.of(introducedEvent());
    TypeSemVer otherType = TypeSemVer.of(fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    TypeSemVer type = TypeSemVer.of(introducedEvent());
    TypeSemVer otherType = TypeSemVer.of(introducedEvent());
    assertThat(type.hashCode()).isEqualTo(otherType.hashCode());
  }

  @Test
  void testWithRepoToString() {
    TypeSemVer type = TypeSemVer.of(repo(), introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    TypeSemVer type = TypeSemVer.of(introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  private static Repo repo() {
    return Repo.of("https://osv.dev");
  }

  private static SemVerEvent introducedEvent() {
    return SemVerEvent.of(EventSpecifier.introduced, "1.0.0");
  }

  private static SemVerEvent fixedEvent() {
    return SemVerEvent.of(EventSpecifier.fixed, "1.0.1");
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
