package ch.addere.osv.util.serializer.pckg;

import static ch.addere.osv.impl.fields.affected.PackageImpl.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl.ECOSYSTEM_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.pckg.Ecosystem;
import ch.addere.osv.fields.affected.pckg.Name;
import ch.addere.osv.fields.affected.pckg.Purl;
import ch.addere.osv.impl.fields.affected.PackageImpl;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl;
import ch.addere.osv.impl.fields.affected.pckg.NameImpl;
import ch.addere.osv.impl.fields.affected.pckg.PurlImpl;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

/**
 * Deserialization helper class for affected package properties.
 */
public final class PackageDeserializer {

  /**
   * Deserialize affected package JSON nodes.
   *
   * @param packageNode JSON data structure of an affected range property
   * @return A Package
   */
  public static Package deserialize(JsonNode packageNode) throws OsvParserException {
    if (!packageNode.isEmpty()) {
      return readPackage(packageNode);
    }
    throw new OsvParserException(PACKAGE_KEY + " node is not a valid node");
  }

  private static Package readPackage(JsonNode pckg) {
    Ecosystem ecosystem = readEcosystem(pckg.get(ECOSYSTEM_KEY));
    Name name = readName(pckg.get(NAME_KEY));
    Optional<JsonNode> purlNode = Optional.ofNullable(pckg.get(PURL_KEY));
    return purlNode.map(jsonNode -> PackageImpl.of(ecosystem, name, readPurl(jsonNode)))
        .orElseGet(() -> PackageImpl.of(ecosystem, name));
  }

  private static Ecosystem readEcosystem(JsonNode ecosystem) {
    final String name = ecosystem.asText();
    return EcosystemImpl.of(name);
  }

  private static Name readName(JsonNode name) {
    return NameImpl.of(name.asText());
  }

  private static Purl readPurl(JsonNode purl) {
    return PurlImpl.of(purl.asText());
  }
}
