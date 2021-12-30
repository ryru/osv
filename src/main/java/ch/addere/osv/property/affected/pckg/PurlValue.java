package ch.addere.osv.property.affected.pckg;

import static java.lang.String.format;

import ch.addere.osv.Value;
import com.github.packageurl.MalformedPackageURLException;
import com.github.packageurl.PackageURL;
import java.util.Objects;

/**
 * Purl describes a package URL describing the package.
 */
public final class PurlValue implements Value<String> {

  public static final String PURL_KEY = "purl";

  private final PackageURL value;

  private PurlValue(String value) {
    Objects.requireNonNull(value, "argument purl must not be null");
    try {
      this.value = new PackageURL(value);
    } catch (MalformedPackageURLException e) {
      throw new IllegalArgumentException(format("invalid purl: '%s'", value), e);
    }
  }

  public static PurlValue fromString(String purl) {
    return new PurlValue(purl);
  }

  @Override
  public String value() {
    return value.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PurlValue that = (PurlValue) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return PURL_KEY + ": " + value;
  }
}
