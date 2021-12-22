package ch.addere.osv.impl.fields.affected.ranges;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Semantic version type to be used in ranges.
 */
public final class TypeSemVerImpl implements Ranges {

  private final RangeTypeValue type;
  private final RepoValue repo;
  private final List<SemVerEvent> events;

  private TypeSemVerImpl(RepoValue repo, SemVerEvent... events) {
    Objects.requireNonNull(events, "argument events must not be null");
    this.type = RangeTypeValue.SEMVER;
    this.repo = repo;
    this.events = List.of(events);
  }

  public static TypeSemVerImpl of(SemVerEvent... events) {
    return new TypeSemVerImpl(null, events);
  }

  public static TypeSemVerImpl of(RepoValue repo, SemVerEvent... events) {
    return new TypeSemVerImpl(repo, events);
  }

  @Override
  public RangeTypeValue type() {
    return type;
  }

  @Override
  public Optional<RepoValue> repo() {
    return Optional.ofNullable(repo);
  }

  @Override
  public List<? extends Event> events() {
    return new LinkedList<>(events);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypeSemVerImpl that = (TypeSemVerImpl) o;
    return type.equals(that.type) && Objects.equals(repo, that.repo) && Objects.equals(
        events, that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, repo, events);
  }

  @Override
  public String toString() {
    Stream<Object> streamTypeRepo = Stream.of(type, repo);
    Stream<SemVerEvent> streamSemVers = events.stream();
    Stream<Object> concat = Stream.concat(streamTypeRepo, streamSemVers);

    return RANGES_KEY + ": " + concat
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }
}
