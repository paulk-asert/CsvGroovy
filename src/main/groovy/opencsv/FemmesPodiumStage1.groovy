package opencsv

import com.opencsv.CSVReader
import com.opencsv.CSVWriter

def data = [
        ['place', 'firstname', 'lastname', 'team'],
        ['1', 'Lorena', 'Wiebes', 'Team DSM'],
        ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
        ['3', 'Lotte', 'Kopecky', 'Team SD Worx']
]

def file = File.createTempFile('FemmesStage1Podium', '.csv')

file.withWriter { w ->
    new CSVWriter(w).writeAll(data.collect{ it as String[] })
}

file.withReader {r ->
    assert new CSVReader(r).readAll() == data
}
