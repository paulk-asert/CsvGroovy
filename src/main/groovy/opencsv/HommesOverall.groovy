package opencsv

import com.opencsv.CSVReaderHeaderAware

def file = new File('D:/projects/CsvGroovy/HommesStageWinners.csv')

file.withReader {r ->
    def rows = []
    def reader = new CSVReaderHeaderAware(r)
    while ((next = reader.readMap()) != null) { rows << next }
    assert rows.size() == 21
    assert rows.collect{ it.firstname + ' ' + it.lastname }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
