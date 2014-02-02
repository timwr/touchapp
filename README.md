

Simple app to allow you to control your google glass from your Android phone.

Setup

1. Enable USB debugging on the glass (Settings -> Device info -> Turn on debug)
2. Plug in the glass 
3. adb install glass.apk
4. adb shell ime set com.headsup.glass/.GlassInputService

