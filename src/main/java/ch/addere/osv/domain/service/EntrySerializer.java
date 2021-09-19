package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Details.DETAILS;
import static ch.addere.osv.domain.model.fields.Id.ID;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN;

import ch.addere.osv.domain.model.Entry;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

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
  public void serialize(Entry value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField(ID, value.id().toJson());
    gen.writeStringField(PUBLISHED, value.published().toJson());
    gen.writeStringField(MODIFIED, value.modified().toJson());
    gen.writeStringField(WITHDRAWN, value.withdrawn().toJson());
    gen.writeStringField(SUMMARY, value.summary().toJson());
    gen.writeStringField(DETAILS, value.details().toJson());
    gen.writeEndObject();
  }
}
