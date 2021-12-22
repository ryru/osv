package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoValue.REPO_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierValue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeEcosystemTest {


  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> TypeEcosystemImpl.of(repo(), (EcosystemEvent[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Ranges ranges1 = TypeEcosystemImpl.of(repo(), introducedEvent());
    Ranges ranges2 = TypeEcosystemImpl.of(
        ranges1.repo().get(),
        ranges1.events().toArray(new EcosystemEvent[0]));
    assertThat(ranges1).isEqualTo(ranges2);
  }

  private static RepoValue repo() {
    return RepoValue.fromString("https://osv.dev");
  }

  private static EcosystemEvent introducedEvent() {
    return EcosystemEvent.of(EventSpecifierValue.INTRODUCED, "1.0.0");
  }

  private static EcosystemEvent fixedEvent() {
    return EcosystemEvent.of(EventSpecifierValue.FIXED, "1.0.1");
  }

  @Test
  void testType() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    RangeTypeValue type = typeEcosystem.type();
    assertThat(type).isEqualTo(RangeTypeValue.ECOSYSTEM);
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
    assertThat(type).hasSameHashCodeAs(otherType);
  }

  @Test
  void testWithRepoToString() {
    Ranges type = TypeEcosystemImpl.of(repo(), introducedEvent());
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    Ranges type = TypeEcosystemImpl.of(introducedEvent());
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  @Test
  void testWitRepo() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(repo(), introducedEvent());
    Optional<RepoValue> repo = typeEcosystem.repo();
    assertThat(repo).contains(RepoValue.fromString("https://osv.dev"));
  }

  @Test
  void testWithoutRepo() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    Optional<RepoValue> repo = typeEcosystem.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    Ranges typeEcosystem = TypeEcosystemImpl.of(introducedEvent());
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        EcosystemEvent.of(EventSpecifierValue.INTRODUCED, "1.0.0"));
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
