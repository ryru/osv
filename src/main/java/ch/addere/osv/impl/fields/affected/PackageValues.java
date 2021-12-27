package ch.addere.osv.impl.fields.affected;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Package identifies the affected code library or command provided by the package.
 */
public final class PackageValues {

  public static final String PACKAGE_KEY = "package";

  private final EcosystemValue ecosystem;
  private final NameValue name;
  private PurlValue purl;

  private PackageValues(EcosystemValue ecosystem, NameValue name) {
    this.ecosystem = ecosystem;
    this.name = name;
  }

  public EcosystemValue ecosystem() {
    return ecosystem;
  }

  public NameValue name() {
    return name;
  }

  public Optional<PurlValue> purl() {
    return Optional.ofNullable(purl);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackageValues that = (PackageValues) o;
    return Objects.equals(ecosystem, that.ecosystem) && Objects.equals(name,
        that.name) && Objects.equals(purl, that.purl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ecosystem, name, purl);
  }

  @Override
  public String toString() {
    return PACKAGE_KEY + ": " + Stream.of(ecosystem, name, purl)
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * Builder for Package.
   */
  public static class PackageValueBuilder {

    private final EcosystemValue ecosystem;
    private final NameValue name;
    private PurlValue purl = null;

    /**
     * PackageValues builder.
     *
     * @param ecosystem EcosystemValue to use
     * @param name      NameValue to use
     */
    public PackageValueBuilder(EcosystemValue ecosystem, NameValue name) {
      Objects.requireNonNull(ecosystem, "argument ecosystem must not be null");
      Objects.requireNonNull(name, "argument name must not be null");
      this.ecosystem = ecosystem;
      this.name = name;
    }

    /**
     * PurlValue which is optional to a valid PackageValues property.
     *
     * @param purl PurlValue to use
     * @return builder object
     */
    public PackageValueBuilder purl(PurlValue purl) {
      this.purl = purl;
      return this;
    }

    /**
     * PackageValue from builder.
     *
     * @return valid PackageValue
     */
    public PackageValues build() {
      PackageValues pckg = new PackageValues(ecosystem, name);
      pckg.purl = purl;
      return pckg;
    }
  }
}
