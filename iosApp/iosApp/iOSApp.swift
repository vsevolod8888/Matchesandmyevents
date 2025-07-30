import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
@UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
  init() {
        DiHelper().startKoinIos()

    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        return .portrait
    }
}