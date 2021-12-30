package ch.addere.osv.property.references;

import ch.addere.osv.Value;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Reference URL property.
 */
public final class ReferenceUrlValue implements Value<URL> {

  public static final String REFERENCE_URL_KEY = "url";

  private final URL value;

  private ReferenceUrlValue(URL value) {
    Objects.requireNonNull(value, "argument value must not be null");
    this.value = value;
  }

  public static ReferenceUrlValue of(URL url) {
    return new ReferenceUrlValue(url);
  }

  /**
   * Create ReferenceUrlValue from a reference URL string.
   *
   * @param referenceUrl String of a URL pointing to a reference
   * @return valid ReferenceUrlValue
   */
  public static ReferenceUrlValue fromString(String referenceUrl) {
    try {
      return new ReferenceUrlValue(new URL(referenceUrl));
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("argument reference url is not a valid URL", e);

    }
  }

  @Override
  public URL value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenceUrlValue that = (ReferenceUrlValue) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return REFERENCE_URL_KEY + ": " + value;
  }
}
