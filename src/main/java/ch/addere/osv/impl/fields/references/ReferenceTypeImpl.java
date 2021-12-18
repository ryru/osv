package ch.addere.osv.impl.fields.references;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.references.ReferenceType;

/**
 * Reference type property.
 */
public enum ReferenceTypeImpl implements ReferenceType {

  ADVISORY("ADVISORY"),
  ARTICLE("ARTICLE"),
  FIX("FIX"),
  PACKAGE("PACKAGE"),
  REPORT("REPORT"),
  WEB("WEB");

  public static final String REFERENCE_TYPE_KEY = "type";

  private final String referenceType;

  ReferenceTypeImpl(String referenceType) {
    this.referenceType = referenceType;
  }

  @Override
  public String value() {
    return this.referenceType;
  }

  /**
   * Get ReferenceType from reference type string.
   *
   * @param referenceType string value of an reference type
   * @return              valid ReferenceType
   */
  public static ReferenceType of(String referenceType) {
    return stream(ReferenceTypeImpl.values())
        .filter(values -> values.value().equals(referenceType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid reference type", referenceType)));
  }

  @Override
  public String toString() {
    return REFERENCE_TYPE_KEY + ": " + referenceType;
  }
}
