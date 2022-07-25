package jackson

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser

def data = [['place', 'firstname', 'lastname', 'team'],
            ['1', 'Lorena', 'Wiebes', 'TeamDSM'],
            ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
            ['3', 'Lotte', 'Kopecky', 'Team SD Worx']]

def file = File.createTempFile('FemmesStage1Podium', '.csv')

file.withWriter { w ->
    new CsvMapper().writeValue(w, data)
}

def mapper = new CsvMapper().readerForListOf(String).with(CsvParser.Feature.WRAP_AS_ARRAY)
file.withReader { r ->
    assert mapper.readValues(r).readAll() == data
}
