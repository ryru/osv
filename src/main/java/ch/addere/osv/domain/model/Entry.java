package ch.addere.osv.domain.model;

import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.Modified;
import lombok.Builder;
import lombok.Value;

/**
 * Entry is an open source vulnerability.
 */
@Value
@Builder(builderMethodName = "hiddenBuilder")
public class Entry {

  Id id;
  Modified modified;

  /**
   * Entity builder creates new entities.
   * <p>A minimal entity requires an ID and a modified attribute.</p>
   *
   * @param id       unique ID
   * @param modified last modification date
   * @return valid entity
   */
  public static EntryBuilder builder(Id id, Modified modified) {
    return hiddenBuilder().id(id).modified(modified);
  }
}
