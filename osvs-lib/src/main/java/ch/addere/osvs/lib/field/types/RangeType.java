package ch.addere.osvs.lib.field.types;

/** It specifies the type of version range being recorded and defines the interpretation of them. */
public enum RangeType {

  /** The versions are semantic versions as defined by SemVer 2.0.0, with no leading “v” prefix. */
  ECOSYSTEM,

  /**
   * The versions are full-length Git commit hashes. The repository’s commit graph is needed to
   * evaluate whether a given version is in the range.
   */
  GIT,

  /** The versions are semantic versions as defined by SemVer 2.0.0, with no leading “v” prefix. */
  SEMVER
}
