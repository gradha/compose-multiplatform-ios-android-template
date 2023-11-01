import UIKit
import SwiftUI
import shared


struct ContentView: View {
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(destination: MainView()) {
                    Text("Open the actual test")
                }
            }
        }
    }
}

class FirstNavigator: ProxyNavigator {
    weak var vc: UIViewController?

    func openNext() {
        vc?.navigationController?.pushViewController(
            Main_iosKt.SecondViewController(), animated: true
        )
    }
}

class MainNavigator: ProxyNavigator {
    weak var vc: UIViewController?

    func openNext() {
        let firstNavigator = FirstNavigator()
        let result = Main_iosKt.FirstViewController(proxyNavigator: firstNavigator)
        firstNavigator.vc = result
        vc?.navigationController?.pushViewController(result, animated: true)
    }
}

struct MainView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewControllerType {

        let mainNavigator = MainNavigator()
        let result = Main_iosKt.MainViewController(proxyNavigator: mainNavigator)
        mainNavigator.vc = result
        return result
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}


