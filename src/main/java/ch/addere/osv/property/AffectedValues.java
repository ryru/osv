package ch.addere.osv.property;

import static ch.addere.osv.EntrySchemaVersion.V_1_0_0;
import static ch.addere.osv.EntrySchemaVersion.latest;
import static ch.addere.osv.property.affected.VersionsValue.VERSIONS_KEY;
import static ch.addere.osv.property.affected.ranges.RangeTypeValue.SEMVER;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

import ch.addere.osv.EntrySchemaVersion;
import ch.addere.osv.property.affected.DatabaseSpecificValue;
import ch.addere.osv.property.affected.EcosystemSpecificValue;
import ch.addere.osv.property.affected.PackageValues;
import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.VersionsValue;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Affected describe the package versions that are affected by this vulnerability.
 */
public final class AffectedValues {

  public static final String AFFECTED_KEY = "affected";

  private final PackageValues pckg;
  private final List<Ranges> ranges;
  private final VersionsValue versions;
  private final EcosystemSpecificValue ecosystemSpecific;
  private final DatabaseSpecificValue databaseSpecificValue;

  private AffectedValues(PackageValues pckg,
      List<Ranges> ranges,
      VersionsValue versions,
      EcosystemSpecificValue ecosystemSpecific,
      DatabaseSpecificValue databaseSpecificValue) {
    this.pckg = pckg;
    this.ranges = ranges;
    this.versions = versions;
    this.ecosystemSpecific = ecosystemSpecific;
    this.databaseSpecificValue = databaseSpecificValue;
  }

  public PackageValues pckg() {
    return pckg;
  }

  public List<Ranges> ranges() {
    return List.copyOf(ranges);
  }

  public Optional<VersionsValue> versions() {
    return Optional.ofNullable(versions);
  }

  public Optional<EcosystemSpecificValue> ecosystemSpecific() {
    return Optional.ofNullable(ecosystemSpecific);
  }

  public Optional<DatabaseSpecificValue> databaseSpecific() {
    return Optional.ofNullable(databaseSpecificValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AffectedValues affected = (AffectedValues) o;
    return Objects.equals(pckg, affected.pckg) && Objects.equals(ranges,
        affected.ranges) && Objects.equals(versions, affected.versions)
        && Objects.equals(ecosystemSpecific, affected.ecosystemSpecific)
        && Objects.equals(databaseSpecificValue, affected.databaseSpecificValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pckg, ranges, versions, ecosystemSpecific, databaseSpecificValue);
  }

  @Override
  public String toString() {
    Stream<PackageValues> packageStream = Stream.of(pckg);
    Stream<Ranges> rangesStream = ranges.stream().filter(Objects::nonNull);
    Stream<Object> objectStream = Stream.of(versions, ecosystemSpecific, databaseSpecificValue);
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
  public static final class AffectedValuesBuilder {

    private EntrySchemaVersion entrySchemaVersion;
    private final PackageValues pckg;
    private List<Ranges> ranges = List.of();
    private VersionsValue versions = null;
    private EcosystemSpecificValue ecosystemSpecific = null;
    private DatabaseSpecificValue databaseSpecificValue = null;

    /**
     * Affected builder.
     *
     * @param pckg valid Package
     */
    public AffectedValuesBuilder(PackageValues pckg) {
      Objects.requireNonNull(pckg, "argument package must not be null");
      this.entrySchemaVersion = latest();
      this.pckg = pckg;
    }

    public AffectedValuesBuilder entrySchemaVersion(EntrySchemaVersion entrySchemaVersion) {
      this.entrySchemaVersion = entrySchemaVersion;
      return this;
    }

    /**
     * Add multiple Ranges to Affected builder.
     *
     * @param ranges valid Ranges
     * @return Affected builder
     */
    public AffectedValuesBuilder ranges(Ranges... ranges) {
      Objects.requireNonNull(ranges, "argument ranges must not be null");
      this.ranges = List.of(ranges);
      return this;
    }

    /**
     * Add Versions to Affected builder.
     *
     * @param versions valid Versions
     * @return Affected builder
     */
    public AffectedValuesBuilder versions(VersionsValue versions) {
      Objects.requireNonNull(versions, "argument versions must not be null");
      this.versions = versions;
      return this;
    }

    /**
     * Add EcosystemSpecific to Affected builder.
     *
     * @param ecosystemSpecific valid EcosystemSpecific
     * @return Affected builder
     */
    public AffectedValuesBuilder ecosystemSpecific(EcosystemSpecificValue ecosystemSpecific) {
      Objects.requireNonNull(ecosystemSpecific, "argument ecosystem specific must not be null");
      this.ecosystemSpecific = ecosystemSpecific;
      return this;
    }

    /**
     * Add DatabaseSpecific to Affected builder.
     *
     * @param databaseSpecificValue valid DatabaseSpecific
     * @return Affected builder
     */
    public AffectedValuesBuilder databaseSpecific(DatabaseSpecificValue databaseSpecificValue) {
      Objects.requireNonNull(databaseSpecificValue, "argument database specific must not be null");
      this.databaseSpecificValue = databaseSpecificValue;
      return this;
    }

    /**
     * Build a concrete Affected property.
     *
     * @return valid AffectedValue
     */
    public AffectedValues build() {
      isValidAffectedValue();
      return new AffectedValues(pckg, ranges, versions, ecosystemSpecific, databaseSpecificValue);
    }

    private void isValidAffectedValue() {
      if (entrySchemaVersion == V_1_0_0) {
        validate(hasVersionProperty, this, format("%s is required", VERSIONS_KEY));
        validate(hasRangeOfSemVerType, this, "range must have one SemVer type");
      }
    }

    private final Predicate<AffectedValuesBuilder> hasVersionProperty = affected ->
        affected.versions != null && affected.versions.value().stream().findAny().isPresent();

    private final Predicate<AffectedValuesBuilder> hasRangeOfSemVerType = affected ->
        affected.ranges.stream().anyMatch(range -> range.type() == SEMVER);

    private void validate(
        Predicate<AffectedValuesBuilder> predicate,
        AffectedValuesBuilder builder,
        String failMsg) {
      if (!predicate.test(builder)) {
        throw new IllegalStateException(
            format("validation for schema version %s failed: %s",
                entrySchemaVersion.value(),
                failMsg));
      }
    }
  }
}
