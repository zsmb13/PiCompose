package co.zsmb.picompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.zsmb.picompose.data.ScoreKeeper
import co.zsmb.picompose.screens.HighScores
import co.zsmb.picompose.screens.Home
import co.zsmb.picompose.ui.theme.PiPracticeComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Too lazy to add Jetpack App Startup
        ScoreKeeper.init(applicationContext)

        setContent {
            PiPracticeComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            Home(navController)
                        }
                        composable("highscores") {
                            HighScores(navController)
                        }
                    }
                }
            }
        }
    }
}
