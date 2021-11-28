package ch.addere.osv.fields.affected;

import ch.addere.osv.fields.affected.pckg.Name;
import ch.addere.osv.fields.affected.pckg.Purl;
import ch.addere.osv.impl.fields.affected.pckg.Ecosystem;
import java.util.Optional;

/**
 * Package identifies the affected code library or command provided by the package.
 */
public interface Package {

  Ecosystem ecosystem();

  Name name();

  Optional<Purl> purl();

}
