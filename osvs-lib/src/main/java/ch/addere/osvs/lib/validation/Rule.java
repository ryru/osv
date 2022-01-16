package ch.addere.osvs.lib.validation;

import java.util.function.Predicate;

public final class Rule<T> {

  private final String version;
  private final Predicate<T> predicate;
  private final String violationMsg;

  public Rule(String version, Predicate<T> predicate, String violationMsg) {
    this.version = version;
    this.predicate = predicate;
    this.violationMsg = violationMsg;
  }

  public Predicate<T> getPredicate() {
    return predicate;
  }

  public String getViolationMsg() {
    return "validation error: for schema version " + version + " " + violationMsg;
  }
}
