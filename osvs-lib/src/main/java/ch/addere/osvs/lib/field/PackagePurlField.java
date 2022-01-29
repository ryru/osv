package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.PackagePurlFieldImpl;

/**
 * The purl field is a string following the <a
 * href="https://github.com/package-url/purl-spec">Package URL specification</a> that identifies the
 * package.
 */
public interface PackagePurlField extends EntryField<String> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param purl package url
   * @return valid {@code PackagePurlField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static PackagePurlField of(String purl) {
    return PackagePurlFieldImpl.of(purl);
  }
}
