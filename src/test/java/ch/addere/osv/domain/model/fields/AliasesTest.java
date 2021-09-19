package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Id.Database.GO;
import static ch.addere.osv.domain.model.fields.Id.Database.OSV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class AliasesTest {

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(() -> new Aliases(List.of()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("aliases must not be empty");
  }

  @Test
  void testValidArugment() {
    var id1 = new Id(GO, "anEntryId");
    var alias = new Aliases(List.of(id1));
    assertThat(alias).satisfies(a -> {
      assertThat(a.aliases().size()).isEqualTo(1);
      assertThat(a.aliases().get(0)).isEqualTo(id1);
    });
  }

  @Test
  void testValidArugments() {
    var id1 = new Id(GO, "anEntryId");
    var id2 = new Id(OSV, "anotherEntryId");
    var alias = new Aliases(List.of(id1, id2));
    assertThat(alias).satisfies(a -> {
      assertThat(a.aliases().size()).isEqualTo(2);
      assertThat(a.aliases().get(0)).isEqualTo(id1);
      assertThat(a.aliases().get(1)).isEqualTo(id2);
    });
  }
}