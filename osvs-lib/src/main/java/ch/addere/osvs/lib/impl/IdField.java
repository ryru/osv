package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.Id;

public final class IdField implements Id {

  private final String id;

  public IdField(String id) {
    this.id = id;
  }

  @Override
  public String getKey() {
    return "id";
  }

  @Override
  public String getValue() {
    return id;
  }

}
