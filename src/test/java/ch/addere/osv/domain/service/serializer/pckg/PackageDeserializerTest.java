package ch.addere.osv.domain.service.serializer.pckg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import ch.addere.osv.domain.service.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class PackageDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("package node is not a valid node");
  }

  @Test
  void testValidMinimalGoPackage() throws JsonProcessingException, OsvParserException {
    Package pckg = Package.of(Ecosystem.Go, Name.of("crypto/elliptic"));
    Package deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }

  @Test
  void testValidGoPackage() throws JsonProcessingException, OsvParserException {
    Package pckg = Package.of(Ecosystem.Go, Name.of("crypto/elliptic"), Purl.of("https://osv.dev"));
    Package deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\",\"purl\":\"https://osv.dev\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }

  private static Package deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return PackageDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
