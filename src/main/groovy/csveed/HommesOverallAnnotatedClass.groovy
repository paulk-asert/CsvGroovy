/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package csveed

import groovy.transform.CompileStatic
import groovy.transform.stc.POJO
import org.csveed.annotations.CsvCell
import org.csveed.annotations.CsvFile
import org.csveed.annotations.CsvIgnore
import org.csveed.bean.BeanReaderImpl
import org.csveed.bean.ColumnNameMapper

@CsvFile(mappingStrategy = ColumnNameMapper, separator = ',')
@CompileStatic
@POJO
class Rider {
    @CsvIgnore
    String stage
    @CsvCell(columnName = 'firstname')
    String first
    @CsvCell(columnName = 'lastname')
    String last
    String team
    String country
    String getFull() { "$first $last" }
}

def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File

file.withReader { r ->
    List<Rider> records = new BeanReaderImpl<>(r, Rider).readBeans()
    assert records.size() == 21
    assert records*.full.toSet().size() == 15
    assert records*.team.toSet().size() == 10
    assert records*.country.toSet().size() == 9
}
