package ch.addere.osv.impl.fields.affected.pckg;

import static java.lang.String.format;

import ch.addere.osv.fields.affected.pckg.Purl;
import com.github.packageurl.MalformedPackageURLException;
import com.github.packageurl.PackageURL;
import java.util.Objects;

/**
 * Purl describes a package URL describing the package.
 */
public final class PurlImpl implements Purl {

  public static final String PURL_KEY = "purl";

  private final PackageURL purl;

  private PurlImpl(String purl) {
    try {
      this.purl = new PackageURL(purl);
    } catch (MalformedPackageURLException e) {
      throw new IllegalArgumentException(format("invalid purl: '%s'", purl), e);
    }
  }

  public static PurlImpl of(String purl) {
    return new PurlImpl(purl);
  }

  @Override
  public String value() {
    return purl.toString();
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
