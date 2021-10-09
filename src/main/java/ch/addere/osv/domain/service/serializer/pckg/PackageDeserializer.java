package ch.addere.osv.domain.service.serializer.pckg;

import static ch.addere.osv.domain.model.fields.affected.Package.PACKAGE_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Name.NAME_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Purl.PURL_KEY;

import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import ch.addere.osv.domain.service.OsvParserException;
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
    Ecosystem ecosystem = readEcosystem(pckg.get(ECOSYSTEM_KEY)).orElse(null);
    Name name = readName(pckg.get(NAME_KEY));
    Optional<JsonNode> purlNode = Optional.ofNullable(pckg.get(PURL_KEY));
    return purlNode.map(jsonNode -> Package.of(ecosystem, name, readPurl(jsonNode)))
        .orElseGet(() -> Package.of(ecosystem, name));
  }

  private static Optional<Ecosystem> readEcosystem(JsonNode ecosystem) {
    final var name = ecosystem.asText();
    return Optional.of(Ecosystem.valueOf(name));
  }

  private static Name readName(JsonNode name) {
    return Name.of(name.asText());
  }

  private static Purl readPurl(JsonNode purl) {
    return Purl.of(purl.asText());
  }
}
