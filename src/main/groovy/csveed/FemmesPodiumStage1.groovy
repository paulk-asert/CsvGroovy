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

import org.csveed.row.RowInstructionsImpl
import org.csveed.row.RowReaderImpl
import org.csveed.row.RowWriterImpl

def data = [
        ['place', 'firstname', 'lastname', 'team'],
        ['1', 'Lorena', 'Wiebes', 'Team DSM'],
        ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
        ['3', 'Lotte', 'Kopecky', 'Team SD Worx']
]

def file = File.createTempFile('FemmesStage1Podium', '.csv')
file.deleteOnExit()

def instructions = new RowInstructionsImpl(useHeader: false, separator: ',')
file.withWriter { w ->
    new RowWriterImpl(w, instructions).writeRows(data as String[][])
}

file.withReader {r ->
    assert new RowReaderImpl(r, instructions).readRows()*.toList() == data
}
