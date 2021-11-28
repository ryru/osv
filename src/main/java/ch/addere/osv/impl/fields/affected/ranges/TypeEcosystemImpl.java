package ch.addere.osv.impl.fields.affected.ranges;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.Repo;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Ecosystem type to be used in ranges.
 */
public final class TypeEcosystemImpl implements Ranges {

  private final Type type;
  private final Repo repo;
  private final List<EcosystemEvent> events;

  private TypeEcosystemImpl(Repo repo, EcosystemEvent... events) {
    this.type = Type.ECOSYSTEM;
    this.repo = repo;
    this.events = List.of(events);
  }

  public static TypeEcosystemImpl of(EcosystemEvent... events) {
    return new TypeEcosystemImpl(null, events);
  }

  public static TypeEcosystemImpl of(Repo repo, EcosystemEvent... events) {
    return new TypeEcosystemImpl(repo, events);
  }

  @Override
  public Type type() {
    return type;
  }

  @Override
  public Optional<Repo> repo() {
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
    TypeEcosystemImpl that = (TypeEcosystemImpl) o;
    return type == that.type && Objects.equals(repo, that.repo) && Objects.equals(
        events, that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, repo, events);
  }

  @Override
  public String toString() {
    Stream<Object> streamTypeRepo = Stream.of(type, repo);
    Stream<EcosystemEvent> streamEcosystems = events.stream();
    Stream<Object> concat = Stream.concat(streamTypeRepo, streamEcosystems);

    return RANGES_KEY + ": " + concat
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }
}
