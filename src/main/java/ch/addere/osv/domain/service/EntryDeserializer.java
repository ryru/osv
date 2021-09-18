package ch.addere.osv.domain.service;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.Withdrawn;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

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

    JsonNode idNode = node.get("id");
    Id id = Id.create(trimJson(idNode.toString()));

    JsonNode modifiedNode = node.get("modified");
    Modified modified = Modified.create(trimJson(modifiedNode.toString()));

    JsonNode publishedNode = node.get("published");
    Published published = Published.create(trimJson(publishedNode.toString()));

    JsonNode withdrawnNode = node.get("withdrawn");
    Withdrawn withdrawn = Withdrawn.create(trimJson(withdrawnNode.toString()));

    return Entry.builder(id, modified)
        .published(published)
        .withdrawn(withdrawn)
        .build();
  }

  private static String trimJson(String json) {
    return json.trim().replace("\"", "");
  }
}
