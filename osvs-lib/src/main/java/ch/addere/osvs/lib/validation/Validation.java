package ch.addere.osvs.lib.validation;

import ch.addere.osvs.lib.field.types.SchemaVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static ch.addere.osvs.lib.validation.RuleResult.invalid;
import static ch.addere.osvs.lib.validation.RuleResult.valid;

public abstract class Validation<T> {

  private SchemaVersion version = SchemaVersion.latest();
  private final T value;
  private final List<Rule<T>> rules = new ArrayList<>();

  protected Validation(T value) {
    this.value = value;
  }

  protected Validation(T value, SchemaVersion version) {
    this(value);
    this.version = version;
  }

  public T getValue() {
    return value;
  }

  protected void addRuleForValidation(Predicate<T> predicate, String msgIfNotTrue) {
    rules.add(new Rule<>(version.toString(), predicate, msgIfNotTrue));
  }

  public RuleResult validate() {
    for (Rule<T> rule : rules) {
      if (!rule.getPredicate().test(value)) {
        return invalid(rule.getViolationMsg());
      }
    }
    return valid();
  }
}
