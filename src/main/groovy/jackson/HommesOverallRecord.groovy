package jackson

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema

def file = new File('D:/projects/CsvGroovy/HommesStageWinners.csv')

@JsonCreator
record Cyclist(
        @JsonProperty('stage') int stage,
        @JsonProperty('firstname') String first,
        @JsonProperty('lastname') String last,
        @JsonProperty('team') String team,
        @JsonProperty('country') String country) {
    String full() { "$first $last" }
}

def schema = CsvSchema.emptySchema().withHeader()
def mapper = new CsvMapper().readerFor(Cyclist).with(schema)
file.withReader { r ->
    List<Cyclist> records = mapper.readValues(r).readAll()
    assert records.size() == 21
    assert records*.full().toSet().size() == 15
    assert records*.team.toSet().size() == 10
    assert records*.country.toSet().size() == 9
}
