package ch.addere.osv.domain.service;

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
    gen.writeStringField("id", value.id().toJson());
    gen.writeStringField("published", value.published().toJson());
    gen.writeStringField("modified", value.modified().toJson());
    gen.writeStringField("withdrawn", value.withdrawn().toJson());
    gen.writeStringField("details", value.details().toJson());
    gen.writeEndObject();
  }
}
