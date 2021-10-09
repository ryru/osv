package ch.addere.osv.domain.model.fields.affected;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Package identifies the affected code library or command provided by the package.
 */
public final class Package {

  public static final String PACKAGE_KEY = "package";

  private final Ecosystem ecosystem;
  private final Name name;
  private final Purl purl;

  private Package(Ecosystem ecosystem, Name name,
      Purl purl) {
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
  public static Package of(Ecosystem ecosystem, Name name) {
    return new Package(ecosystem, name, null);
  }

  /**
   * Create new Package with Purl.
   *
   * @param ecosystem ecosystem the package refers to
   * @param name      name the package refers to
   * @param purl      purl the package refers to
   * @return valid Package
   */
  public static Package of(Ecosystem ecosystem, Name name, Purl purl) {
    return new Package(ecosystem, name, purl);
  }

  public Ecosystem ecosystem() {
    return ecosystem;
  }

  public Name name() {
    return name;
  }

  public Optional<Purl> purl() {
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
    Package that = (Package) o;
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
