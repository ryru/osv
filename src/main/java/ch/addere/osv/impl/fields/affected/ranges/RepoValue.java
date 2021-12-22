package ch.addere.osv.impl.fields.affected.ranges;

import ch.addere.osv.fields.Value;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Repository the events refers to.
 */
public final class RepoValue implements Value<URL> {

  public static final String REPO_KEY = "repo";

  private final URL value;

  private RepoValue(URL value) {
    Objects.requireNonNull(value, "argument value must not be null");
    this.value = value;
  }

  /**
   * Create a Repo object.
   *
   * @param url a valid URL
   * @return valid Repo
   */
  public static RepoValue of(URL url) {
    return new RepoValue(url);
  }

  /**
   * Create RepoValue from a repo URL string.
   *
   * @param repoUrl String of a URL pointing to a repository
   * @return valid RepoValue
   */
  public static RepoValue fromString(String repoUrl) {
    try {
      return new RepoValue(new URL(repoUrl));
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("invalid URL: '" + repoUrl + "'", e);
    }
  }

  @Override
  public URL value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RepoValue repo = (RepoValue) o;
    return Objects.equals(value, repo.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return REPO_KEY + ": " + value.toString();
  }
}
