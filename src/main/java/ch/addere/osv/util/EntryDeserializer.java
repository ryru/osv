package ch.addere.osv.util;

import static ch.addere.osv.property.AffectedValues.AFFECTED_KEY;
import static ch.addere.osv.property.AliasesValue.ALIASES_KEY;
import static ch.addere.osv.property.DetailsValue.DETAILS_KEY;
import static ch.addere.osv.property.IdValue.ID_KEY;
import static ch.addere.osv.property.ModifiedValue.MODIFIED_KEY;
import static ch.addere.osv.property.PublishedValue.PUBLISHED_KEY;
import static ch.addere.osv.property.ReferencesValues.REFERENCES_KEY;
import static ch.addere.osv.property.RelatedValue.RELATED_KEY;
import static ch.addere.osv.property.SchemaVersionValue.SCHEMA_VERSION_KEY;
import static ch.addere.osv.property.SummaryValue.SUMMARY_KEY;
import static ch.addere.osv.property.WithdrawnValue.WITHDRAWN_KEY;
import static ch.addere.osv.property.affected.DatabaseSpecificValue.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.property.references.ReferenceTypeValue.REFERENCE_TYPE_KEY;
import static ch.addere.osv.property.references.ReferenceUrlValue.REFERENCE_URL_KEY;

import ch.addere.osv.Entry;
import ch.addere.osv.EntrySchemaVersion;
import ch.addere.osv.property.AffectedValues;
import ch.addere.osv.property.AliasesValue;
import ch.addere.osv.property.DetailsValue;
import ch.addere.osv.property.IdValue;
import ch.addere.osv.property.ModifiedValue;
import ch.addere.osv.property.PublishedValue;
import ch.addere.osv.property.ReferencesValues;
import ch.addere.osv.property.RelatedValue;
import ch.addere.osv.property.SchemaVersionValue;
import ch.addere.osv.property.SummaryValue;
import ch.addere.osv.property.WithdrawnValue;
import ch.addere.osv.property.affected.DatabaseSpecificValue;
import ch.addere.osv.property.references.ReferenceTypeValue;
import ch.addere.osv.property.references.ReferenceUrlValue;
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
public final class EntryDeserializer extends StdDeserializer<Entry> {

  EntryDeserializer() {
    this(null);
  }

  @Override
  public Entry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.get(ID_KEY) == null) {
      throw new OsvParserException("deserialization error");
    }
    final IdValue id = readId(node.get(ID_KEY));
    if (node.get(MODIFIED_KEY) == null) {
      throw new OsvParserException("deserialization error");
    }
    ModifiedValue modified = readModified(node.get(MODIFIED_KEY));
    Optional<SchemaVersionValue> schemaVersion = readSchemaVersion(node.get(SCHEMA_VERSION_KEY));
    Optional<AliasesValue> aliases = readAliases(node.get(ALIASES_KEY));
    Optional<RelatedValue> related = readIdAggregate(node.get(RELATED_KEY));
    Optional<PublishedValue> published = readPublished(node.get(PUBLISHED_KEY));
    Optional<WithdrawnValue> withdrawn = readWithdrawn(node.get(WITHDRAWN_KEY));
    Optional<SummaryValue> summary = readSummary(node.get(SUMMARY_KEY));
    Optional<DetailsValue> details = readDetails(node.get(DETAILS_KEY));
    List<AffectedValues> affected = null;
    JsonNode affectedNode = node.get(AFFECTED_KEY);
    if (affectedNode != null && !affectedNode.isNull()) {
      if (schemaVersion.isPresent()) {
        affected = AffectedDeserializer.deserialize(affectedNode, schemaVersion.get().value2());
      } else {
        affected = AffectedDeserializer.deserialize(affectedNode, EntrySchemaVersion.latest());
      }
    }
    List<ReferencesValues> references = null;
    JsonNode referenceNode = node.get(REFERENCES_KEY);
    if (referenceNode != null && !referenceNode.isNull()) {
      references = readReferences(referenceNode);
    }
    Optional<DatabaseSpecificValue> databaseSpecific = readDatabaseSpecific(
        node.get(DATABASE_SPECIFIC_KEY));

    Entry.EntryBuilder builder = Entry.builder(id, modified)
        .schemaVersion(schemaVersion.orElse(null))
        .published(published.orElse(null))
        .withdrawn(withdrawn.orElse(null))
        .aliases(aliases.orElse(null))
        .related(related.orElse(null))
        .summary(summary.orElse(null))
        .details(details.orElse(null))
        .databaseSpecific(databaseSpecific.orElse(null));
    if (affected != null) {
      builder.affected(affected.toArray(new AffectedValues[0]));
    }

    if (references != null) {
      builder.references(references.toArray(new ReferencesValues[0]));
    }
    return builder.build();
  }

  private EntryDeserializer(Class<?> vc) {
    super(vc);
  }

  private static IdValue readId(JsonNode idNode) {
    return IdValue.fromString(idNode.asText());
  }

  private static ModifiedValue readModified(JsonNode modifiedNode) {
    Instant instant = readInstant(modifiedNode);
    return ModifiedValue.of(instant);
  }

  private static Optional<SchemaVersionValue> readSchemaVersion(JsonNode schemaVersion) {
    if (isEmptyJsonNode(schemaVersion)) {
      return Optional.empty();
    }
    return Optional.of(SchemaVersionValue.fromString(schemaVersion.asText()));
  }

  private static Optional<AliasesValue> readAliases(JsonNode aliasesNode) {
    if (isEmptyJsonNode(aliasesNode)) {
      return Optional.empty();
    }
    List<IdValue> ids = readMultipleIds(aliasesNode);
    if (ids.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(AliasesValue.of(ids.toArray(new IdValue[0])));
    }
  }

  private static Optional<RelatedValue> readIdAggregate(JsonNode idAggregateNode) {
    if (isEmptyJsonNode(idAggregateNode)) {
      return Optional.empty();
    }
    List<IdValue> ids = readMultipleIds(idAggregateNode);
    if (ids.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(RelatedValue.of(ids.toArray(new IdValue[0])));
    }
  }

  private static List<IdValue> readMultipleIds(JsonNode aliasesNode) {
    List<IdValue> ids = new ArrayList<>();
    if (aliasesNode.isArray()) {
      for (final JsonNode idNote : aliasesNode) {
        ids.add(readId(idNote));
      }
    }
    return ids;
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

  private static Optional<DatabaseSpecificValue> readDatabaseSpecific(JsonNode databaseSpecific) {
    if (isEmptyJsonNode(databaseSpecific)) {
      return Optional.empty();
    }
    return Optional.of(DatabaseSpecificValue.fromString(databaseSpecific.asText()));
  }

  private static boolean isEmptyJsonNode(JsonNode relatedNode) {
    return relatedNode == null || relatedNode.isNull();
  }

  private static Instant readInstant(JsonNode jsonNode) {
    return Instant.parse(jsonNode.asText());
  }
}
