import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class PseudoViewModel {

    val _state = MutableStateFlow(1)
    val state = _state.asStateFlow()

    private var mJob: Job? = null

    init {
        _state.monitorSubscriptionCountIn(GlobalScope,
            onStartBeingObserved = {
                mJob = GlobalScope.launch {
                    println("Beginning to observe")
                    while (true) {
                        delay(2_000L)
                        _state.update { it + 1 }
                        println("Updated ticker to ${_state.value}")
                    }
                }

            },
            onStopBeingObserved = {
                mJob?.cancel()
                println("Finished observing")
            })
    }
}

fun <T> MutableStateFlow<T>.monitorSubscriptionCountIn(
    scope: CoroutineScope,
    onStartBeingObserved: () -> Unit,
    onStopBeingObserved: () -> Unit,
) {
    subscriptionCount
        .map { count -> count > 0 } // map count into active/inactive flag
        .distinctUntilChanged() // only react to true<->false changes
        .drop(1) // ignore the initial state
        .onEach { isActive -> // configure an action
            if (isActive) onStartBeingObserved() else onStopBeingObserved()
        }
        .launchIn(scope) // launch it
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(vm: PseudoViewModel) {
    MaterialTheme {
        val state = vm.state.collectAsState()
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }
            Text("Counter ${state.value}")
        }
    }
}

expect fun getPlatformName(): String