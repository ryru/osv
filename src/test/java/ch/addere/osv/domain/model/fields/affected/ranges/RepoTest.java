package ch.addere.osv.domain.model.fields.affected.ranges;

import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;
import org.junit.jupiter.api.Test;

class RepoTest {

  private static final String VALID_URL = "https://osv.dev";
  private static final String INVALID_URL = "not-a-valid-url";
  private static final String OTHER_VALID_URL = "https://github.com";

  @Test
  void testJsonKey() {
    assertThat(REPO_KEY).isEqualTo("repo");
  }

  @Test
  void testValidRepoValue() throws MalformedURLException {
    Repo repo = Repo.of(VALID_URL);
    String url = repo.value();
    assertThat(url).isEqualTo(VALID_URL);
  }

  @Test
  void testInvalidRepoValue() {
    assertThatThrownBy(() -> Repo.of(INVALID_URL))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid URL: '" + INVALID_URL + "'");
  }

  @Test
  void testSameness() {
    Repo repo = Repo.of(VALID_URL);
    Repo otherRepo = repo;
    assertThat(repo).isEqualTo(otherRepo);
  }

  @Test
  void testEquality() {
    Repo repo = Repo.of(VALID_URL);
    Repo otherRepo = Repo.of(VALID_URL);
    assertThat(repo).isEqualTo(otherRepo);
  }

  @Test
  void testNonEquality() {
    Repo repo = Repo.of(VALID_URL);
    Repo otherRepo = Repo.of(OTHER_VALID_URL);
    assertThat(repo).isNotEqualTo(otherRepo);
  }

  @Test
  void testHashCode() {
    Repo repo = Repo.of(VALID_URL);
    Repo otherRepo = Repo.of(VALID_URL);
    assertThat(repo.hashCode()).isEqualTo(otherRepo.hashCode());
  }

  @Test
  void testToString() {
    Repo repo = Repo.of(VALID_URL);
    assertThat(repo.toString()).isEqualTo(REPO_KEY + ": " + VALID_URL);
  }
}
