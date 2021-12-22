package ch.addere.osv.fields;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.Versions;
import ch.addere.osv.impl.fields.affected.DatabaseSpecificValue;
import ch.addere.osv.impl.fields.affected.EcosystemSpecificValue;
import java.util.List;
import java.util.Optional;

/**
 * Affected describe the package versions that are affected by this vulnerability.
 */
public interface Affected {

  Package pckg();

  List<Ranges> ranges();

  Optional<Versions> versions();

  Optional<EcosystemSpecificValue> ecosystemSpecific();

  Optional<DatabaseSpecificValue> databaseSpecific();

}
