package ch.addere.osv.domain.model.fields.affected.pckg;

import java.util.Objects;

/**
 * Purl describes a package URL describing the package.
 */
public final class Purl {

  public static final String PURL_KEY = "purl";

  // TODO this should be a purl objeckt
  // following the spec: https://github.com/package-url/purl-spec
  private final String purl;

  private Purl(String purl) {
    this.purl = purl;
  }

  public static Purl of(String purl) {
    return new Purl(purl);
  }

  public String value() {
    return purl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Purl that = (Purl) o;
    return Objects.equals(purl, that.purl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purl);
  }

  @Override
  public String toString() {
    return PURL_KEY + ": " + purl;
  }
}
