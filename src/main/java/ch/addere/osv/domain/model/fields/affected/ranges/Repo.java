package ch.addere.osv.domain.model.fields.affected.ranges;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Repository the events refers to.
 */
public final class Repo {

  public static final String REPO_KEY = "repo";

  private final URL url;

  private Repo(URL url) {
    this.url = url;
  }

  /**
   * Create a Repo object.
   *
   * @param url a valid URL
   * @return valid Repo
   */
  public static Repo of(String url) {
    try {
      return new Repo(new URL(url));
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("invalid URL: '" + url + "'", e);
    }
  }

  public String value() {
    return url.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Repo repo = (Repo) o;
    return Objects.equals(url, repo.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  @Override
  public String toString() {
    return REPO_KEY + ": " + url.toString();
  }
}
