package ch.addere.osv.util;

import static java.nio.file.Files.readString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import ch.addere.osv.Entry;
import java.io.IOException;
import java.nio.file.Path;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

class SerializationTest {

  private static final String MINIMAL_OSV = "{\"id\":\"GO-2021-99998\","
      + "\"modified\":\"2021-03-10T23:20:53Z\"}";
  private static final String GO_EXAMPLE_PATH = "src/test/resources/go-example.json";
  private static final String GO_TOOL_EXAMPLE_PATH = "src/test/resources/go-tool-example.json";
  private static final String NPM_EXAMPLE_PATH = "src/test/resources/npm-example.json";
  private static final String OSV_EXAMPLE_PATH = "src/test/resources/osv-example.json";
  private static final String PYTHON_EXAMPLE_PATH = "src/test/resources/python-example.json";
  private static final String RUBY_EXAMPLE_PATH = "src/test/resources/ruby-example.json";
  private static final String RUST_EXAMPLE_PATH = "src/test/resources/rust-example.json";

  private static Entry deserialize(String jsonData) throws OsvParserException {
    return Deserializer.fromJson(jsonData);
  }

  private static String serialize(Entry entry) throws IOException {
    return Serializer.toJson(entry);
  }

  private static String readJsonFile(String path) {
    var fileName = Path.of(path);
    try {
      return readString(fileName);
    } catch (IOException e) {
      return null;
    }
  }

  @Test
  void testMinimalSerialization() throws IOException, JSONException {
    Entry entry = deserialize(MINIMAL_OSV);
    String json = serialize(entry);
    assertEquals(MINIMAL_OSV, json, JSONCompareMode.STRICT);
  }

  @Test
  void testGoExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(GO_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(GO_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testGoToolExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(GO_TOOL_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(GO_TOOL_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testNpmExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(NPM_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(NPM_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testOsvExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(OSV_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(OSV_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testPythonExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(PYTHON_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(PYTHON_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testRubyExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(RUBY_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(RUBY_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }

  @Test
  void testRustExampleSerialization() throws IOException, JSONException {
    Entry entry = deserialize(readJsonFile(RUST_EXAMPLE_PATH));
    String json = serialize(entry);
    assertEquals(readJsonFile(RUST_EXAMPLE_PATH), json, JSONCompareMode.STRICT);
  }
}
