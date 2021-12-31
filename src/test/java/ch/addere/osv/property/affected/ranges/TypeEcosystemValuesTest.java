package ch.addere.osv.property.affected.ranges;

import static ch.addere.osv.property.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.property.affected.ranges.RepoValue.REPO_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.ranges.TypeEcosystemValues.TypeEcosystemBuilder;
import ch.addere.osv.property.affected.ranges.events.EcosystemEventValues;
import ch.addere.osv.property.affected.ranges.events.EventSpecifierValue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeEcosystemValuesTest {

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> new TypeEcosystemBuilder((EcosystemEventValues[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testMissingIntroducedEvent() {
    assertThatThrownBy(() -> new TypeEcosystemBuilder(fixedEvent()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("at least one 'introduced' event required");
  }

  @Test
  void testOfValueObjectCreation() {
    Ranges ranges1 = new TypeEcosystemBuilder(introducedEvent()).repo(repo()).build();
    Ranges ranges2 = new TypeEcosystemBuilder(
        ranges1.events().toArray(new EcosystemEventValues[0]))
        .repo(ranges1.repo().get())
        .build();
    assertThat(ranges1).isEqualTo(ranges2);
  }

  @Test
  void testType() {
    Ranges typeEcosystem = new TypeEcosystemBuilder(introducedEvent()).build();
    RangeTypeValue type = typeEcosystem.type();
    assertThat(type).isEqualTo(RangeTypeValue.ECOSYSTEM);
  }

  @Test
  void testEquality() {
    Ranges type = new TypeEcosystemBuilder(introducedEvent()).build();
    Ranges otherType = new TypeEcosystemBuilder(introducedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isEqualTo(type);
      assertThat(t).isEqualTo(otherType);
    });
  }

  @Test
  void testNonEquality() {
    Ranges type = new TypeEcosystemBuilder(introducedEvent()).build();
    Ranges otherType = new TypeEcosystemBuilder(introducedEvent(), fixedEvent()).build();
    assertThat(type).satisfies(t -> {
      assertThat(t).isNotEqualTo(null);
      assertThat(t).isNotEqualTo(otherType);
    });
  }

  @Test
  void testHashCode() {
    Ranges type = new TypeEcosystemBuilder(introducedEvent()).build();
    Ranges otherType = new TypeEcosystemBuilder(introducedEvent()).build();
    assertThat(type).hasSameHashCodeAs(otherType);
  }

  @Test
  void testWithRepoToString() {
    Ranges type = new TypeEcosystemBuilder(introducedEvent()).repo(repo()).build();
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  @Test
  void testWithoutRepoToString() {
    Ranges type = new TypeEcosystemBuilder(introducedEvent()).build();
    assertThat(type).hasToString(RANGES_KEY + ": " + join(", ",
        typeToString(),
        eventToString()));
  }

  @Test
  void testWitRepo() {
    Ranges typeEcosystem = new TypeEcosystemBuilder(introducedEvent()).repo(repo()).build();
    Optional<RepoValue> repo = typeEcosystem.repo();
    assertThat(repo).contains(RepoValue.fromString("https://osv.dev"));
  }

  @Test
  void testWithoutRepo() {
    Ranges typeEcosystem = new TypeEcosystemBuilder(introducedEvent()).build();
    Optional<RepoValue> repo = typeEcosystem.repo();
    assertThat(repo).isEmpty();
  }

  @Test
  void testEvents() {
    Ranges typeEcosystem = new TypeEcosystemBuilder(introducedEvent()).build();
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0"));
  }

  private static EcosystemEventValues introducedEvent() {
    return EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0");
  }

  private static EcosystemEventValues fixedEvent() {
    return EcosystemEventValues.of(EventSpecifierValue.FIXED, "1.0.1");
  }

  private static RepoValue repo() {
    return RepoValue.fromString("https://osv.dev");
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
