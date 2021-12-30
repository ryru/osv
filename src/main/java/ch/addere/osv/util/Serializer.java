package ch.addere.osv.util;

import ch.addere.osv.Entry;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;

/**
 * JSON serializer.
 */
class Serializer {

  private Serializer() {
  }

  /**
   * Serialize an open source vulnerability Entry to JSON.
   *
   * @param entry open source vulnerability entry
   * @return JSON serialization of an Entry
   * @throws IOException an exception
   */
  public static String toJson(Entry entry) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule(
        "EntrySerializer",
        new Version(1, 0, 0, null, null, null));
    module.addSerializer(Entry.class, new EntrySerializer());
    mapper.registerModule(module);
    return mapper.writeValueAsString(entry);
  }

}
