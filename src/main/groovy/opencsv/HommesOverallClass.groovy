package opencsv

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvToBeanBuilder

def file = new File('D:/projects/CsvGroovy/HommesStageWinners.csv')

class Cyclist {
    @CsvBindByName(column = 'firstname')
    String first
    @CsvBindByName(column = 'lastname')
    String last
    @CsvBindByName
    String team
    @CsvBindByName
    String country
}

file.withReader {r ->
    List<Cyclist> rows = new CsvToBeanBuilder(r).withType(Cyclist).build().parse()
    assert rows.size() == 21
    assert rows.collect{ it.first + ' ' + it.last }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
