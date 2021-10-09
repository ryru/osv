package ch.addere.osv.domain.model.fields.affected.ranges;

import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.domain.model.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeEcosystemTest {

  @Test
  void testType() {
    TypeEcosystem typeEcosystem = TypeEcosystem.of(introducedEvent());
    Type type = typeEcosystem.type();
    assertThat(type).isEqualTo(Type.ECOSYSTEM);
  }

  @Test
  void testWitRepo() {
    TypeEcosystem typeEcosystem = TypeEcosystem.of(repo(), introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).contains(Repo.of("https://osv.dev"));
  }

  @Test
  void testWitoutRepo() {
    TypeEcosystem typeEcosystem = TypeEcosystem.of(introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    TypeEcosystem typeEcosystem = TypeEcosystem.of(introducedEvent());
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        EcosystemEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  @Test
  void testSameness() {
    TypeEcosystem type = TypeEcosystem.of(introducedEvent());
    TypeEcosystem otherType = type;
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testEquality() {
    TypeEcosystem type = TypeEcosystem.of(introducedEvent());
    TypeEcosystem otherType = TypeEcosystem.of(introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    TypeEcosystem type = TypeEcosystem.of(introducedEvent());
    TypeEcosystem otherType = TypeEcosystem.of(fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    TypeEcosystem type = TypeEcosystem.of(introducedEvent());
    TypeEcosystem otherType = TypeEcosystem.of(introducedEvent());
    assertThat(type.hashCode()).isEqualTo(otherType.hashCode());
  }

  @Test
  void testWithRepoToString() {
    TypeEcosystem type = TypeEcosystem.of(repo(), introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    TypeEcosystem type = TypeEcosystem.of(introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  private static Repo repo() {
    return Repo.of("https://osv.dev");
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
