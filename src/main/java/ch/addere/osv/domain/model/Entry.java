package ch.addere.osv.domain.model;

import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.IdAggregate;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import java.util.Optional;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Entry is an open source vulnerability.
 */
@Value
@Accessors(fluent = true)
@Builder(builderMethodName = "hiddenBuilder")
public class Entry {

  Id id;
  Modified modified;
  Optional<IdAggregate> aliases;
  Optional<IdAggregate> related;
  Optional<Published> published;
  Optional<Withdrawn> withdrawn;
  Optional<Summary> summary;
  Optional<Details> details;

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

  public Id getId() {
    return id;
  }

  public Modified getModified() {
    return modified;
  }

}
