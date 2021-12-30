package ch.addere.osv.property;

import static java.lang.String.join;

import ch.addere.osv.property.references.ReferenceTypeValue;
import ch.addere.osv.property.references.ReferenceUrlValue;
import java.util.Objects;

/**
 * Reference field property.
 */
public final class ReferencesValues {

  public static final String REFERENCES_KEY = "references";

  private final ReferenceTypeValue referenceType;
  private final ReferenceUrlValue referenceUrl;

  private ReferencesValues(
      ReferenceTypeValue referenceType,
      ReferenceUrlValue referenceUrl) {
    Objects.requireNonNull(referenceType, "argument referenceType must not be null");
    Objects.requireNonNull(referenceUrl, "argument referenceUrl must not be null");
    this.referenceType = referenceType;
    this.referenceUrl = referenceUrl;
  }

  public static ReferencesValues of(ReferenceTypeValue referenceType,
      ReferenceUrlValue referenceUrl) {
    return new ReferencesValues(referenceType, referenceUrl);
  }

  public ReferenceTypeValue referenceType() {
    return referenceType;
  }

  public ReferenceUrlValue referenceUrl() {
    return referenceUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferencesValues that = (ReferencesValues) o;
    return Objects.equals(referenceType, that.referenceType) && Objects.equals(
        referenceUrl, that.referenceUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referenceType, referenceUrl);
  }

  @Override
  public String toString() {
    return REFERENCES_KEY + ": " + join(", ", referenceType.toString(), referenceUrl.toString());
  }
}
