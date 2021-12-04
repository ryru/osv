package ch.addere.osv.impl.fields.affected.ranges;

import ch.addere.osv.fields.affected.ranges.Repo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Repository the events refers to.
 */
public final class RepoImpl implements Repo {

  public static final String REPO_KEY = "repo";

  private final URL url;

  private RepoImpl(String url) {
    Objects.requireNonNull(url, "argument url must not be null");
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("invalid URL: '" + url + "'", e);
    }
  }

  /**
   * Create a Repo object.
   *
   * @param url a valid URL
   * @return valid Repo
   */
  public static RepoImpl of(String url) {
    return new RepoImpl(url);
  }

  @Override
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
    RepoImpl repo = (RepoImpl) o;
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
