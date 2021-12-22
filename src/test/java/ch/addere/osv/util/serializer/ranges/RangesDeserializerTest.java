package ch.addere.osv.util.serializer.ranges;

import static ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.impl.fields.affected.ranges.RepoValue;
import ch.addere.osv.impl.fields.affected.ranges.TypeEcosystemImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeGitImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeSemVerImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RangesDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String SEM_VER = "1.2.3";

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("ranges node is not an array node");
  }

  @Test
  void testInvalidTypeInput() {
    assertThatThrownBy(() -> deserialize("[{\"type\":\"invalidType\"}]")).isInstanceOf(
            OsvParserException.class)
        .hasMessageContaining("type 'invalidType' is not valid");
  }

  @Test
  void testValidMinimalSemVerRange() throws JsonProcessingException, OsvParserException {
    Ranges range = semVerRange(null);
    List<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"SEMVER\",\"events\":[{\"introduced\":\"1.2.3\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidSemVerRangeWithRepo()
      throws JsonProcessingException, OsvParserException {
    Ranges range = semVerRange(getRepo());
    List<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"SEMVER\",\"repo\":\"https://test.repo\",\"events\":[{\"introduced\":\"1.2.3\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidMinimalGitRange()
      throws JsonProcessingException, OsvParserException {
    Ranges range = gitRange();
    List<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"GIT\",\"repo\":\"https://test.repo\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testInvalidRepoUrl() {
    assertThatThrownBy(() -> deserialize(
        "[{\"type\":\"GIT\",\"repo\":\"not-a-valid-url\",\"events\":[{\"introduced\":\"0\"}]}]"))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("invalid repo url: ");
  }

  @Test
  void testValidMinimalEcosystemRange() throws JsonProcessingException, OsvParserException {
    Ranges range = ecosystemRange(null);
    List<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"ECOSYSTEM\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidEcosystemRangeWithRepo()
      throws JsonProcessingException, OsvParserException {
    Ranges range = ecosystemRange(getRepo());
    List<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"ECOSYSTEM\",\"repo\":\"https://test.repo\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  private static Ranges semVerRange(RepoValue repo) {
    return Optional.ofNullable(repo)
        .map(value -> TypeSemVerImpl.of(value, SemVerEvent.of(INTRODUCED, SEM_VER)))
        .orElseGet(() -> TypeSemVerImpl.of(SemVerEvent.of(INTRODUCED, SEM_VER)));
  }

  private static Ranges gitRange() {
    return TypeGitImpl.of(getRepo(), GitEvent.of(INTRODUCED, "0"));
  }

  private static Ranges ecosystemRange(RepoValue repo) {
    return Optional.ofNullable(repo).map(
            value -> TypeEcosystemImpl.of(value, EcosystemEvent.of(INTRODUCED, "0")))
        .orElseGet(() -> TypeEcosystemImpl.of(EcosystemEvent.of(INTRODUCED, "0")));
  }

  private static RepoValue getRepo() {
    return RepoValue.fromString("https://test.repo");
  }

  private static List<Ranges> deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return RangesDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
