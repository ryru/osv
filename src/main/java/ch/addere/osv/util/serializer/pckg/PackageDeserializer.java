package ch.addere.osv.util.serializer.pckg;

import static ch.addere.osv.property.affected.PackageValues.PACKAGE_KEY;
import static ch.addere.osv.property.affected.pckg.EcosystemValue.ECOSYSTEM_KEY;
import static ch.addere.osv.property.affected.pckg.NameValue.NAME_KEY;
import static ch.addere.osv.property.affected.pckg.PurlValue.PURL_KEY;

import ch.addere.osv.property.affected.PackageValues;
import ch.addere.osv.property.affected.PackageValues.PackageValueBuilder;
import ch.addere.osv.property.affected.pckg.EcosystemValue;
import ch.addere.osv.property.affected.pckg.NameValue;
import ch.addere.osv.property.affected.pckg.PurlValue;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

/**
 * Deserialization helper class for affected package properties.
 */
public final class PackageDeserializer {

  private PackageDeserializer() {
  }

  /**
   * Deserialize affected package JSON nodes.
   *
   * @param packageNode JSON data structure of an affected range property
   * @return A Package
   */
  public static PackageValues deserialize(JsonNode packageNode) throws OsvParserException {
    if (!packageNode.isEmpty()) {
      return readPackage(packageNode);
    }
    throw new OsvParserException(PACKAGE_KEY + " node is not a valid node");
  }

  private static PackageValues readPackage(JsonNode pckg) {
    EcosystemValue ecosystem = readEcosystem(pckg.get(ECOSYSTEM_KEY));
    NameValue name = readName(pckg.get(NAME_KEY));
    Optional<JsonNode> purlNode = Optional.ofNullable(pckg.get(PURL_KEY));
    PackageValueBuilder packageValueBuilder = new PackageValueBuilder(ecosystem, name);
    return purlNode.map(jsonNode -> packageValueBuilder.purl(readPurl(jsonNode)).build())
        .orElseGet(packageValueBuilder::build);
  }

  private static EcosystemValue readEcosystem(JsonNode ecosystem) {
    final String name = ecosystem.asText();
    return EcosystemValue.of(name);
  }

  private static NameValue readName(JsonNode name) {
    return NameValue.fromString(name.asText());
  }

  private static PurlValue readPurl(JsonNode purl) {
    return PurlValue.fromString(purl.asText());
  }
}
