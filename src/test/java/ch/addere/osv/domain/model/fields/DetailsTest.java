package ch.addere.osv.domain.model.fields;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DetailsTest {

  @Test
  void testDescriptionToString() {
    var details = new Details("some details");
    var detailDescription = details.toString();
    assertThat(detailDescription).isEqualTo(details.toString());
  }
}