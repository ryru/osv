package ch.addere.osv.domain.model.fields.affected.ranges;

import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;

import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.GitEvent;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TypeGitTest {

  @Test
  void testType() {
    TypeGit typeEcosystem = TypeGit.of(repo(), introducedEvent());
    Type type = typeEcosystem.type();
    assertThat(type).isEqualTo(Type.GIT);
  }

  @Test
  void testWitRepo() {
    TypeGit typeEcosystem = TypeGit.of(repo(), introducedEvent());
    Optional<Repo> repo = typeEcosystem.repo();
    assertThat(repo).contains(Repo.of("https://osv.dev"));
  }

  @Test
  void testEvents() {
    TypeGit typeEcosystem = TypeGit.of(repo(), introducedEvent());
    List<? extends Event> events = typeEcosystem.events();
    assertThat(events.toArray()).containsExactly(
        GitEvent.of(EventSpecifier.introduced, "1.0.0"));
  }

  @Test
  void testSameness() {
    TypeGit type = TypeGit.of(repo(), introducedEvent());
    TypeGit otherType = type;
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testEquality() {
    TypeGit type = TypeGit.of(repo(), introducedEvent());
    TypeGit otherType = TypeGit.of(repo(), introducedEvent());
    assertThat(type).isEqualTo(otherType);
  }

  @Test
  void testNonEquality() {
    TypeGit type = TypeGit.of(repo(), introducedEvent());
    TypeGit otherType = TypeGit.of(repo(), fixedEvent());
    assertThat(type).isNotEqualTo(otherType);
  }

  @Test
  void testHashCode() {
    TypeGit type = TypeGit.of(repo(), introducedEvent());
    TypeGit otherType = TypeGit.of(repo(), introducedEvent());
    assertThat(type.hashCode()).isEqualTo(otherType.hashCode());
  }

  @Test
  void testToString() {
    TypeGit type = TypeGit.of(repo(), introducedEvent());
    assertThat(type.toString()).isEqualTo(RANGES_KEY + ": " + join(", ",
        typeToString(),
        repoToString(),
        eventToString()));
  }

  private static Repo repo() {
    return Repo.of("https://osv.dev");
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
