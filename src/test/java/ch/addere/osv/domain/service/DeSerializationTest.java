package ch.addere.osv.domain.service;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DeSerializationTest {

  private static final String EMPTY_FILE = "";
  private static final String EMPTY_JSON = "{}";
  private static final String MINIMAL_OSV = "{\"id\":\"GO-2021-99998\","
      + "\"modified\":\"2021-03-10T23:20:53Z\"}";
  private static final String GO_OSV_PATH = "src/test/resources/go-osv.json";

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
        readJsonFile(GO_OSV_PATH)
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
  void testValidOpenSourceVulnerabilityReport(String jsonData) throws IOException {
    String serialized = deserializeSerialize(jsonData);
    assertThat(serialized).isEqualTo(jsonData);
  }

  private static String deserializeSerialize(String jsonData) throws IOException {
    var deserialized = Deserializer.fromJson(jsonData);
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
