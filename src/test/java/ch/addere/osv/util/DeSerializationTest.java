package ch.addere.osv.util;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import ch.addere.osv.Entry;
import java.io.IOException;
import java.nio.file.Path;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

class DeSerializationTest {

  private static final String EMPTY_FILE = "";
  private static final String EMPTY_JSON = "{}";
  private static final String MINIMAL_OSV = "{\"id\":\"GO-2021-99998\","
      + "\"modified\":\"2021-03-10T23:20:53Z\"}";
  private static final String GO_EXAMPLE_PATH = "src/test/resources/go-example.json";
  private static final String GO_TOOL_EXAMPLE_PATH = "src/test/resources/go-tool-example.json";
  private static final String NPM_EXAMPLE_PATH = "src/test/resources/npm-example.json";
  private static final String OSV_EXAMPLE_PATH = "src/test/resources/osv-example.json";
  private static final String RUST_EXAMPLE_PATH = "src/test/resources/rust-example.json";
  private static final String PYTHON_EXAMPLE_PATH = "src/test/resources/python-example.json";
  private static final String RUBY_EXAMPLE_PATH = "src/test/resources/ruby-example.json";

  @Test
  void testInvalidEmptyFile() {
    assertThatThrownBy(() -> deserializeSerialize(EMPTY_FILE))
        .isInstanceOf(OsvParserException.class)
        .hasMessage("deserialization error");
  }

  @Test
  void testInvalidEmptyJson() {
    assertThatThrownBy(() -> deserializeSerialize(EMPTY_JSON))
        .isInstanceOf(OsvParserException.class)
        .hasMessage("deserialization error");
  }

  @Test
  void testMinimalDeSerialization() throws IOException, JSONException {
    String serialized = deserializeSerialize(MINIMAL_OSV);
    assertEquals(MINIMAL_OSV, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testGoExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(GO_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testGoToolExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(GO_TOOL_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testNpmExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(NPM_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testOsvExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(OSV_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testRustExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(RUST_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testPythonExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(PYTHON_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  @Test
  void testRubyExampleDeSerialization() throws IOException, JSONException {
    String jsonData = readJsonFile(RUBY_EXAMPLE_PATH);
    String serialized = deserializeSerialize(jsonData);
    assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
  }

  private static String deserializeSerialize(String jsonData) throws IOException {
    Entry deserialized = Deserializer.fromJson(jsonData);
    return Serializer.toJson(deserialized);
  }

  private static String readJsonFile(String path) {
    var fileName = Path.of(path);
    try {
      return normalizeJson(readString(fileName));
    } catch (IOException e) {
      return null;
    }
  }

  private static String normalizeJson(String json) {
    return json.replace("\n", "").replace(" ", "");
  }
}
