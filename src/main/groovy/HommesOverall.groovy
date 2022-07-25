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


def byValueDesc = { -it.value }
def bySize = { k, v -> [k, v.size()] }
def isMultiple = { it.value > 1 }
def multipleWins = { Closure select -> rows
    .groupBy(select)
    .collectEntries(bySize)
    .findAll(isMultiple)
    .sort(byValueDesc)
    .entrySet()
    .join(', ')
}
println 'Multiple wins by country:\n' + multipleWins{ it[4] }
println 'Multiple wins by rider:\n' + multipleWins{ it[1] + ' ' + it[2] }
println 'Multiple wins by team:\n' + multipleWins{ it[3] }
