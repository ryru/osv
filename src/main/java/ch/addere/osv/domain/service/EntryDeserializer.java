package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Affected.AFFECTED_KEY;
import static ch.addere.osv.domain.model.fields.Details.DETAILS_KEY;
import static ch.addere.osv.domain.model.fields.Id.ID_KEY;
import static ch.addere.osv.domain.model.fields.IdAggregate.ALIASES_KEY;
import static ch.addere.osv.domain.model.fields.IdAggregate.RELATED_KEY;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED_KEY;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED_KEY;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY_KEY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN_KEY;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Affected;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.Id.Database;
import ch.addere.osv.domain.model.fields.IdAggregate;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import ch.addere.osv.domain.service.serializer.AffectedDeserializer;
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
import java.util.Set;

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

    if (node.get(ID_KEY) == null) {
      throw new OsvParserException("deserialization error");
    }
    Id id = readId(node.get(ID_KEY));
    if (node.get(MODIFIED_KEY) == null) {
      throw new OsvParserException("deserialization error");
    }
    Modified modified = readModified(node.get(MODIFIED_KEY));
    Optional<IdAggregate> aliases = readAliases(node.get(ALIASES_KEY));
    Optional<IdAggregate> related = readRelated(node.get(RELATED_KEY));
    Optional<Published> published = readPublished(node.get(PUBLISHED_KEY));
    Optional<Withdrawn> withdrawn = readWithdrawn(node.get(WITHDRAWN_KEY));
    Optional<Summary> summary = readSummary(node.get(SUMMARY_KEY));
    Optional<Details> details = readDetails(node.get(DETAILS_KEY));
    Set<Affected> affected = Set.of();
    JsonNode affecedNode = node.withArray(AFFECTED_KEY);
    if (!affecedNode.isNull()) {
      affected = AffectedDeserializer.deserialize(node.get(AFFECTED_KEY));
    }

    return Entry.builder(id, modified)
        .published(published.orElse(null))
        .withdrawn(withdrawn.orElse(null))
        .aliases(aliases.orElse(null))
        .related(related.orElse(null))
        .summary(summary.orElse(null))
        .details(details.orElse(null))
        .affected(affected.toArray(new Affected[0]))
        .build();
  }

  private static Id readId(JsonNode idNode) {
    final String delimiter = "-";
    String[] tokenized = idNode.asText().split(delimiter);
    Database database = Database.valueOf(tokenized[0]);
    String entryId = stream(tokenized)
        .skip(1)
        .collect(joining(delimiter));
    return new Id(database, entryId);
  }

  private static Modified readModified(JsonNode modifiedNode) {
    Instant instant = readInstant(modifiedNode);
    return new Modified(instant);
  }

  private static Optional<IdAggregate> readAliases(JsonNode aliasesNode) {
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
      return Optional.of(IdAggregate.of(ids.toArray(new Id[0])));
    }
  }

  private static Optional<IdAggregate> readRelated(JsonNode relatedNode) {
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
      return Optional.of(IdAggregate.of(ids.toArray(new Id[0])));
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
    return Optional.of(new Summary(summary.asText()));
  }

  private static Optional<Details> readDetails(JsonNode details) {
    if (isEmptyJsonNode(details)) {
      return Optional.empty();
    }
    return Optional.of(new Details(details.asText()));
  }

  private static boolean isEmptyJsonNode(JsonNode relatedNode) {
    return relatedNode == null || relatedNode.isNull();
  }

  private static Instant readInstant(JsonNode jsonNode) {
    return Instant.parse(jsonNode.asText());
  }
}
