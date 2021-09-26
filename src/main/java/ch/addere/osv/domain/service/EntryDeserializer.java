package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Aliases.ALIASES;
import static ch.addere.osv.domain.model.fields.Details.DETAILS;
import static ch.addere.osv.domain.model.fields.Id.Database.valueOf;
import static ch.addere.osv.domain.model.fields.Id.ID;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED;
import static ch.addere.osv.domain.model.fields.Related.RELATED;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Aliases;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.Id.Database;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.Related;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;

/**
 * Deserializer for open source vulnerabilities.
 */
public class EntryDeserializer extends StdDeserializer<Entry> {

  protected EntryDeserializer() {
    this(null);
  }

  protected EntryDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Entry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.get(ID) == null) {
      throw new ParserException("deserialization error");
    }
    Id id = readId(node.get(ID));
    if (node.get(MODIFIED) == null) {
      throw new ParserException("deserialization error");
    }
    Modified modified = readModified(node.get(MODIFIED));
    Optional<Aliases> aliases = readAliases(node.get(ALIASES));
    Optional<Related> related = readRelated(node.get(RELATED));
    Optional<Published> published = readPublished(node.get(PUBLISHED));
    Optional<Withdrawn> withdrawn = readWithdrawn(node.get(WITHDRAWN));
    Optional<Summary> summary = readSummary(node.get(SUMMARY));
    Optional<Details> details = readDetails(node.get(DETAILS));

    return Entry.builder(id, modified)
        .published(published)
        .withdrawn(withdrawn)
        .aliases(aliases)
        .related(related)
        .summary(summary)
        .details(details)
        .build();
  }

  private static Id readId(JsonNode idNode) {
    final String delimiter = "-";
    String[] tokenized = trimJson(idNode.toString()).split(delimiter);
    Database database = valueOf(tokenized[0]);
    String entryId = stream(tokenized)
        .skip(1)
        .collect(joining(delimiter));
    return new Id(database, entryId);
  }

  private static Modified readModified(JsonNode modifiedNode) {
    Instant instant = readInstant(modifiedNode);
    return new Modified(instant);
  }

  private static Optional<Aliases> readAliases(JsonNode aliasesNode) {
    if (isEmptyJsonNode(aliasesNode)) {
      return Optional.empty();
    }
    List<Id> ids = new ArrayList<>();
    if (aliasesNode.isArray()) {
      for (final JsonNode idNote : aliasesNode) {
        ids.add(readId(idNote));
      }
    }
    if (ids.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new Aliases(ids));
    }
  }

  private static Optional<Related> readRelated(JsonNode relatedNode) {
    if (isEmptyJsonNode(relatedNode)) {
      return Optional.empty();
    }
    List<Id> ids = new ArrayList<>();
    if (relatedNode.isArray()) {
      for (final JsonNode idNote : relatedNode) {
        ids.add(readId(idNote));
      }
    }
    if (ids.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new Related(ids));
    }
  }

  private static Optional<Published> readPublished(JsonNode publishedNode) {
    if (isEmptyJsonNode(publishedNode)) {
      return Optional.empty();
    }
    Instant instant = readInstant(publishedNode);
    return Optional.of(new Published(instant));
  }

  private static Optional<Withdrawn> readWithdrawn(JsonNode withdrawnNode) {
    if (isEmptyJsonNode(withdrawnNode)) {
      return Optional.empty();
    }
    Instant instant = readInstant(withdrawnNode);
    return Optional.of(new Withdrawn(instant));
  }

  private static Optional<Summary> readSummary(JsonNode summary) {
    if (isEmptyJsonNode(summary)) {
      return Optional.empty();
    }
    return Optional.of(new Summary(trimJson(summary.toString())));
  }

  private static Optional<Details> readDetails(JsonNode details) {
    if (isEmptyJsonNode(details)) {
      return Optional.empty();
    }
    return Optional.of(new Details(trimJson(details.toString())));
  }

  private static boolean isEmptyJsonNode(JsonNode relatedNode) {
    return relatedNode == null || relatedNode.isNull();
  }

  private static Instant readInstant(JsonNode jsonNode) {
    return Instant.parse(trimJson(jsonNode.toString()));
  }

  private static String trimJson(@NonNull String json) {
    return json.trim().replace("\"", "");
  }
}
