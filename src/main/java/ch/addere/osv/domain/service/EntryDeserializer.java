package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Aliases.ALIASES;
import static ch.addere.osv.domain.model.fields.Details.DETAILS;
import static ch.addere.osv.domain.model.fields.Id.Database.valueOf;
import static ch.addere.osv.domain.model.fields.Id.ID;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED;
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

    Id id = readId(node.get(ID));
    Modified modified = readModified(node.get(MODIFIED));
    Aliases aliases = readAliases(node.get(ALIASES));
    Published published = readPublished(node.get(PUBLISHED));
    Withdrawn withdrawn = readWithdrawn(node.get(WITHDRAWN));
    Summary summary = readSummary(node.get(SUMMARY));
    Details details = readDetails(node.get(DETAILS));

    return Entry.builder(id, modified)
        .published(published)
        .withdrawn(withdrawn)
        .aliases(aliases)
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

  private static Aliases readAliases(JsonNode aliasesNode) {
    List<Id> ids = new ArrayList<>();
    if (aliasesNode.isArray()) {
      for (final JsonNode idNote : aliasesNode) {
        ids.add(readId(idNote));
      }
    }
    return new Aliases(ids);
  }

  private static Published readPublished(JsonNode publishedNode) {
    Instant instant = readInstant(publishedNode);
    return new Published(instant);
  }

  private static Withdrawn readWithdrawn(JsonNode withdrawnNode) {
    Instant instant = readInstant(withdrawnNode);
    return new Withdrawn(instant);
  }

  private static Summary readSummary(JsonNode summary) {
    return new Summary(trimJson(summary.toString()));
  }

  private static Details readDetails(JsonNode details) {
    return new Details(trimJson(details.toString()));
  }

  private static Instant readInstant(JsonNode jsonNode) {
    return Instant.parse(trimJson(jsonNode.toString()));
  }

  private static String trimJson(String json) {
    return json.trim().replace("\"", "");
  }
}
