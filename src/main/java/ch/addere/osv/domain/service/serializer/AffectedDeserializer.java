package ch.addere.osv.domain.service.serializer;

import static ch.addere.osv.domain.model.fields.Affected.AFFECTED_KEY;
import static ch.addere.osv.domain.model.fields.affected.DatabaseSpecific.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.EcosystemSpecific.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.Package.PACKAGE_KEY;
import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.Versions.VERSIONS_KEY;

import ch.addere.osv.domain.model.fields.Affected;
import ch.addere.osv.domain.model.fields.Affected.AffectedBuilder;
import ch.addere.osv.domain.model.fields.affected.DatabaseSpecific;
import ch.addere.osv.domain.model.fields.affected.EcosystemSpecific;
import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.Versions;
import ch.addere.osv.domain.service.OsvParserException;
import ch.addere.osv.domain.service.serializer.pckg.PackageDeserializer;
import ch.addere.osv.domain.service.serializer.ranges.RangesDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Deserialization helper class for affected properties.
 */
public final class AffectedDeserializer {

  /**
   * Deserialize affected JSON nodes.
   *
   * @param affectedNode JSON data structure of an affected property
   * @return Set of affected
   * @throws OsvParserException if parser exception
   */
  public static Set<Affected> deserialize(JsonNode affectedNode) throws OsvParserException {
    if (affectedNode.isArray()) {
      Set<Affected> affectedSet = new HashSet<>();
      for (JsonNode jsonNode : affectedNode) {
        Optional<Affected> affected = readAffected(jsonNode);
        affected.ifPresent(affectedSet::add);
      }
      return affectedSet;
    } else {
      throw new OsvParserException(AFFECTED_KEY + " node is not an array node");
    }
  }

  private static Optional<Affected> readAffected(JsonNode affected) throws OsvParserException {
    Package pckg = PackageDeserializer.deserialize(affected.get(PACKAGE_KEY));
    Set<Ranges> ranges = Set.of();
    JsonNode rangesNode = affected.withArray(RANGES_KEY);
    if (!rangesNode.isNull()) {
      ranges = RangesDeserializer.deserialize(rangesNode);
    }
    Optional<JsonNode> versionsNode = Optional.ofNullable(affected.get(VERSIONS_KEY));
    Optional<JsonNode> ecosystemSpecificNode = Optional.ofNullable(
        affected.get(ECOSYSTEM_SPECIFIC_KEY));
    Optional<JsonNode> databaseSpecificNode = Optional.ofNullable(
        affected.get(DATABASE_SPECIFIC_KEY));

    AffectedBuilder affectedBuilder = new AffectedBuilder(pckg).ranges(
        ranges.toArray(new Ranges[0]));

    versionsNode.ifPresent(jsonNode -> affectedBuilder.versions(readVersions(jsonNode)));

    ecosystemSpecificNode.ifPresent(
        jsonNode -> affectedBuilder.ecosystemSpecific(readEcosystemSpecific(jsonNode)));

    databaseSpecificNode.ifPresent(
        jsonNode -> affectedBuilder.databaseSpecific(readDatabaseSpecific(jsonNode)));

    return Optional.of(affectedBuilder.build());
  }

  private static Versions readVersions(JsonNode versionsNode) {
    List<String> versions = new ArrayList<>();
    if (versionsNode.isArray()) {
      for (final JsonNode versionNote : versionsNode) {
        versions.add(versionNote.asText());
      }
    }
    return Versions.of(versions.toArray(new String[0]));
  }

  private static EcosystemSpecific readEcosystemSpecific(JsonNode ecosystemSpecificNode) {
    return EcosystemSpecific.of(ecosystemSpecificNode.toString());
  }

  private static DatabaseSpecific readDatabaseSpecific(JsonNode databaseSpecificNode) {
    return DatabaseSpecific.of(databaseSpecificNode.toString());
  }
}
