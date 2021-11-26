package ch.addere.osv.domain.model.fields;

import static java.lang.String.join;

import ch.addere.osv.domain.model.fields.reverences.ReferenceType;
import ch.addere.osv.domain.model.fields.reverences.ReferenceUrl;
import java.util.Objects;

/**
 * Reference field property.
 */
public final class References {

  public static final String REFERENCES_KEY = "references";

  private final ReferenceType referenceType;
  private final ReferenceUrl referenceUrl;

  private References(ReferenceType referenceType,
      ReferenceUrl referenceUrl) {
    this.referenceType = referenceType;
    this.referenceUrl = referenceUrl;
  }

  public static References of(ReferenceType referenceType, ReferenceUrl referenceUrl) {
    return new References(referenceType, referenceUrl);
  }

  public ReferenceType referenceType() {
    return referenceType;
  }

  public ReferenceUrl referenceUrl() {
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
    References that = (References) o;
    return referenceType == that.referenceType && Objects.equals(referenceUrl,
        that.referenceUrl);
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
