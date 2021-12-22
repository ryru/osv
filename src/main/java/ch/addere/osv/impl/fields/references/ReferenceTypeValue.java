package ch.addere.osv.impl.fields.references;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.Value;

/**
 * Reference type property.
 */
public enum ReferenceTypeValue implements Value<String> {

  ADVISORY("ADVISORY"),
  ARTICLE("ARTICLE"),
  FIX("FIX"),
  PACKAGE("PACKAGE"),
  REPORT("REPORT"),
  WEB("WEB");

  public static final String REFERENCE_TYPE_KEY = "type";

  private final String value;

  ReferenceTypeValue(String value) {
    this.value = value;
  }

  /**
   * Get ReferenceType from reference type string.
   *
   * @param referenceType string value of an reference type
   * @return valid ReferenceType
   */
  public static ReferenceTypeValue of(String referenceType) {
    return stream(ReferenceTypeValue.values())
        .filter(values -> values.value().equals(referenceType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid reference type", referenceType)));
  }

  @Override
  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return REFERENCE_TYPE_KEY + ": " + value;
  }
}
