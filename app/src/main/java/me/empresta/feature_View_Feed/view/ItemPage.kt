package me.empresta.feature_View_Feed.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import me.empresta.feature_View_Feed.view.model.AvailableItem

@Composable
fun ItemPage(item: AvailableItem) {
    // UI code for the item page
    // Use the item parameter to display the item details, such as name, description, etc.
    // You can also apply any necessary styling or modifiers to the UI elements

    // Example code:
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = item.name, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
    }
}
