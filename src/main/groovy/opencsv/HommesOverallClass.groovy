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
package opencsv

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvToBeanBuilder

def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File

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

file.withReader { r ->
    List<Cyclist> rows = new CsvToBeanBuilder(r).withType(Cyclist).build().parse()
    assert rows.size() == 21
    assert rows.collect { it.first + ' ' + it.last }.toSet().size() == 15
    assert rows*.team.toSet().size() == 10
    assert rows*.country.toSet().size() == 9
}
