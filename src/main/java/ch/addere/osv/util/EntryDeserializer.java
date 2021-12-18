package ch.addere.osv.util;

import static ch.addere.osv.impl.fields.AffectedImpl.AFFECTED_KEY;
import static ch.addere.osv.impl.fields.DetailsImpl.DETAILS_KEY;
import static ch.addere.osv.impl.fields.IdAggregate.ALIASES_KEY;
import static ch.addere.osv.impl.fields.IdAggregate.RELATED_KEY;
import static ch.addere.osv.impl.fields.IdImpl.ID_KEY;
import static ch.addere.osv.impl.fields.ModifiedImpl.MODIFIED_KEY;
import static ch.addere.osv.impl.fields.PublishedImpl.PUBLISHED_KEY;
import static ch.addere.osv.impl.fields.ReferencesImpl.REFERENCES_KEY;
import static ch.addere.osv.impl.fields.SummaryImpl.SUMMARY_KEY;
import static ch.addere.osv.impl.fields.WithdrawnImpl.WITHDRAWN_KEY;
import static ch.addere.osv.impl.fields.references.ReferenceTypeImpl.REFERENCE_TYPE_KEY;
import static ch.addere.osv.impl.fields.references.ReferenceUrlImpl.REFERENCE_URL_KEY;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.Entry;
import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Details;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.IdDatabase;
import ch.addere.osv.fields.Modified;
import ch.addere.osv.fields.Published;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Summary;
import ch.addere.osv.fields.Withdrawn;
import ch.addere.osv.fields.references.ReferenceType;
import ch.addere.osv.fields.references.ReferenceUrl;
import ch.addere.osv.impl.EntryImpl;
import ch.addere.osv.impl.fields.DetailsImpl;
import ch.addere.osv.impl.fields.IdAggregate;
import ch.addere.osv.impl.fields.IdDatabaseImpl;
import ch.addere.osv.impl.fields.IdImpl;
import ch.addere.osv.impl.fields.ModifiedImpl;
import ch.addere.osv.impl.fields.PublishedImpl;
import ch.addere.osv.impl.fields.ReferencesImpl;
import ch.addere.osv.impl.fields.SummaryImpl;
import ch.addere.osv.impl.fields.WithdrawnImpl;
import ch.addere.osv.impl.fields.references.ReferenceTypeImpl;
import ch.addere.osv.impl.fields.references.ReferenceUrlImpl;
import ch.addere.osv.util.serializer.AffectedDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

  private static Id readId(JsonNode idNode) {
    final String delimiter = "-";
    String[] tokenized = idNode.asText().split(delimiter);
    IdDatabase database = IdDatabaseImpl.of(tokenized[0]);
    String entryId = stream(tokenized)
        .skip(1)
        .collect(joining(delimiter));
    return IdImpl.of(database, entryId);
  }

  private static Modified readModified(JsonNode modifiedNode) {
    Instant instant = readInstant(modifiedNode);
    return ModifiedImpl.of(instant);
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
    return Optional.of(PublishedImpl.of(instant));
  }

  private static Optional<Withdrawn> readWithdrawn(JsonNode withdrawnNode) {
    if (isEmptyJsonNode(withdrawnNode)) {
      return Optional.empty();
    }
    Instant instant = readInstant(withdrawnNode);
    return Optional.of(WithdrawnImpl.of(instant));
  }

  private static Optional<Summary> readSummary(JsonNode summary) {
    if (isEmptyJsonNode(summary)) {
      return Optional.empty();
    }
    return Optional.of(SummaryImpl.of(summary.asText()));
  }

  private static Optional<Details> readDetails(JsonNode details) {
    if (isEmptyJsonNode(details)) {
      return Optional.empty();
    }
    return Optional.of(DetailsImpl.of(details.asText()));
  }

  private static List<References> readReferences(JsonNode references)
      throws OsvParserException {
    if (references.isArray()) {
      List<References> referencesList = new LinkedList<>();
      for (JsonNode jsonNode : references) {
        Optional<References> reference = readReference(jsonNode);
        reference.ifPresent(referencesList::add);
      }
      return referencesList;
    } else {
      throw new OsvParserException(REFERENCES_KEY + " node is not an array node");
    }
  }

  private static Optional<References> readReference(JsonNode reference) {
    Optional<JsonNode> referenceTypeNode = Optional.ofNullable(reference.get(REFERENCE_TYPE_KEY));
    Optional<JsonNode> urlNode = Optional.ofNullable(reference.get(REFERENCE_URL_KEY));

    if (referenceTypeNode.isPresent() && urlNode.isPresent()) {
      ReferenceType type = ReferenceTypeImpl.of(referenceTypeNode.get().asText());
      ReferenceUrl url = ReferenceUrlImpl.of(URI.create(urlNode.get().asText()));
      return Optional.of(ReferencesImpl.of(type, url));
    } else {
      return Optional.empty();
    }
  }

  private static boolean isEmptyJsonNode(JsonNode relatedNode) {
    return relatedNode == null || relatedNode.isNull();
  }

  private static Instant readInstant(JsonNode jsonNode) {
    return Instant.parse(jsonNode.asText());
  }

  @Override
  public Entry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.get(ID_KEY) == null) {
      throw new OsvParserException("deserialization error");
    }
    final Id id = readId(node.get(ID_KEY));
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
    List<Affected> affected = null;
    JsonNode affecedNode = node.get(AFFECTED_KEY);
    if (affecedNode != null && !affecedNode.isNull()) {
      affected = AffectedDeserializer.deserialize(affecedNode);
    }
    List<References> references = null;
    JsonNode referenceNode = node.get(REFERENCES_KEY);
    if (referenceNode != null && !referenceNode.isNull()) {
      references = readReferences(referenceNode);
    }

    EntryImpl.EntryBuilder builder = EntryImpl.builder(id, modified)
        .published(published.orElse(null))
        .withdrawn(withdrawn.orElse(null))
        .aliases(aliases.orElse(null))
        .related(related.orElse(null))
        .summary(summary.orElse(null))
        .details(details.orElse(null));
    if (affected != null) {
      builder.affected(affected.toArray(new Affected[0]));
    }

    if (references != null) {
      builder.references(references.toArray(new References[0]));
    }
    return builder.build();
  }
}
