package ch.addere.osv.util;

import static ch.addere.osv.impl.fields.AffectedImpl.AFFECTED_KEY;
import static ch.addere.osv.impl.fields.DetailsValue.DETAILS_KEY;
import static ch.addere.osv.impl.fields.Id.ID_KEY;
import static ch.addere.osv.impl.fields.IdAggregate.ALIASES_KEY;
import static ch.addere.osv.impl.fields.IdAggregate.RELATED_KEY;
import static ch.addere.osv.impl.fields.ModifiedValue.MODIFIED_KEY;
import static ch.addere.osv.impl.fields.PublishedValue.PUBLISHED_KEY;
import static ch.addere.osv.impl.fields.ReferencesValues.REFERENCES_KEY;
import static ch.addere.osv.impl.fields.SummaryValue.SUMMARY_KEY;
import static ch.addere.osv.impl.fields.WithdrawnValue.WITHDRAWN_KEY;
import static ch.addere.osv.impl.fields.references.ReferenceTypeValue.REFERENCE_TYPE_KEY;
import static ch.addere.osv.impl.fields.references.ReferenceUrlValue.REFERENCE_URL_KEY;

import ch.addere.osv.Entry;
import ch.addere.osv.fields.Affected;
import ch.addere.osv.impl.EntryImpl;
import ch.addere.osv.impl.fields.DetailsValue;
import ch.addere.osv.impl.fields.Id;
import ch.addere.osv.impl.fields.IdAggregate;
import ch.addere.osv.impl.fields.ModifiedValue;
import ch.addere.osv.impl.fields.PublishedValue;
import ch.addere.osv.impl.fields.ReferencesValues;
import ch.addere.osv.impl.fields.SummaryValue;
import ch.addere.osv.impl.fields.WithdrawnValue;
import ch.addere.osv.impl.fields.references.ReferenceTypeValue;
import ch.addere.osv.impl.fields.references.ReferenceUrlValue;
import ch.addere.osv.util.serializer.AffectedDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
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
    return Id.fromString(idNode.asText());
  }

  private static ModifiedValue readModified(JsonNode modifiedNode) {
    Instant instant = readInstant(modifiedNode);
    return ModifiedValue.of(instant);
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

  private static Optional<PublishedValue> readPublished(JsonNode publishedNode) {
    if (isEmptyJsonNode(publishedNode)) {
      return Optional.empty();
    }
    Instant instant = readInstant(publishedNode);
    return Optional.of(PublishedValue.of(instant));
  }

  private static Optional<WithdrawnValue> readWithdrawn(JsonNode withdrawnNode) {
    if (isEmptyJsonNode(withdrawnNode)) {
      return Optional.empty();
    }
    Instant instant = readInstant(withdrawnNode);
    return Optional.of(WithdrawnValue.of(instant));
  }

  private static Optional<SummaryValue> readSummary(JsonNode summary) {
    if (isEmptyJsonNode(summary)) {
      return Optional.empty();
    }
    return Optional.of(SummaryValue.fromString(summary.asText()));
  }

  private static Optional<DetailsValue> readDetails(JsonNode details) {
    if (isEmptyJsonNode(details)) {
      return Optional.empty();
    }
    return Optional.of(DetailsValue.fromString(details.asText()));
  }

  private static List<ReferencesValues> readReferences(JsonNode references)
      throws OsvParserException {
    if (references.isArray()) {
      List<ReferencesValues> referencesList = new LinkedList<>();
      for (JsonNode jsonNode : references) {
        Optional<ReferencesValues> reference = readReference(jsonNode);
        reference.ifPresent(referencesList::add);
      }
      return referencesList;
    } else {
      throw new OsvParserException(REFERENCES_KEY + " node is not an array node");
    }
  }

  private static Optional<ReferencesValues> readReference(JsonNode reference) {
    Optional<JsonNode> referenceTypeNode = Optional.ofNullable(reference.get(REFERENCE_TYPE_KEY));
    Optional<JsonNode> urlNode = Optional.ofNullable(reference.get(REFERENCE_URL_KEY));

    if (referenceTypeNode.isPresent() && urlNode.isPresent()) {
      ReferenceTypeValue type = ReferenceTypeValue.of(referenceTypeNode.get().asText());
      ReferenceUrlValue url = ReferenceUrlValue.fromString(urlNode.get().asText());
      return Optional.of(ReferencesValues.of(type, url));
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
    ModifiedValue modified = readModified(node.get(MODIFIED_KEY));
    Optional<IdAggregate> aliases = readAliases(node.get(ALIASES_KEY));
    Optional<IdAggregate> related = readRelated(node.get(RELATED_KEY));
    Optional<PublishedValue> published = readPublished(node.get(PUBLISHED_KEY));
    Optional<WithdrawnValue> withdrawn = readWithdrawn(node.get(WITHDRAWN_KEY));
    Optional<SummaryValue> summary = readSummary(node.get(SUMMARY_KEY));
    Optional<DetailsValue> details = readDetails(node.get(DETAILS_KEY));
    List<Affected> affected = null;
    JsonNode affecedNode = node.get(AFFECTED_KEY);
    if (affecedNode != null && !affecedNode.isNull()) {
      affected = AffectedDeserializer.deserialize(affecedNode);
    }
    List<ReferencesValues> references = null;
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
      builder.references(references.toArray(new ReferencesValues[0]));
    }
    return builder.build();
  }
}
