package ch.addere.osv.util;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.Entry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.json.JSONException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class DeSerializationTest {

  private static final String EMPTY_FILE = "";
  private static final String EMPTY_JSON = "{}";
  private static final String MINIMAL_OSV = "{\"id\":\"GO-2021-99998\","
      + "\"modified\":\"2021-03-10T23:20:53Z\"}";
  private static final String GO_OSV_PATH = "src/test/resources/go-osv.json";
  private static final String GO_EXAMPLE_PATH = "src/test/resources/go-example.json";
  private static final String GO_TOOL_EXAMPLE_PATH = "src/test/resources/go-tool-example.json";
  private static final String NPM_EXAMPLE_PATH = "src/test/resources/npm-example.json";
  private static final String OSV_EXAMPLE_PATH = "src/test/resources/osv-example.json";
  private static final String RUST_EXAMPLE_PATH = "src/test/resources/rust-example.json";
  private static final String PYTHON_EXAMPLE_PATH = "src/test/resources/python-example.json";
  private static final String RUBY_EXAMPLE_PATH = "src/test/resources/ruby-example.json";

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private static Stream<String> nonParsableOpenSourceVulnerabilitiesReports() {
    return Stream.of(
        EMPTY_FILE,
        EMPTY_JSON
    );
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private static Stream<String> parsableOpenSourceVulnerabilitiesReports() {
    return Stream.of(
        MINIMAL_OSV,
        readJsonFile(GO_OSV_PATH),
        readJsonFile(GO_EXAMPLE_PATH),
        readJsonFile(GO_TOOL_EXAMPLE_PATH),
        readJsonFile(NPM_EXAMPLE_PATH),
        readJsonFile(OSV_EXAMPLE_PATH),
        readJsonFile(RUST_EXAMPLE_PATH),
        readJsonFile(PYTHON_EXAMPLE_PATH),
        readJsonFile(RUBY_EXAMPLE_PATH)
    );
  }

  @ParameterizedTest
  @MethodSource("nonParsableOpenSourceVulnerabilitiesReports")
  void testInvalidOpenSourceVulnerabilityReport(String jsonData) {
    assertThatThrownBy(() -> deserializeSerialize(jsonData))
        .isInstanceOf(OsvParserException.class)
        .hasMessage("deserialization error");
  }

  @ParameterizedTest
  @MethodSource("parsableOpenSourceVulnerabilitiesReports")
  void testValidOpenSourceVulnerabilityReport(String jsonData) throws IOException, JSONException {
    String serialized = deserializeSerialize(jsonData);
    JSONAssert.assertEquals(jsonData, serialized, JSONCompareMode.STRICT);
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
