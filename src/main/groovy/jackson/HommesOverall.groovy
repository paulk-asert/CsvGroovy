package jackson

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema

def file = new File('D:/projects/CsvGroovy/HommesStageWinners.csv')

def schema = CsvSchema.emptySchema().withHeader()
def mapper = new CsvMapper().readerForMapOf(String).with(schema)
file.withReader { r ->
    def rows = mapper.readValues(r).readAll()
    assert rows.size() == 21
    assert rows.collect { it.firstname + ' ' + it.lastname }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
