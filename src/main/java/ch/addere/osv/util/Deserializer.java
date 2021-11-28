package ch.addere.osv.util;

import ch.addere.osv.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * JSON deserializer.
 */
class Deserializer {

  /**
   * Deserialize JSON representation of an open source vulnerability entry.
   *
   * @param json data of an open source vulnerability
   * @return Entry deserialized from the JSON data
   * @throws OsvParserException an exception
   */
  public static Entry fromJson(String json) throws OsvParserException {
    final ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule(
        "EntryDeserializer",
        new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Entry.class, new EntryDeserializer());
    mapper.registerModule(module);
    try {
      return mapper.readValue(json, Entry.class);
    } catch (JsonProcessingException e) {
      throw new OsvParserException("deserialization error", e);
    }
  }
}
