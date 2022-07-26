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
package flatpack

import net.sf.flatpack.DelimiterParser
import net.sf.flatpack.writer.DelimiterWriterFactory
import net.sf.flatpack.writer.WriterOptions

def data = [
        ['place', 'firstname', 'lastname', 'team'],
        ['1', 'Lorena', 'Wiebes', 'Team DSM'],
        ['2', 'Marianne', 'Vos', 'Team Jumbo Visma'],
        ['3', 'Lotte', 'Kopecky', 'Team SD Worx']
]

def file = File.createTempFile('FemmesStage1Podium', '.csv')
file.deleteOnExit()

char SEP = ','
char QT = '"'
def header = data[0]
def options = WriterOptions.instance.autoPrintHeader(false)
def factory = new DelimiterWriterFactory(SEP, QT).addColumnTitles(header)
factory.createWriter(file.newWriter(), options).withCloseable { writer ->
    writer.printHeader()
    (1..<data.size()).each { row ->
        (0..<header.size()).each { col ->
            writer.addRecordEntry(header[col], data[row][col])
        }
        writer.nextRecord()
    }
}

def result = []
file.withReader {r ->
    def records = new DelimiterParser(r, SEP, QT, false).parse()
    result << records.columns
    while(records.next()) {
        result << header.collect{ records.getString(it) }
    }
}
assert result == data
