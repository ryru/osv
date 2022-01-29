package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.validation.Validation;
import com.github.packageurl.MalformedPackageURLException;
import com.github.packageurl.PackageURL;

import java.util.function.Predicate;

public final class PackagePurlFieldValidation extends Validation<PackagePurlFieldImpl> {

  public PackagePurlFieldValidation(PackagePurlFieldImpl value) {
    super(value);

    this.addRuleForValidation(validPurlString(), "purl is not valid package URL");
  }

  private Predicate<PackagePurlFieldImpl> validPurlString() {
    try {
      new PackageURL(getValue().getValue());
      return v -> true;
    } catch (MalformedPackageURLException e) {
      return v -> false;
    }
  }
}
