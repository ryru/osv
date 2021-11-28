package ch.addere.osv.util.serializer;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.impl.fields.AffectedImpl.AFFECTED_KEY;
import static ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.impl.fields.affected.PackageImpl.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.VersionsImpl.VERSIONS_KEY;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.impl.fields.AffectedImpl.AffectedBuilder;
import ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl;
import ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl;
import ch.addere.osv.impl.fields.affected.VersionsImpl;
import ch.addere.osv.util.OsvParserException;
import ch.addere.osv.util.serializer.pckg.PackageDeserializer;
import ch.addere.osv.util.serializer.ranges.RangesDeserializer;
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

    AffectedBuilder affectedBuilder = new AffectedBuilder(pckg).ranges(
        ranges.toArray(new Ranges[0]));

    versionsNode.ifPresent(jsonNode -> affectedBuilder.versions(readVersions(jsonNode)));

    ecosystemSpecificNode.ifPresent(
        jsonNode -> affectedBuilder.ecosystemSpecific(readEcosystemSpecific(jsonNode)));

    databaseSpecificNode.ifPresent(
        jsonNode -> affectedBuilder.databaseSpecific(readDatabaseSpecific(jsonNode)));

    return Optional.of(affectedBuilder.build());
  }

  private static VersionsImpl readVersions(JsonNode versionsNode) {
    List<String> versions = new ArrayList<>();
    if (versionsNode.isArray()) {
      for (final JsonNode versionNote : versionsNode) {
        versions.add(versionNote.asText());
      }
    }
    return VersionsImpl.of(versions.toArray(new String[0]));
  }

  private static EcosystemSpecificImpl readEcosystemSpecific(JsonNode ecosystemSpecificNode) {
    return EcosystemSpecificImpl.of(ecosystemSpecificNode.toString());
  }

  private static DatabaseSpecificImpl readDatabaseSpecific(JsonNode databaseSpecificNode) {
    return DatabaseSpecificImpl.of(databaseSpecificNode.toString());
  }
}
