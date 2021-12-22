package ch.addere.osv.fields.affected;

import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import java.util.Optional;

/**
 * Package identifies the affected code library or command provided by the package.
 */
public interface Package {

  EcosystemValue ecosystem();

  NameValue name();

  Optional<PurlValue> purl();

}
