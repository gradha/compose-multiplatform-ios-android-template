import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable fun MainView(vm: PseudoViewModel) = App(vm)
