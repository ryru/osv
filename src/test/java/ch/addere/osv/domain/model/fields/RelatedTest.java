package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Id.Database.GO;
import static ch.addere.osv.domain.model.fields.Id.Database.OSV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class RelatedTest {

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(() -> new Related(List.of()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("related must not be empty");
  }

  @Test
  void testValidArugment() {
    var id1 = new Id(GO, "anEntryId");
    var related = new Related(List.of(id1));
    assertThat(related).satisfies(r -> {
      assertThat(r.related().size()).isEqualTo(1);
      assertThat(r.related().get(0)).isEqualTo(id1);
    });
  }

  @Test
  void testValidArugments() {
    var id1 = new Id(GO, "anEntryId");
    var id2 = new Id(OSV, "anotherEntryId");
    var related = new Related(List.of(id1, id2));
    assertThat(related).satisfies(r -> {
      assertThat(r.related().size()).isEqualTo(2);
      assertThat(r.related().get(0)).isEqualTo(id1);
      assertThat(r.related().get(1)).isEqualTo(id2);
    });
  }
}