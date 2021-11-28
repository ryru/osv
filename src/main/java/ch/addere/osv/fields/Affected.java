package ch.addere.osv.fields;

import ch.addere.osv.fields.affected.DatabaseSpecific;
import ch.addere.osv.fields.affected.EcosystemSpecific;
import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.Versions;
import java.util.List;
import java.util.Optional;

/**
 * Affected describe the package versions that are affected by this vulnerability.
 */
public interface Affected {

  Package pckg();

  List<Ranges> ranges();

  Optional<Versions> versions();

  Optional<EcosystemSpecific> ecosystemSpecific();

  Optional<DatabaseSpecific> databaseSpecific();

}
