package ch.addere.osv;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Aliases;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Related;
import ch.addere.osv.impl.fields.DetailsValue;
import ch.addere.osv.impl.fields.ModifiedValue;
import ch.addere.osv.impl.fields.PublishedValue;
import ch.addere.osv.impl.fields.SummaryValue;
import ch.addere.osv.impl.fields.WithdrawnValue;
import java.util.List;
import java.util.Optional;

/**
 * Entry is an open source vulnerability.
 */
public interface Entry {

  Id id();

  ModifiedValue modified();

  Optional<Aliases> aliases();

  Optional<Related> related();

  Optional<PublishedValue> published();

  Optional<WithdrawnValue> withdrawn();

  Optional<SummaryValue> summary();

  Optional<DetailsValue> details();

  List<Affected> affected();

  List<References> references();
}
