package commons


import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter

import static org.apache.commons.csv.CSVFormat.RFC4180

def data = [
        ['place', 'firstname', 'lastname', 'team'],
        ['1', 'Lorena', 'Wiebes', 'Team DSM'],
        ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
        ['3', 'Lotte', 'Kopecky', 'Team SD Worx']
]

def file = File.createTempFile('FemmesStage1Podium', '.csv')

file.withWriter { w ->
    new CSVPrinter(w, CSVFormat.DEFAULT).printRecords(data)
}

file.withReader {r ->
    assert RFC4180.parse(r).records*.toList() == data
}
