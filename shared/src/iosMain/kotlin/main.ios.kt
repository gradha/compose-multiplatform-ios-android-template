import androidx.compose.ui.window.ComposeUIViewController

actual fun getPlatformName(): String = "iOS"

fun MainViewController(proxyNavigator: ProxyNavigator) =
    ComposeUIViewController { ComposeApp(proxyNavigator) }

fun FirstViewController(proxyNavigator: ProxyNavigator) =
    ComposeUIViewController { FirstScreen(proxyNavigator) }

fun SecondViewController() =
    ComposeUIViewController { SecondScreen() }