package ch.addere.osv.util.serializer;

import static ch.addere.osv.property.AffectedValues.AFFECTED_KEY;
import static ch.addere.osv.property.affected.DatabaseSpecificValue.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.property.affected.EcosystemSpecificValue.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.property.affected.PackageValues.PACKAGE_KEY;
import static ch.addere.osv.property.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.property.affected.VersionsValue.VERSIONS_KEY;

import ch.addere.osv.EntrySchemaVersion;
import ch.addere.osv.property.AffectedValues;
import ch.addere.osv.property.AffectedValues.AffectedValuesBuilder;
import ch.addere.osv.property.affected.DatabaseSpecificValue;
import ch.addere.osv.property.affected.EcosystemSpecificValue;
import ch.addere.osv.property.affected.PackageValues;
import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.VersionsValue;
import ch.addere.osv.util.OsvParserException;
import ch.addere.osv.util.serializer.pckg.PackageDeserializer;
import ch.addere.osv.util.serializer.ranges.RangesDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Deserialization helper class for affected properties.
 */
public final class AffectedDeserializer {

  private AffectedDeserializer() {
  }

  /**
   * Deserialize affected JSON nodes.
   *
   * @param affectedNode JSON data structure of an affected property
   * @return Set of affected
   * @throws OsvParserException if parser exception
   */
  public static List<AffectedValues> deserialize(
      JsonNode affectedNode,
      EntrySchemaVersion schemaVersion) throws OsvParserException {
    if (affectedNode.isArray()) {
      List<AffectedValues> affectedSet = new LinkedList<>();
      for (JsonNode jsonNode : affectedNode) {
        Optional<AffectedValues> affected = readAffected(jsonNode, schemaVersion);
        affected.ifPresent(affectedSet::add);
      }
      return affectedSet;
    } else {
      throw new OsvParserException(AFFECTED_KEY + " node is not an array node");
    }
  }

  private static Optional<AffectedValues> readAffected(
      JsonNode affected,
      EntrySchemaVersion schemaVersion)
      throws OsvParserException {
    PackageValues pckg = PackageDeserializer.deserialize(affected.get(PACKAGE_KEY));
    List<Ranges> ranges = List.of();
    JsonNode rangesNode = affected.withArray(RANGES_KEY);
    if (!rangesNode.isNull()) {
      ranges = RangesDeserializer.deserialize(rangesNode);
    }
    Optional<JsonNode> versionsNode = Optional.ofNullable(affected.get(VERSIONS_KEY));
    Optional<JsonNode> ecosystemSpecificNode = Optional.ofNullable(
        affected.get(ECOSYSTEM_SPECIFIC_KEY));
    Optional<JsonNode> databaseSpecificNode = Optional.ofNullable(
        affected.get(DATABASE_SPECIFIC_KEY));

    AffectedValuesBuilder affectedValuesBuilder = new AffectedValuesBuilder(pckg).ranges(
        ranges.toArray(new Ranges[0]));

    versionsNode.ifPresent(jsonNode -> affectedValuesBuilder.versions(readVersions(jsonNode)));

    ecosystemSpecificNode.ifPresent(
        jsonNode -> affectedValuesBuilder.ecosystemSpecific(readEcosystemSpecific(jsonNode)));

    databaseSpecificNode.ifPresent(
        jsonNode -> affectedValuesBuilder.databaseSpecific(readDatabaseSpecific(jsonNode)));

    return Optional.of(affectedValuesBuilder.entrySchemaVersion(schemaVersion).build());
  }

  private static VersionsValue readVersions(JsonNode versionsNode) {
    List<String> versions = new ArrayList<>();
    if (versionsNode.isArray()) {
      for (final JsonNode versionNote : versionsNode) {
        versions.add(versionNote.asText());
      }
    }
    return VersionsValue.of(versions.toArray(new String[0]));
  }

  private static EcosystemSpecificValue readEcosystemSpecific(JsonNode ecosystemSpecificNode) {
    return EcosystemSpecificValue.fromString(ecosystemSpecificNode.toString());
  }

  private static DatabaseSpecificValue readDatabaseSpecific(JsonNode databaseSpecificNode) {
    return DatabaseSpecificValue.fromString(databaseSpecificNode.toString());
  }
}
