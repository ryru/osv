package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackagePurlField;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

import java.util.Objects;

public final class PackagePurlFieldImpl implements PackagePurlField {

  private static final String JSON_KEY = "purl";

  private final String purl;

  private PackagePurlFieldImpl(String purl) {
    Objects.requireNonNull(purl, "argument purl must not be null");

    this.purl = purl;

    Validation<PackagePurlFieldImpl> validator = new PackagePurlFieldValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  public static PackagePurlField of(String purl) {
    return new PackagePurlFieldImpl(purl);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public String getValue() {
    return purl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PackagePurlFieldImpl that = (PackagePurlFieldImpl) o;

    return purl.equals(that.purl);
  }

  @Override
  public int hashCode() {
    return purl.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
