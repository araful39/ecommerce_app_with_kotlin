# bdSkills_app - দোকান App

Simple shop app built with Jetpack Compose.

Flow: Splash → Login / Register → Home (Products) → Details + Logout.

## Features

- **Splash Screen** – App logo `দোকান App`, auto-navigates to Login after 2 sec.
- **Login / Register** – Username + password with validation. Empty submit shows `Validation Error` dialog.
- **Home Screen** – Category banner, `Products` list (6 sample products), click any card to open Details.
- **Product Card** – Reusable card: name + short description on left, price (৳) on right.
- **Details Screen** – Shows product name, price, description, `Add to Cart` button (placeholder), `← Back` button.
- **Image Picker (Camera + Gallery)** – Common component:
  - Gallery via `GetContent("image/*")`
  - Camera via `TakePicturePreview()` with runtime `CAMERA` permission handling
  - Preview shown with Coil / `Image`
- **Logout** – TopBar `Logout` button (Home only) with confirm dialog, clears backstack to Login.
- **Common Components** – `CommonButton`, `CommonTextField`, `AuthScreenLayout`, `ScreenTitle`, `SectionTitle`, `ValidationErrorDialog`, `ConfirmDialog`, `ImagePicker` — reused across screens.

## Tech Stack

Kotlin, Jetpack Compose Material3, Navigation-Compose, Coil, minSdk 24, targetSdk 37.

## Project Structure

```
com.example.bdskills_app
├── MainActivity.kt              // setContent { MyApp() }
├── navigation/NavGraph.kt       // 5 routes: splash, login, register, home, details
├── data/Product.kt              // Product data class + sampleProducts (6)
├── ui/screens/                  // Splash, Login, Register, Home, Details
├── ui/components/               // ProductCard, ImagePicker, CommonComponents
└── ui/theme/                    // Theme, Color, Type
```

## How to Run

1. Open in Android Studio.
2. `Run > Run app` on emulator or phone.
3. Test: empty login → error dialog, login with any text → Home, card click → Details, Home TopBar → Logout.
