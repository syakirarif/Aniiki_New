/*
 * Designed and developed by 2021 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.syakirarif.aniiki.compose.custom

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow

inline fun <T> LazyGridScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    threshold: Int = 4,
    pageSize: Int = 20,
    crossinline fetch: () -> Unit,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    val currentIndex = currentIndexFlow.value

    itemsIndexed(items) { index, item ->

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
            fetch()
        }
    }
}