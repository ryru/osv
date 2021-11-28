package ch.addere.osv;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Details;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.Modified;
import ch.addere.osv.fields.Published;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Summary;
import ch.addere.osv.fields.Withdrawn;
import ch.addere.osv.impl.fields.IdAggregate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Entry is an open source vulnerability.
 */
public interface Entry {

  Id id();

  Modified modified();

  Optional<IdAggregate> aliases();

  Optional<IdAggregate> related();

  Optional<Published> published();

  Optional<Withdrawn> withdrawn();

  Optional<Summary> summary();

  Optional<Details> details();

  Set<Affected> affected();

  List<References> references();
}
