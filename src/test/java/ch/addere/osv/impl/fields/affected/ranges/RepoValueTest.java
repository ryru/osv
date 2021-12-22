package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.impl.fields.affected.ranges.RepoValue.REPO_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;

class RepoValueTest {

  private static final String VALID_URL = "https://osv.dev";
  private static final String INVALID_URL = "not-a-valid-url";
  private static final String OTHER_VALID_URL = "https://github.com";

  @Test
  void testJsonKey() {
    assertThat(REPO_KEY).isEqualTo("repo");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> RepoValue.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument value must not be null");
  }

  @Test
  void testOfValueObjectCreation() throws MalformedURLException {
    RepoValue repo1 = RepoValue.of(new URL(VALID_URL));
    RepoValue repo2 = RepoValue.of(repo1.value());
    assertThat(repo1).isEqualTo(repo2);
  }

  @Test
  void testValidRepoValue() throws MalformedURLException {
    RepoValue repo = RepoValue.of(new URL(VALID_URL));
    String url = repo.value().toString();
    assertThat(url).isEqualTo(VALID_URL);
  }

  @Test
  void testInvalidRepoValue() throws MalformedURLException {
    assertThatThrownBy(() -> RepoValue.fromString(INVALID_URL))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid URL: '" + INVALID_URL + "'");
  }

  @Test
  void testEquality() throws MalformedURLException {
    RepoValue repo = RepoValue.of(new URL(VALID_URL));
    RepoValue otherRepo = RepoValue.of(new URL(VALID_URL));
    assertThat(repo).satisfies(r -> {
      assertThat(r).isEqualTo(repo);
      assertThat(r).isEqualTo(otherRepo);
    });
  }

  @Test
  void testNonEquality() throws MalformedURLException {
    RepoValue repo = RepoValue.of(new URL(VALID_URL));
    RepoValue otherRepo = RepoValue.of(new URL(OTHER_VALID_URL));
    assertThat(repo).satisfies(r -> {
      assertThat(r).isNotEqualTo(null);
      assertThat(r).isNotEqualTo(otherRepo);
    });
  }

  @Test
  void testHashCode() throws MalformedURLException {
    RepoValue repo = RepoValue.of(new URL(VALID_URL));
    RepoValue otherRepo = RepoValue.of(new URL(VALID_URL));
    assertThat(repo).hasSameHashCodeAs(otherRepo);
  }

  @Test
  void testToString() throws MalformedURLException {
    RepoValue repo = RepoValue.of(new URL(VALID_URL));
    assertThat(repo).hasToString(REPO_KEY + ": " + VALID_URL);
  }
}
