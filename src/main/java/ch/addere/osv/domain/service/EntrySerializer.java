package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Details.DETAILS;
import static ch.addere.osv.domain.model.fields.Id.ID;
import static ch.addere.osv.domain.model.fields.IdAggregate.ALIASES;
import static ch.addere.osv.domain.model.fields.IdAggregate.RELATED;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.IdAggregate;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.List;

/**
 * Serializer for open source vulnerabilities.
 */
public class EntrySerializer extends StdSerializer<Entry> {

  protected EntrySerializer() {
    this(null);
  }

  protected EntrySerializer(Class<Entry> t) {
    super(t);
  }

  @Override
  public void serialize(Entry entry, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField(ID, writeId(entry.id()));
    if (entry.published().isPresent()) {
      gen.writeStringField(PUBLISHED, writePublished(entry.published().get()));
    }
    gen.writeStringField(MODIFIED, writeModified(entry.modified()));
    if (entry.aliases().isPresent()) {
      writeAliases(entry, gen);
    }
    if (entry.withdrawn().isPresent()) {
      writeRelated(entry, gen);
    }
    if (entry.withdrawn().isPresent()) {
      gen.writeStringField(WITHDRAWN, writeWithdrawn(entry.withdrawn().get()));
    }
    if (entry.summary().isPresent()) {
      gen.writeStringField(SUMMARY, writeSummary(entry.summary().get()));
    }
    if (entry.details().isPresent()) {
      gen.writeStringField(DETAILS, writeDetails(entry.details().get()));
    }
    gen.writeEndObject();
  }

  private static String writeId(Id id) {
    return id.database().name() + "-" + id.entryId();
  }

  private static String writePublished(Published published) {
    return published.published().toString();
  }

  private static String writeModified(Modified modified) {
    return modified.modified().toString();
  }

  private static void writeAliases(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(ALIASES);
    gen.writeStartArray();
    var ids = value.aliases().map(IdAggregate::ids).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static void writeRelated(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(RELATED);
    gen.writeStartArray();
    var ids = value.related().map(IdAggregate::ids).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static String writeWithdrawn(Withdrawn withdrawn) {
    return withdrawn.withdrawn().toString();
  }

  private static String writeSummary(Summary summary) {
    return summary.summary();
  }

  private static String writeDetails(Details details) {
    return details.details().replace("\\n", "\n");
  }
}
