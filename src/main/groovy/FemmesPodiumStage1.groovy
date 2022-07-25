def data = [
        ['place', 'firstname', 'lastname', 'team'],
        ['1', 'Lorena', 'Wiebes', 'Team DSM'],
        ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
        ['3', 'Lotte', 'Kopecky', 'Team SD Worx']
]

def file = File.createTempFile('FemmesStage1Podium', '.csv')

file.text = data*.join(',').join(System.lineSeparator())

assert file.readLines()*.split(',') == data
