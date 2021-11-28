package ch.addere.osv.impl.fields.references;

import ch.addere.osv.fields.references.ReferenceType;

/**
 * Reference type property.
 */
public enum ReferenceTypeImpl implements ReferenceType {

  ADVISORY("ADVISORY"),
  ARTICLE("ARTICLE"),
  REPORT("REPORT"),
  FIX("FIX"),
  PACKAGE("PACKAGE"),
  WEB("WEB");

  public static final String REFERENCE_TYPE_KEY = "type";

  private final String referenceType;

  ReferenceTypeImpl(String referenceType) {
    this.referenceType = referenceType;
  }

  @Override
  public String toString() {
    return REFERENCE_TYPE_KEY + ": " + referenceType;
  }
}
