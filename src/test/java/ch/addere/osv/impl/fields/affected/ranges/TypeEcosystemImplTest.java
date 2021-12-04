package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.REPO_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.of;
import static ch.addere.osv.impl.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.Repo;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifier;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeEcosystemImplTest {


  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> TypeEcosystemImpl.of(repo(), (EcosystemEvent[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testType() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    Type type = typeEcosystem.type();
    assertThat(type).isEqualTo(Type.ECOSYSTEM);
  }

  @Test
  void testWitRepo() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(repo(), introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).contains(of("https://osv.dev"));
  }

  @Test
  void testWitoutRepo() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        EcosystemEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  @Test
  void testSameness() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    Ranges otherType = type;
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testEquality() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    Ranges otherType = TypeEcosystemImpl.of(introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    Ranges otherType = TypeEcosystemImpl.of(fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    Ranges otherType = TypeEcosystemImpl.of(introducedEvent());
    assertThat(type.hashCode()).isEqualTo(otherType.hashCode());
  }

  @Test
  void testWithRepoToString() {
    Ranges type = TypeEcosystemImpl.of(repo(), introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  private static Repo repo() {
    return of("https://osv.dev");
  }

  private static EcosystemEvent introducedEvent() {
    return EcosystemEvent.of(EventSpecifier.introduced, "1.0.0");
  }

  private static EcosystemEvent fixedEvent() {
    return EcosystemEvent.of(EventSpecifier.fixed, "1.0.1");
  }

  private static String typeToString() {
    return TYPE_KEY + ": " + "ECOSYSTEM";
  }

  private static String repoToString() {
    return REPO_KEY + ": " + "https://osv.dev";
  }

  private static String eventToString() {
    return EVENTS_KEY + ": " + "introduced, 1.0.0";
  }
}
