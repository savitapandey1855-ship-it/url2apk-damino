# Damino - Android App

WebView-based Android app wrapping https://damino3getsocial.edgeone.dev

## Features
- Loading progress bar (Instagram pink)
- Animated pull-to-refresh with colorful spinner
- Push notifications (Firebase FCM)
- Exit confirmation (press back again to exit)
- Pink+yellow gradient logo with letter "D"

## Build
The GitHub Actions workflow builds the APK automatically on push to main.
You can also trigger it manually from the Actions tab.

## Push Notifications
1. Place your `google-services.json` in `app/`
2. Place your `firebase-service-account.json` in the project root
3. Run: `python send_notification.py --title "Test" --body "Hello!" --topic all`
