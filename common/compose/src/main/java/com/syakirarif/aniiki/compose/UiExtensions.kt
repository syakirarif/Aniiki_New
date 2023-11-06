package com.syakirarif.aniiki.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Spacer Extension
@Composable
fun Int.spacer() = Spacer(modifier = Modifier.height(this.dp))