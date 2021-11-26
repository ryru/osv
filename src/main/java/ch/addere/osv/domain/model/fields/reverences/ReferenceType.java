package ch.addere.osv.domain.model.fields.reverences;

/**
 * Reference type property.
 */
public enum ReferenceType {

  ADVISORY("ADVISORY"),
  ARTICLE("ARTICLE"),
  REPORT("REPORT"),
  FIX("FIX"),
  PACKAGE("PACKAGE"),
  WEB("WEB");

  public static final String REFERENCE_TYPE_KEY = "type";

  private final String referenceType;

  ReferenceType(String referenceType) {
    this.referenceType = referenceType;
  }

  @Override
  public String toString() {
    return REFERENCE_TYPE_KEY + ": " + referenceType;
  }
}
