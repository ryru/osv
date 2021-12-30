package ch.addere.osv.property.affected.ranges;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.ranges.events.EcosystemEventValues;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Ecosystem type to be used in ranges.
 */
public final class TypeEcosystemValues implements Ranges {

  private final RangeTypeValue type;
  private final List<EcosystemEventValues> events;
  private RepoValue repo;

  private TypeEcosystemValues(List<EcosystemEventValues> events) {
    Objects.requireNonNull(events, "argument events must not be null");
    this.type = RangeTypeValue.ECOSYSTEM;
    this.events = events;
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
  public List<Event> events() {
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
    TypeEcosystemValues that = (TypeEcosystemValues) o;
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
    Stream<EcosystemEventValues> streamEcosystems = events.stream();
    Stream<Object> concat = Stream.concat(streamTypeRepo, streamEcosystems);

    return RANGES_KEY + ": " + concat
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * TypeEcosystemBuilder builder to create TypeEcosystemValues.
   */
  public static final class TypeEcosystemBuilder {

    private final List<EcosystemEventValues> events;
    private RepoValue repo = null;

    /**
     * Builder constructor.
     *
     * @param events EcosystemEventValues to add
     */
    public TypeEcosystemBuilder(EcosystemEventValues... events) {
      Objects.requireNonNull(events, "argument events must not be null");
      this.events = List.of(events);
    }

    /**
     * Add RepoValue to property.
     *
     * @param repo RepoValue to add
     * @return valid TypeSemVerBuilder
     */
    public TypeEcosystemBuilder repo(RepoValue repo) {
      this.repo = repo;
      return this;
    }

    /**
     * Build TypeEcosystemValues property.
     *
     * @return valid TypeEcosystemValue
     */
    public TypeEcosystemValues build() {
      TypeEcosystemValues typeEcosystemValues = new TypeEcosystemValues(events);
      typeEcosystemValues.repo = this.repo;
      return typeEcosystemValues;
    }
  }
}
