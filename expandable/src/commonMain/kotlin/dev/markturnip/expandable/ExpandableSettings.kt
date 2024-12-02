package dev.markturnip.expandable

import androidx.compose.ui.unit.Dp

data class ExpandableSettings(
    val minimizedHeight: Dp,
    val maximizedHeight: Dp,
    val bottomPadding: Dp,
    val expandedBottomPadding: Dp,
    val cornerRadius: Dp
)
