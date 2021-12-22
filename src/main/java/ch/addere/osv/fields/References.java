package ch.addere.osv.fields;

import ch.addere.osv.impl.fields.references.ReferenceTypeValue;
import ch.addere.osv.impl.fields.references.ReferenceUrlValue;

/**
 * Reference field property.
 */
public interface References {

  ReferenceTypeValue referenceType();

  ReferenceUrlValue referenceUrl();

}
