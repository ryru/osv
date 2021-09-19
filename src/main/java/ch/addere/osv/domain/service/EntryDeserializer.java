package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Aliases.ALIASES;
import static ch.addere.osv.domain.model.fields.Details.DETAILS;
import static ch.addere.osv.domain.model.fields.Id.ID;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Aliases;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
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

    JsonNode idNode = node.get(ID);
    Id id = Id.create(trimJson(idNode.toString()));

    JsonNode modifiedNode = node.get(MODIFIED);
    Modified modified = Modified.create(trimJson(modifiedNode.toString()));

    JsonNode aliasesNode = node.get(ALIASES);
    List<Id> ids = new ArrayList<>();
    if (aliasesNode.isArray()) {
      for (final JsonNode objNode : aliasesNode) {
        ids.add(Id.create(trimJson(objNode.toString())));
      }
    }
    Aliases aliases = new Aliases(ids);
    //Aliases aliases = Aliases.create(trimJson(aliasesNode.toString()));

    JsonNode publishedNode = node.get(PUBLISHED);
    Published published = Published.create(trimJson(publishedNode.toString()));

    JsonNode withdrawnNode = node.get(WITHDRAWN);
    Withdrawn withdrawn = Withdrawn.create(trimJson(withdrawnNode.toString()));

    JsonNode summaryNode = node.get(SUMMARY);
    Summary summary = new Summary(trimJson(summaryNode.toString()));

    JsonNode detailsNode = node.get(DETAILS);
    Details details = Details.create(trimJson(detailsNode.toString()));

    return Entry.builder(id, modified)
        .published(published)
        .withdrawn(withdrawn)
        .aliases(aliases)
        .summary(summary)
        .details(details)
        .build();
  }

  private static String trimJson(String json) {
    return json.trim().replace("\"", "");
  }
}
