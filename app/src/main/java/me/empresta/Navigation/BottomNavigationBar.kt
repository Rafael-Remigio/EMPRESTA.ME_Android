package me.empresta.Navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import me.empresta.BrightOrange
import me.empresta.Grey
import me.empresta.White


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)
{


}

@Composable
fun BottomBar(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
    )

{
    val backStackEntry = navController.currentBackStackEntryAsState();
    BottomNavigation(modifier = modifier, backgroundColor = Grey, elevation = 5.dp)
    {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(selected = selected,
                onClick = {onItemClick(item)},
                selectedContentColor = BrightOrange,
                unselectedContentColor = White,
                icon={
                    Icon(imageVector = item.icon,contentDescription = item.name)
                }
            )
        }
    }

}