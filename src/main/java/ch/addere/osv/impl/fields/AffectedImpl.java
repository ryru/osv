package ch.addere.osv.impl.fields;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.affected.DatabaseSpecific;
import ch.addere.osv.fields.affected.EcosystemSpecific;
import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.Versions;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Affected describe the package versions that are affected by this vulnerability.
 */
public final class AffectedImpl implements Affected {

  public static final String AFFECTED_KEY = "affected";

  private final Package pckg;
  private final List<Ranges> ranges;
  private final Versions versions;
  private final EcosystemSpecific ecosystemSpecific;
  private final DatabaseSpecific databaseSpecific;

  // TODO constrain: In short, each object in the affected array must contain either a non-empty
  //  versions list or at least one range in the ranges list of type SEMVER.

  private AffectedImpl(Package pckge,
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

  @Override
  public Package pckg() {
    return pckg;
  }

  @Override
  public List<Ranges> ranges() {
    return List.copyOf(ranges);
  }

  @Override
  public Optional<Versions> versions() {
    return Optional.ofNullable(versions);
  }

  @Override
  public Optional<EcosystemSpecific> ecosystemSpecific() {
    return Optional.ofNullable(ecosystemSpecific);
  }

  @Override
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
    AffectedImpl affected = (AffectedImpl) o;
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

    private final Package pckg;
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
    public AffectedImpl build() {
      return new AffectedImpl(pckg, ranges, versions, ecosystemSpecific, databaseSpecific);
    }
  }
}
