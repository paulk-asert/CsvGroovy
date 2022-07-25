def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File
//def file = new File('HommesStageWinners.csv')
def rows = file.readLines().tail()*.split(',')
int total = rows.size()
Set names = rows.collect { it[1] + ' ' + it[2] }
Set teams = rows*.getAt(3)
Set countries = rows*.getAt(4)
String result = "Across $total stages, ${names.size()} riders from " +
        "${teams.size()} teams and ${countries.size()} countries won stages."
assert result == 'Across 21 stages, 15 riders from 10 teams and 9 countries won stages.'
