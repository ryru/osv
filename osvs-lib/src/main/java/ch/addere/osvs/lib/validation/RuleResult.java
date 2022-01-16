package ch.addere.osvs.lib.validation;

public final class RuleResult {

  private final boolean isValid;
  private final String violationMsg;

  public RuleResult(boolean isValid, String violationMsg) {
    this.isValid = isValid;
    this.violationMsg = violationMsg;
  }

  public static RuleResult valid() {
    return new RuleResult(true, "all good");
  }

  public static RuleResult invalid(String violationMsg) {
    return new RuleResult(false, violationMsg);
  }

  public boolean isValid() {
    return isValid;
  }

  public String getViolationMsg() {
    return violationMsg;
  }
}
