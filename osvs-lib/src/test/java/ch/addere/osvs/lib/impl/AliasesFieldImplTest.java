package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.AliasesField;
import ch.addere.osvs.lib.field.IdField;
import org.junit.jupiter.api.Test;

import static ch.addere.osvs.lib.field.types.IdDb.GO;
import static ch.addere.osvs.lib.field.types.IdDb.OSV;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AliasesFieldImplTest {

  private static final IdField ID_1 = IdField.of(GO, "id1");
  private static final IdField ID_2 = IdField.of(GO, "id2");

  @Test
  void testOfIdFieldsNull() {
    assertThatThrownBy(() -> AliasesField.of((IdField[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument idFields must not be null");
  }

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(AliasesFieldImpl::of)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("value must not be empty");
  }

  @Test
  void testOfValueObjectCreation() {
    AliasesField aliases1 = AliasesField.of(IdField.of(GO, "enEntryId"));
    AliasesField aliases2 = AliasesField.of(aliases1.getValue().toArray(new IdField[0]));
    assertThat(aliases1).isEqualTo(aliases2);
  }

  @Test
  void testKeyValue() {
    IdField id = IdField.of(GO, "anEntryId");
    AliasesField alias = AliasesField.of(id);
    assertThat(alias)
        .satisfies(
            a -> {
              assertThat(a.getKey()).isEqualTo("aliases");
              assertThat(a.getValue()).hasSize(1);
              assertThat(a.getValue().get(0)).isEqualTo(id);
            });
  }

  @Test
  void testKeyValues() {
    var id1 = IdField.of(GO, "anEntryId");
    var id2 = IdField.of(OSV, "anotherEntryId");
    var alias = AliasesField.of(id1, id2);
    assertThat(alias)
        .satisfies(
            a -> {
              assertThat(a.getKey()).isEqualTo("aliases");
              assertThat(a.getValue()).hasSize(2);
              assertThat(a.getValue().get(0)).isEqualTo(id1);
              assertThat(a.getValue().get(1)).isEqualTo(id2);
            });
  }

  @Test
  void testEquality() {
    AliasesField aliases1 = AliasesField.of(ID_1, ID_2);
    AliasesField aliases2 = AliasesField.of(ID_1, ID_2);
    assertThat(aliases1)
        .satisfies(
            i -> {
              assertThat(i).isEqualTo(aliases1);
              assertThat(i).isEqualTo(aliases2);
            });
  }

  @Test
  void testNonEquality() {
    AliasesField aliases1 = AliasesField.of(ID_1, ID_2);
    AliasesField aliases2 = AliasesField.of(ID_2);
    assertThat(aliases1)
        .satisfies(
            i -> {
              assertThat(i).isNotEqualTo(null);
              assertThat(i).isNotEqualTo(aliases2);
            });
  }

  @Test
  void testHashCode() {
    AliasesField aliases1 = AliasesField.of(ID_1, ID_2);
    AliasesField aliases2 = AliasesField.of(ID_1, ID_2);
    assertThat(aliases1).hasSameHashCodeAs(aliases2);
  }

  @Test
  void testToString() {
    AliasesField aliases = AliasesField.of(ID_1, ID_2);
    assertThat(aliases)
        .hasToString("aliases: [ " + join(", ", ID_1.toString(), ID_2.toString()) + " ]");
  }
}
