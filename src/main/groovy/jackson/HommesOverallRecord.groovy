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
package jackson

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema

def file = getClass().classLoader.getResource('HommesStageWinners.csv').file as File

@JsonCreator
record Cyclist(
        @JsonProperty('stage') int stage,
        @JsonProperty('firstname') String first,
        @JsonProperty('lastname') String last,
        @JsonProperty('team') String team,
        @JsonProperty('country') String country) {
    String full() { "$first $last" }
}

def schema = CsvSchema.emptySchema().withHeader()
def mapper = new CsvMapper().readerFor(Cyclist).with(schema)
file.withReader { r ->
    List<Cyclist> records = mapper.readValues(r).readAll()
    assert records.size() == 21
    assert records*.full().toSet().size() == 15
    assert records*.team.toSet().size() == 10
    assert records*.country.toSet().size() == 9
}
