import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi

interface ProxyNavigator {
    fun openNext()
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComposeApp(proxyNavigator: ProxyNavigator) {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = { proxyNavigator.openNext() }) {
                Text("Open actual test")
            }
        }
    }
}

expect fun getPlatformName(): String

@Composable
fun FirstScreen(proxyNavigator: ProxyNavigator) {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = { proxyNavigator.openNext() }) {
                Text("Open second screen")
            }

            LaunchedEffect(Unit) {
                println("Launching First screen effect")
            }

            DisposableEffect(Unit) {
                println("Entry point of First screen disposable effect")
                onDispose { println("Disposing First Screeen!!!!! Bye bye!!!") }
            }
        }
    }
}

@Composable
fun SecondScreen() {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("This is the end, you can go back now")
        }
    }
}
