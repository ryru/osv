package ch.addere.osv.impl.fields.references;

import ch.addere.osv.fields.references.ReferenceUrl;
import java.net.URI;
import java.util.Objects;

/**
 * Reference URL property.
 */
public final class ReferenceUrlImpl implements ReferenceUrl {

  public static final String REFERENCE_URL_KEY = "url";

  private final URI referenceUrl;

  private ReferenceUrlImpl(URI referenceUrl) {
    this.referenceUrl = referenceUrl;
  }

  public static ReferenceUrlImpl of(URI url) {
    return new ReferenceUrlImpl(url);
  }

  @Override
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
    ReferenceUrlImpl that = (ReferenceUrlImpl) o;
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
