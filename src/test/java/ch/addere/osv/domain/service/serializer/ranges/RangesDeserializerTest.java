package ch.addere.osv.domain.service.serializer.ranges;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.ranges.Repo;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeEcosystem;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeGit;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeSemVer;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.GitEvent;
import ch.addere.osv.domain.model.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.domain.service.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RangesDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();

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
    Set<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"SEMVER\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidSemVerRangeWithRepo()
      throws JsonProcessingException, OsvParserException, MalformedURLException {
    Ranges range = semVerRange(getRepo());
    Set<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"SEMVER\",\"repo\":\"https://test.repo\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidMinimalGitRange()
      throws JsonProcessingException, OsvParserException, MalformedURLException {
    Ranges range = gitRange();
    Set<Ranges> deserializedRange = deserialize(
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
    Set<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"ECOSYSTEM\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  @Test
  void testValidEcosystemRangeWithRepo()
      throws JsonProcessingException, OsvParserException, MalformedURLException {
    Ranges range = ecosystemRange(getRepo());
    Set<Ranges> deserializedRange = deserialize(
        "[{\"type\":\"ECOSYSTEM\",\"repo\":\"https://test.repo\",\"events\":[{\"introduced\":\"0\"}]}]");
    assertThat(deserializedRange).containsExactly(range);
  }

  private static Ranges semVerRange(Repo repo) {
    return Optional.ofNullable(repo)
        .map(value -> TypeSemVer.of(value, SemVerEvent.of(EventSpecifier.introduced, "0")))
        .orElseGet(() -> TypeSemVer.of(SemVerEvent.of(EventSpecifier.introduced, "0")));
  }

  private static Ranges gitRange() throws MalformedURLException {
    return TypeGit.of(getRepo(), GitEvent.of(EventSpecifier.introduced, "0"));
  }

  private static Ranges ecosystemRange(Repo repo) {
    return Optional.ofNullable(repo).map(
            value -> TypeEcosystem.of(value, EcosystemEvent.of(EventSpecifier.introduced, "0")))
        .orElseGet(() -> TypeEcosystem.of(EcosystemEvent.of(EventSpecifier.introduced, "0")));
  }

  private static Repo getRepo() throws MalformedURLException {
    return Repo.of("https://test.repo");
  }

  private static Set<Ranges> deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return RangesDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
