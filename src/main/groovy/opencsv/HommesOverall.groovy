package opencsv

import com.opencsv.CSVReaderHeaderAware

def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File

file.withReader { r ->
    def rows = []
    def reader = new CSVReaderHeaderAware(r)
    while ((next = reader.readMap())) rows << next
    assert rows.size() == 21
    assert rows.collect { it.firstname + ' ' + it.lastname }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
