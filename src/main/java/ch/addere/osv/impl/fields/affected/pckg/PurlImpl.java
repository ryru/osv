package ch.addere.osv.impl.fields.affected.pckg;

import ch.addere.osv.fields.affected.pckg.Purl;
import java.util.Objects;

/**
 * Purl describes a package URL describing the package.
 */
public final class PurlImpl implements Purl {

  public static final String PURL_KEY = "purl";

  // TODO this should be a purl objeckt
  // following the spec: https://github.com/package-url/purl-spec
  private final String purl;

  private PurlImpl(String purl) {
    this.purl = purl;
  }

  public static PurlImpl of(String purl) {
    return new PurlImpl(purl);
  }

  @Override
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
    PurlImpl that = (PurlImpl) o;
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
