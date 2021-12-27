package ch.addere.osv;

import ch.addere.osv.impl.fields.AffectedValues;
import ch.addere.osv.impl.fields.DetailsValue;
import ch.addere.osv.impl.fields.Id;
import ch.addere.osv.impl.fields.IdAggregate;
import ch.addere.osv.impl.fields.ModifiedValue;
import ch.addere.osv.impl.fields.PublishedValue;
import ch.addere.osv.impl.fields.ReferencesValues;
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

  Optional<IdAggregate> aliases();

  Optional<IdAggregate> related();

  Optional<PublishedValue> published();

  Optional<WithdrawnValue> withdrawn();

  Optional<SummaryValue> summary();

  Optional<DetailsValue> details();

  List<AffectedValues> affected();

  List<ReferencesValues> references();
}
