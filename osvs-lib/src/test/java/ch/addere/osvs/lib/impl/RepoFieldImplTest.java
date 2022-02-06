package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.RepoField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepoFieldImplTest {

  private static URL URL_1;
  private static URL URL_2;

  @BeforeAll
  static void init() throws MalformedURLException {
    URL_1 = new URL("https://osv.dev");
    URL_2 = new URL("https://github.com");
  }

  @Test
  void testOfRepoFieldNull() {
    assertThatThrownBy(() -> RepoField.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument url must not be null");
  }

  @Test
  void testKeyValue() {
    RepoField repo = RepoField.of(URL_1);
    assertThat(repo)
        .satisfies(
            r -> {
              assertThat(r.getKey()).isEqualTo("repo");
              assertThat(r.getValue()).isEqualTo(URL_1);
            });
  }

  @Test
  void testEquality() {
    RepoField repo1 = RepoField.of(URL_1);
    RepoField repo2 = RepoField.of(URL_1);
    assertThat(repo1)
        .satisfies(
            r -> {
              assertThat(r).isEqualTo(repo1);
              assertThat(r).isEqualTo(repo2);
            });
  }

  @Test
  void testNonEquality() {
    RepoField repo1 = RepoField.of(URL_1);
    RepoField repo2 = RepoField.of(URL_2);
    assertThat(repo1)
        .satisfies(
            r -> {
              assertThat(r).isNotEqualTo(null);
              assertThat(r).isNotEqualTo(repo2);
            });
  }

  @Test
  void testHashCode() {
    RepoField repo1 = RepoField.of(URL_1);
    RepoField repo2 = RepoField.of(URL_1);
    assertThat(repo1).hasSameHashCodeAs(repo2);
  }

  @Test
  void testToString() {
    RepoField repo = RepoField.of(URL_1);
    assertThat(repo).hasToString("repo: https://osv.dev");
  }
}
