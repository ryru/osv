package ch.addere.osvs.lib.field;


import ch.addere.osvs.lib.impl.IdField;

public interface Id extends EntryField<String> {

  static Id of(String id) {
    return new IdField(id);
  }
}
