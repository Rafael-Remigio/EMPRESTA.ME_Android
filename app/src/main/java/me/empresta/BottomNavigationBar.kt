package me.empresta

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


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