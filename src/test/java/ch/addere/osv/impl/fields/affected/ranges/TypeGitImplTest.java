package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.REPO_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.Repo;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEvent;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeGitImplTest {

  @Test
  void testType() {
    Ranges typeEcosystem = TypeGitImpl.of(repo(), introducedEvent());
    Type type = typeEcosystem.type();
    assertThat(type).isEqualTo(Type.GIT);
  }

  @Test
  void testWitRepo() {
    Ranges typeEcosystem = TypeGitImpl.of(repo(), introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).contains(RepoImpl.of("https://osv.dev"));
  }

  @Test
  void testEvents() {
    Ranges typeEcosystem = TypeGitImpl.of(repo(), introducedEvent());
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        GitEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  @Test
  void testSameness() {
    Ranges type = TypeGitImpl.of(repo(), introducedEvent());
    Ranges otherType = type;
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testEquality() {
    Ranges type = TypeGitImpl.of(repo(), introducedEvent());
    Ranges otherType = TypeGitImpl.of(repo(), introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    Ranges type = TypeGitImpl.of(repo(), introducedEvent());
    Ranges otherType = TypeGitImpl.of(repo(), fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    Ranges type = TypeGitImpl.of(repo(), introducedEvent());
    Ranges otherType = TypeGitImpl.of(repo(), introducedEvent());
    assertThat(type.hashCode()).isEqualTo(otherType.hashCode());
  }

  @Test
  void testToString() {
    Ranges type = TypeGitImpl.of(repo(), introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  private static Repo repo() {
    return RepoImpl.of("https://osv.dev");
  }

  private static GitEvent introducedEvent() {
    return GitEvent.of(EventSpecifier.introduced, "1.0.0");
  }

  private static GitEvent fixedEvent() {
    return GitEvent.of(EventSpecifier.fixed, "1.0.1");
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
