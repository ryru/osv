package ch.addere.osv.domain.model.fields.reverences;

import java.net.URI;
import java.util.Objects;

/**
 * Reference URL property.
 */
public final class ReferenceUrl {

  public static final String REFERENCE_URL_KEY = "url";

  private final URI referenceUrl;

  private ReferenceUrl(URI referenceUrl) {
    this.referenceUrl = referenceUrl;
  }

  public static ReferenceUrl of(URI url) {
    return new ReferenceUrl(url);
  }

  public String value() {
    return referenceUrl.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenceUrl that = (ReferenceUrl) o;
    return Objects.equals(referenceUrl, that.referenceUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referenceUrl);
  }

  @Override
  public String toString() {
    return REFERENCE_URL_KEY + ": " + referenceUrl;
  }
}
