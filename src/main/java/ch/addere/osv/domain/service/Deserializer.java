package ch.addere.osv.domain.service;

import ch.addere.osv.domain.model.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;

/**
 * JSON deserializer.
 */
public class Deserializer {

  /**
   * Deserialize JSON representation of an open source vulnerability entry.
   *
   * @param json data of an open source vulnerability
   * @return Entry deserialized from the JSON data
   * @throws ParserException an exception
   */
  public static Entry fromJson(@NonNull String json) throws ParserException {
    final ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule(
        "EntryDeserializer",
        new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Entry.class, new EntryDeserializer());
    mapper.registerModule(module);
    try {
      return mapper.readValue(json, Entry.class);
    } catch (JsonProcessingException e) {
      throw new ParserException("deserialization error");
    }
  }
}
