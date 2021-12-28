package ch.addere.osv.util.serializer.ranges;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RepoValue.REPO_KEY;
import static java.lang.String.format;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.impl.fields.affected.ranges.RangeTypeValue;
import ch.addere.osv.impl.fields.affected.ranges.RepoValue;
import ch.addere.osv.impl.fields.affected.ranges.TypeEcosystemValues.TypeEcosystemBuilder;
import ch.addere.osv.impl.fields.affected.ranges.TypeGitValues.TypeGitBuilder;
import ch.addere.osv.impl.fields.affected.ranges.TypeSemVerValues.TypeSemVerBuilder;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEventValues;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierValue;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEventValues;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEventValues;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Deserialization helper class for affected range properties.
 */
public final class RangesDeserializer {

  private RangesDeserializer() {
  }

  /**
   * Deserialize affected range JSON nodes.
   *
   * @param rangesNode JSON data structure of an affected range property
   * @return Set of ranges
   * @throws OsvParserException if parser exception
   */
  public static List<Ranges> deserialize(JsonNode rangesNode) throws OsvParserException {
    if (rangesNode.isArray()) {
      List<Ranges> rangesSet = new LinkedList<>();
      for (JsonNode jsonNode : rangesNode) {
        Optional<Ranges> range = readRange(jsonNode);
        range.ifPresent(rangesSet::add);
      }
      return rangesSet;
    } else {
      throw new OsvParserException(RANGES_KEY + " node is not an array node");
    }
  }

  private static Optional<Ranges> readRange(JsonNode rangeNode) throws OsvParserException {
    RangeTypeValue type = readRangeType(rangeNode.get(TYPE_KEY));
    switch (type) {
      case SEMVER:
        return getSemVerRanges(rangeNode);
      case GIT:
        return getGitRanges(rangeNode);
      case ECOSYSTEM:
        return getEcosystemRanges(rangeNode);
      default:
        throw new OsvParserException("type '%s' is not valid");
    }
  }

  private static RangeTypeValue readRangeType(JsonNode rangeTypeNode) throws OsvParserException {
    try {
      return RangeTypeValue.of(rangeTypeNode.asText());
    } catch (IllegalArgumentException ex) {
      throw new OsvParserException(format("type '%s' is not valid", rangeTypeNode.asText()), ex);
    }
  }

  private static Optional<Ranges> getSemVerRanges(JsonNode rangeNode) throws OsvParserException {
    List<SemVerEventValues> events = readRangeSemVerEvents(rangeNode.withArray(EVENTS_KEY));
    Optional<JsonNode> repoNode = Optional.ofNullable(rangeNode.get(REPO_KEY));
    TypeSemVerBuilder semVer = new TypeSemVerBuilder(events.toArray(new SemVerEventValues[0]));
    if (repoNode.isPresent()) {
      return Optional.of(semVer.repo(readRangeRepo(repoNode.get())).build());
    } else {
      return Optional.of(semVer.build());
    }
  }

  private static Optional<Ranges> getGitRanges(JsonNode rangeNode) throws OsvParserException {
    List<GitEventValues> events = readRangeGitEvents(rangeNode.withArray(EVENTS_KEY));
    RepoValue repo = readRangeRepo(rangeNode.get(REPO_KEY));
    return Optional.of(new TypeGitBuilder(repo, events.toArray(new GitEventValues[0])).build());
  }

  private static Optional<Ranges> getEcosystemRanges(JsonNode rangeNode) throws OsvParserException {
    List<EcosystemEventValues> events = readRangeEcosystem(rangeNode.withArray(EVENTS_KEY));
    Optional<JsonNode> repoNode = Optional.ofNullable(rangeNode.get(REPO_KEY));
    TypeEcosystemBuilder typeEcosystem = new TypeEcosystemBuilder(
        events.toArray(new EcosystemEventValues[0]));
    if (repoNode.isPresent()) {
      return Optional.of(typeEcosystem
          .repo(readRangeRepo(repoNode.get()))
          .build());
    } else {
      return Optional.of(typeEcosystem.build());
    }
  }

  private static RepoValue readRangeRepo(JsonNode rangeRepoNode) throws OsvParserException {
    try {
      return RepoValue.fromString(rangeRepoNode.asText());
    } catch (IllegalArgumentException e) {
      throw new OsvParserException("invalid repo url: '" + e.getMessage() + "'", e);
    }
  }

  private static List<SemVerEventValues> readRangeSemVerEvents(ArrayNode eventArray) {
    List<SemVerEventValues> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifierValue specifier = EventSpecifierValue.of(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(SemVerEventValues.of(specifier, version));
    }
    return eventList;
  }

  private static List<GitEventValues> readRangeGitEvents(ArrayNode eventArray) {
    List<GitEventValues> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifierValue specifier = EventSpecifierValue.of(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(GitEventValues.of(specifier, version));
    }
    return eventList;
  }

  private static List<EcosystemEventValues> readRangeEcosystem(ArrayNode eventArray) {
    List<EcosystemEventValues> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifierValue specifier = EventSpecifierValue.of(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(EcosystemEventValues.of(specifier, version));
    }
    return eventList;
  }
}
