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

import org.csveed.bean.BeanInstructionsImpl
import org.csveed.bean.BeanReaderImpl
import org.csveed.bean.ColumnNameMapper

class Cyclist {
    String stage
    String first
    String last
    String team
    String country
}

def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File

def instructions = new BeanInstructionsImpl(Cyclist)
    .ignoreProperty('metaClass')
    .setSeparator(',' as char)
    .mapColumnNameToProperty('firstname', 'first')
    .mapColumnNameToProperty('lastname', 'last')
    .mapColumnNameToProperty('team', 'team')
    .mapColumnNameToProperty('country', 'country')
    .setMapper(ColumnNameMapper)

file.withReader { r ->
    List<Cyclist> records = new BeanReaderImpl<>(r, instructions).readBeans()
    assert records.size() == 21
    assert records.collect { it.first + ' ' + it.last }.toSet().size() == 15
    assert records*.team.toSet().size() == 10
    assert records*.country.toSet().size() == 9
}
