package ch.addere.osv.domain.service.serializer.ranges;

import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static java.lang.String.format;

import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.ranges.Repo;
import ch.addere.osv.domain.model.fields.affected.ranges.Type;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeEcosystem;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeGit;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeSemVer;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.GitEvent;
import ch.addere.osv.domain.model.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.domain.service.OsvParserException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Deserialization helper class for affected range properties.
 */
public final class RangesDeserializer {

  /**
   * Deserialize affected range JSON nodes.
   *
   * @param rangesNode JSON data structure of an affected range property
   * @return Set of ranges
   * @throws OsvParserException if parser exception
   */
  public static Set<Ranges> deserialize(JsonNode rangesNode) throws OsvParserException {
    if (rangesNode.isArray()) {
      Set<Ranges> rangesSet = new HashSet<>();
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
    Type type = readRangeType(rangeNode.get(TYPE_KEY));
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

  private static Type readRangeType(JsonNode rangeTypeNode) throws OsvParserException {
    try {
      return Type.valueOf(rangeTypeNode.asText());
    } catch (IllegalArgumentException ex) {
      throw new OsvParserException(format("type '%s' is not valid", rangeTypeNode.asText()), ex);
    }
  }

  private static Optional<Ranges> getSemVerRanges(JsonNode rangeNode) throws OsvParserException {
    List<SemVerEvent> events = readRangeSemVerEvents(rangeNode.withArray(EVENTS_KEY));
    Optional<JsonNode> repoNode = Optional.ofNullable(rangeNode.get(REPO_KEY));
    if (repoNode.isPresent()) {
      return Optional.of(
          TypeSemVer.of(readRangeRepo(repoNode.get()), events.toArray(new SemVerEvent[0])));
    } else {
      return Optional.of(TypeSemVer.of(events.toArray(new SemVerEvent[0])));
    }
  }

  private static Optional<Ranges> getGitRanges(JsonNode rangeNode) throws OsvParserException {
    List<GitEvent> events = readRangeGitEvents(rangeNode.withArray(EVENTS_KEY));
    Repo repo = readRangeRepo(rangeNode.get(REPO_KEY));
    return Optional.of(TypeGit.of(repo, events.toArray(new GitEvent[0])));
  }

  private static Optional<Ranges> getEcosystemRanges(JsonNode rangeNode) throws OsvParserException {
    List<EcosystemEvent> events = readRangeEcosystem(rangeNode.withArray(EVENTS_KEY));
    Optional<JsonNode> repoNode = Optional.ofNullable(rangeNode.get(REPO_KEY));
    if (repoNode.isPresent()) {
      return Optional.of(TypeEcosystem.of(readRangeRepo(repoNode.get()),
          events.toArray(new EcosystemEvent[0])));
    } else {
      return Optional.of(TypeEcosystem.of(events.toArray(new EcosystemEvent[0])));
    }
  }

  private static Repo readRangeRepo(JsonNode rangeRepoNode) throws OsvParserException {
    try {
      return Repo.of(rangeRepoNode.asText());
    } catch (IllegalArgumentException e) {
      throw new OsvParserException("invalid repo url: '" + e.getMessage() + "'", e);
    }
  }

  private static List<SemVerEvent> readRangeSemVerEvents(ArrayNode eventArray) {
    List<SemVerEvent> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifier specifier = EventSpecifier.valueOf(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(SemVerEvent.of(specifier, version));
    }
    return eventList;
  }

  private static List<GitEvent> readRangeGitEvents(ArrayNode eventArray) {
    List<GitEvent> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifier specifier = EventSpecifier.valueOf(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(GitEvent.of(specifier, version));
    }
    return eventList;
  }

  private static List<EcosystemEvent> readRangeEcosystem(ArrayNode eventArray) {
    List<EcosystemEvent> eventList = new LinkedList<>();
    for (JsonNode jsonNode : eventArray) {
      var entry = jsonNode.fields().next();
      EventSpecifier specifier = EventSpecifier.valueOf(entry.getKey());
      String version = entry.getValue().asText();
      eventList.add(EcosystemEvent.of(specifier, version));
    }
    return eventList;
  }
}
