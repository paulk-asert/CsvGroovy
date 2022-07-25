package commons

import static org.apache.commons.csv.CSVFormat.RFC4180

def file = new File('D:/projects/CsvGroovy/HommesStageWinners.csv')

file.withReader { r ->
    def rows = RFC4180.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build()
            .parse(r)
            .records
    assert rows.size() == 21
    assert rows.collect { it.firstname + ' ' + it.lastname }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
