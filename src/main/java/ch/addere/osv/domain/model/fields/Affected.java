package ch.addere.osv.domain.model.fields;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

import ch.addere.osv.domain.model.fields.affected.DatabaseSpecific;
import ch.addere.osv.domain.model.fields.affected.EcosystemSpecific;
import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.Versions;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Affected describe the package versions that are affected by this vulnerability.
 */
public final class Affected {

  public static final String AFFECTED_KEY = "affected";

  private final ch.addere.osv.domain.model.fields.affected.Package pckg;
  private final List<Ranges> ranges;
  private final Versions versions;
  private final EcosystemSpecific ecosystemSpecific;
  private final DatabaseSpecific databaseSpecific;

  // TODO constrain: In short, each object in the affected array must contain either a non-empty
  //  versions list or at least one range in the ranges list of type SEMVER.

  private Affected(Package pckge,
      List<Ranges> ranges,
      Versions versions,
      EcosystemSpecific ecosystemSpecific,
      DatabaseSpecific databaseSpecific) {
    this.pckg = pckge;
    this.ranges = ranges;
    this.versions = versions;
    this.ecosystemSpecific = ecosystemSpecific;
    this.databaseSpecific = databaseSpecific;
  }

  public Package pckge() {
    return pckg;
  }

  public List<Ranges> ranges() {
    return List.copyOf(ranges);
  }

  public Optional<Versions> versions() {
    return Optional.ofNullable(versions);
  }

  public Optional<EcosystemSpecific> ecosystemSpecific() {
    return Optional.ofNullable(ecosystemSpecific);
  }

  public Optional<DatabaseSpecific> databaseSpecific() {
    return Optional.ofNullable(databaseSpecific);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Affected affected = (Affected) o;
    return Objects.equals(pckg, affected.pckg) && Objects.equals(ranges,
        affected.ranges) && Objects.equals(versions, affected.versions)
        && Objects.equals(ecosystemSpecific, affected.ecosystemSpecific)
        && Objects.equals(databaseSpecific, affected.databaseSpecific);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pckg, ranges, versions, ecosystemSpecific, databaseSpecific);
  }

  @Override
  public String toString() {
    Stream<Package> packageStream = Stream.of(pckg);
    Stream<Ranges> rangesStream = ranges.stream().filter(Objects::nonNull);
    Stream<Object> objectStream = Stream.of(versions, ecosystemSpecific, databaseSpecific);
    Stream<Object> concat = concat(packageStream, concat(rangesStream, objectStream));

    return AFFECTED_KEY + ": " + concat
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * Builder for Affected.
   */
  public static class AffectedBuilder {

    private final ch.addere.osv.domain.model.fields.affected.Package pckg;
    private List<Ranges> ranges = List.of();
    private Versions versions = null;
    private EcosystemSpecific ecosystemSpecific = null;
    private DatabaseSpecific databaseSpecific = null;

    public AffectedBuilder(Package pckg) {
      this.pckg = pckg;
    }

    public AffectedBuilder ranges(Ranges... ranges) {
      this.ranges = List.of(ranges);
      return this;
    }

    public AffectedBuilder versions(Versions versions) {
      this.versions = versions;
      return this;
    }

    public AffectedBuilder ecosystemSpecific(EcosystemSpecific ecosystemSpecific) {
      this.ecosystemSpecific = ecosystemSpecific;
      return this;
    }

    public AffectedBuilder databaseSpecific(DatabaseSpecific databaseSpecific) {
      this.databaseSpecific = databaseSpecific;
      return this;
    }

    /**
     * Build a concrete Affected property.
     *
     * @return valid Affected
     */
    public Affected build() {
      return new Affected(pckg, ranges, versions, ecosystemSpecific, databaseSpecific);
    }
  }
}
