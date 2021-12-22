package ch.addere.osv.impl.fields.affected;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Package identifies the affected code library or command provided by the package.
 */
public final class PackageImpl implements Package {

  public static final String PACKAGE_KEY = "package";

  private final EcosystemValue ecosystem;
  private final NameValue name;
  private final PurlValue purl;

  private PackageImpl(EcosystemValue ecosystem, NameValue name, PurlValue purl) {
    Objects.requireNonNull(ecosystem, "argument ecosystem must not be null");
    Objects.requireNonNull(name, "argument name must not be null");
    this.ecosystem = ecosystem;
    this.name = name;
    this.purl = purl;
  }

  /**
   * Create new Package without Purl.
   *
   * @param ecosystem ecosystem the package revers to
   * @param name      name the package revers to
   * @return valid Package
   */
  public static PackageImpl of(EcosystemValue ecosystem, NameValue name) {
    return new PackageImpl(ecosystem, name, null);
  }

  /**
   * Create new Package with Purl.
   *
   * @param ecosystem ecosystem the package refers to
   * @param name      name the package refers to
   * @param purl      purl the package refers to
   * @return valid Package
   */
  public static PackageImpl of(EcosystemValue ecosystem, NameValue name, PurlValue purl) {
    return new PackageImpl(ecosystem, name, purl);
  }

  @Override
  public EcosystemValue ecosystem() {
    return ecosystem;
  }

  @Override
  public NameValue name() {
    return name;
  }

  @Override
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
    PackageImpl that = (PackageImpl) o;
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
}
