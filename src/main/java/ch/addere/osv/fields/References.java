package ch.addere.osv.fields;

import ch.addere.osv.fields.references.ReferenceType;
import ch.addere.osv.fields.references.ReferenceUrl;

/**
 * Reference field property.
 */
public interface References {

  ReferenceType referenceType();

  ReferenceUrl referenceUrl();

}
