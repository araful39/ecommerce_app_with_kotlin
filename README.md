# bdSkills_app - দোকান App | A to Z Guide + YouTube Tutorial Script

> Jetpack Compose দিয়ে বানানো ছোট Shop App। Splash -> Login / Register -> Home (Product List) -> Details + Logout।

## 1. App টা কিভাবে কাজ করে - A to Z Flow

```
SPLASH (2 sec)
  -> LOGIN --username/password--> HOME
  -> REGISTER --username/password--> HOME
  HOME (6টা Product list)
  -> click যেকোনো Product -> DETAILS (name/price/description)
  HOME TopBar -> Logout -> confirm Dialog -> LOGIN
```

**Demo করলে যা দেখা যাবে:**
1. App open হলে `দোকান App` Splash 2 সেকেন্ড থাকে।
2. Login Page আসে। খালি রেখে Login চাপলে `Validation Error` Dialog আসে।
3. Username + Password দিয়ে Login চাপলে Home আসে।
4. `Do[build.gradle.kts](app/build.gradle.kts)n't have an account? Register` চাপলে Register Page, সেখান থেকে আবার Home যাওয়া যায়।
5. Home এ 6টা Product: Smartphone 25000৳, Headphones 3500৳, Smart Watch 4500৳, Backpack 1800৳, Shoes 3200৳, Speaker 2200৳।
6. যেকোনো Card এ click করলে Details Page: বড় Title, Price, Description, `Add to Cart` Button, `← Back` Button।
7. Home এর TopBar এ `Logout` চাপলে `Are you sure?` Dialog, Yes করলে Login এ ফেরত, BackStack clear।

## 2. Project Structure

```
com.example.bdskills_app
├── MainActivity.kt                 // Entry point: setContent { MyApp() }
├── navigation/NavGraph.kt          // MyApp(), NavHost, Scaffold+TopAppBar, 5টা route, Logout Dialog
├── data/Product.kt                 // data class Product + sampleProducts list (6টা)
├── ui/screens/
│   ├── SplashScreen.kt             // delay(2000) -> login
│   ├── LoginScreen.kt              // validation + navigate home/register
│   ├── RegisterScreen.kt           // Login এর মতোই, navigate home/login
│   ├── HomeScreen.kt               // LazyColumn + ProductCard loop
│   └── DetailsScreen.kt            // productName/price/description args দেখায়
├── ui/components/
│   └── ProductCard.kt              // Card + Row: নাম-বর্ণনা বামে, দাম ডানে
├── ui/theme/                       // Theme.kt, Color.kt, Type.kt
AndroidManifest.xml                  // MainActivity = LAUNCHER
app/build.gradle.kts                 // compose=true, navigation-compose, material3, minSdk 24
res/layout/fff.xml                   // পুরনো test XML, এখন ব্যবহার হয় না (নিচে Note দেখো)
```

**Tech:** Kotlin, Jetpack Compose Material3, Navigation-Compose, `rememberNavController()`

## 3. File by File কাজ

### `MainActivity.kt` (16 line)
```kotlin
setContent { MyApp() }
```
App এর দরজা। `onCreate` এ Compose UI দেখায়। মনে রেখো: XML চাইলে `setContentView`, Compose চাইলে `setContent` — এক Activity তে দুইটা একসাথে না।

### `navigation/NavGraph.kt` - `MyApp()`
- `rememberNavController()` = পথপ্রদর্শক
- `Scaffold(topBar)` = উপরে `দোকান App` Title সব পেজে, শুধু `splash` এ না: `if(currentRoute != "splash")`
- `NavHost(startDestination="splash")` এ 5টা route:
  - `splash`, `login`, `register`, `home`, `details/{productName}/{price}/{description}`
- Logout Dialog: `showLogoutDialog` true হলে `AlertDialog`, Logout চাপলে `navigate("login"){ popUpTo(0){inclusive=true} }` = history clear।

### `SplashScreen.kt`
`LaunchedEffect + delay(2000)` -> `navigate("login"){ popUpTo("splash"){inclusive=true} }`। UI: `Box fillMaxSize primary color`, মাঝে `দোকান App 36sp Bold`।

### `LoginScreen.kt` + `RegisterScreen.kt` (কোড 95% same)
- `username,password by remember { mutableStateOf("") }`
- 2টা `OutlinedTextField`, Password এ `PasswordVisualTransformation()`
- Button: `if(isNotBlank) navigate("home"){popUpTo inclusive=true} else showErrorDialog=true`
- নিচে `TextButton` দিয়ে login <-> register যাওয়া-আসা।

### `data/Product.kt`
```kotlin
data class Product(id:Int, name:String, price:Int, description:String)
val sampleProducts = listOf(6টা...)
```

### `HomeScreen.kt`
`Column padding 16.dp` + `Products` Title + `LazyColumn(spacedBy 12.dp)` + `items(sampleProducts){ ProductCard(onClick={ navigate("details/...") }) }`

### `ui/components/ProductCard.kt`
`Card(elevation 4.dp).clickable{}` ভেতরে `Row SpaceBetween`: বামে `Column(weight 1f)` নাম Bold + description Gray maxLines=1, ডানে দাম `25000৳` primary Bold।

### `DetailsScreen.kt`
Args: `productName:String, price:Int, description:String`। UI: `TextButton("← Back"){popBackStack()}`, Title `headlineLarge`, Price `primary Bold`, Description `bodyLarge`, নিচে `Button Add to Cart (fillMaxWidth)` - এখন empty, পরে Cart logic বসবে।

### `fff.xml` Note (গুরুত্বপূর্ণ)
`app/src/main/res/layout/fff.xml` এখন **ব্যবহার হয় না**। এটা পুরনো test file (ভেতরে 5টা Button)। App 100% Compose চলে, তাই XML change করলে App এ দেখা যাবে না। চাইলে delete করে দিতে পারো, কোনো effect পড়বে না। আগে `setContentView + setContent` দুইটা একসাথে থাকায় confusion হয়েছিল — এখন শুধু `setContent { MyApp() }` আছে।

## 4. কিভাবে Run / Test করবে?
1. Android Studio তে `Run > Run app` (emulator / ফোনে)।
2. Splash 2 sec -> Login দেখো।
3. খালি Login চেপে Error Dialog দেখো।
4. `user/1234` দিয়ে Login -> Home 6টা Card দেখো।
5. Card চাপো -> Details দাম-description দেখো -> Back আসো।
6. TopBar Logout -> Confirm -> Login এ ফেরত।
7. Test homework: `Product.kt` এ নতুন product যোগ করে rebuild করো, Home এ দেখা যাবে।

---

## 5. YouTube Tutorial Script - Part to Part (Full Voiceover + Screen Direction)

**Series Title:** দোকান App A to Z - Jetpack Compose Bangla Tutorial
**Playlist:** 10 Part | প্রতিটা 5-8 মিনিট | Total ~60 মিনিট
**Language:** Bangla (মুখের কথা) + English (কোড)
**Target:** Beginner যারা Android Studio install করে রেখেছে

### ভিডিও ফরম্যাট (প্রতিটা Part এ follow করো)
```
[0:00 HOOK] -> [0:30 SCREEN DEMO] -> [2:00 CODE EXPLAIN] -> [শেষে RECAP + NEXT + CTA]
CTA: "বুঝতে সমস্যা হলে comment করো, Part X এ দেখা হচ্ছে"
```

---
### PART 1: Intro + Full App Demo [5-6 min] - Goal: দর্শককে আটকানো

**[Screen: Emulator Full Screen, App Icon এ click করো]**
**HOOK - Bolo:**
> "মাত্র 60 মিনিটে তুমি নিজের দোকানের App বানাতে পারবা! Splash, Login, Product List, Details - সব Jetpack Compose দিয়ে। XML ছাড়াই। আমি পুরো App টা চালিয়ে দেখাচ্ছি, তারপর Part by Part কোড বুঝাবো।"

**[Screen: একেকটা Screen ঘুরে দেখাও]**
**Bolo:**
> "দেখো, App open করলাম। প্রথমে আসলো Splash - 'দোকান App, Welcome to your digital shop'। ২ সেকেন্ড পরে auto Login এ গেলো। আমি ইচ্ছা করে খালি Login চাপ দিলাম - দেখো Validation Error আসলো - 'Please enter both username and password'। এখন username দিলাম 'reami', password দিলাম '1234', Login চাপলাম - চলে আসলো Home - Products। ৬টা product - Smartphone 25000 টাকা, Headphone 3500... একটায় click করলাম - Details খুললো - নাম, দাম, description, Add to Cart button। Back চাপলাম - ফেরত আসলো। উপরে Logout চাপলাম - 'Are you sure?' - Yes - আবার Login এ। এই পুরো Flow টাই আমরা বানাবো। Part 2 তে Project Setup।"

**RECAP:** "আজ দেখলা Flow। মনে রাখো ৫টা পেজ: splash, login, register, home, details।"

---
### PART 2: Project Setup - build.gradle + Manifest + Folder [6-7 min]

**[Screen: Android Studio > New Project > Empty Activity (Compose) দেখাও]**
**Bolo:**
> "Part 2 তে setup। অনেকে ভুল template নেয় - XML ওয়ালা নিবা না। নিবা 'Empty Activity' যেটায় Compose লেখা থাকে। Package name দিবা `com.example.bdskills_app`, minSdk 24 রাখবা যাতে ৯০% ফোনে চলে।"

**[Screen: `app/build.gradle.kts` open করো]**
**Bolo:**
> "দেখো dependencies। `compose-bom`, `material3`, `activity-compose`, `navigation-compose`। এই last টা সবচেয়ে জরুরি - এটা ছাড়া এক পেজ থেকে আরেক পেজে যাওয়া যায় না। `buildFeatures { compose = true }` মানে Compose on। `compileSdk 37, targetSdk 37`। Sync Now চাপতে ভুলবা না।"

**[Screen: `AndroidManifest.xml`]**
**Bolo:**
> "এখানে MainActivity হলো LAUNCHER - মানে App icon এ click করলে এটাই খুলে। INTERNET permission আগে থেকে দেওয়া আছে, পরে API বসালে লাগবে।"

**[Screen: Project View > Folder ঘুরাও]**
**Bolo:**
> "Folder বুঝো: `navigation/NavGraph.kt` হলো ম্যানেজার, `data/Product.kt` হলো মাল, `ui/screens` এ ৫টা পেজ, `ui/components/ProductCard.kt` হলো একটা Card ডিজাইন, `ui/theme` হলো রং। এই structure মুখস্থ রাখো।"

---
### PART 3: MainActivity.kt - App এর দরজা [5 min]

**[Screen: `MainActivity.kt` - 16 line]**
```kotlin
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { MyApp() }
  }
}
```
**Bolo:**
> "এই ১৬ লাইনই পুরো App এর দরজা। onCreate মানে Activity জন্ম নেওয়ার সময়। setContent মানে Compose UI দেখাও। ভেতরে MyApp() - যেটা NavGraph এ আছে। बस শেষ।"

**[Screen: ভুল কোড দেখাও, cross চিহ্ন দাও]**
**Bolo:**
> "সবচেয়ে বড় ভুল: `setContentView(R.layout.fff)` + `setContent` দুইটা একসাথে লেখা। Android এ setContent পরে call হলে setContentView কে overwrite করে দেয়। তাই অনেকে fff.xml change করে দেখতে পায় না। Rule: XML চাইলে setContentView, Compose চাইলে setContent - যেকোনো একটা। আমাদের App 100% Compose, তাই শুধু setContent।"

---
### PART 4: Navigation - NavGraph.kt MyApp() [8-10 min] - সবচেয়ে গুরুত্বপূর্ণ

**[Screen: `NavGraph.kt` Full open]**
**Bolo:**
> "এটা App এর ম্যানেজার। না বুঝলে পুরো App অন্ধকার।"

> "১. `val navController = rememberNavController()` - এটা পথপ্রদর্শক। কে কোথায় যাবে সে মনে রাখে।"
> "২. `Scaffold(topBar = ...)` - উপরে 'দোকান App' bar। দেখো `if(currentRoute != splash)` - মানে Splash এ TopBar থাকবে না, বাকি সব পেজে থাকবে। actions এ `if(currentRoute == home)` - শুধু Home এ Logout button দেখাবে।"
> "৩. `NavHost(startDestination=splash)` - শুরু splash থেকে। ভেতরে ৫টা composable: splash, login, register, home, আর details।"
> "৪. Details route টা আলাদা: `details/{productName}/{price}/{description}` - মানে ৩টা data নিয়ে যাবে। navArgument দিয়ে type বলে দিছি - String, Int, String।"
> "৫. Logout Dialog: `showLogoutDialog` true হলে AlertDialog আসে। Confirm এ `navigate(login){ popUpTo(0){inclusive=true} }` - মানে পুরো history clear। যাতে Logout এর পর Back চাপলে Home এ ফেরত না যায়। এই popUpTo trick টা খাতায় লিখে রাখো।"

**Demo:** "চলো Home থেকে Logout করে Back চেপে দেখি - Login এই থাকে, প্রমাণ history clear।"

---
### PART 5: SplashScreen.kt [5 min]

**[Screen: `SplashScreen.kt`]**
```kotlin
LaunchedEffect(Unit){
  delay(2000)
  navController.navigate("login"){ popUpTo("splash"){inclusive=true} }
}
```
**Bolo:**
> "Splash মানে welcome gate। LaunchedEffect মানে Screen আসার সাথে সাথে কাজ শুরু। delay 2000 মানে ২ সেকেন্ড wait। তারপর login এ যাও + splash কে history থেকে delete। যাতে Back চাপলে splash না আসে। UI তে Box fillMaxSize primary color, মাঝে Column Center - 'দোকান App 36sp Bold', নিচে 'Welcome...' 16sp। চাইলে delay 5000 করে দেখো, তারপর 2000 করে দাও।"

---
### PART 6: LoginScreen.kt + RegisterScreen.kt [8 min]

**[Screen: দুই file split view এ দেখাও]**
**Bolo:**
> "দেখো দুইটা file ৯৫% same। একটা বুঝলে দুইটা বুঝবা।"

> "১. `var username by remember {mutableStateOf(\"\")}` - remember মানে টাইপ করলে মনে রাখে, UI update হয়।"
> "২. 2টা OutlinedTextField। Password এ `PasswordVisualTransformation()` - যাতে *** দেখায়।45212"
> "৩. Button onClick: `if(username.isNotBlank() && password.isNotBlank()) navigate(home){popUpTo(login) inclusive=true}` - মানে Login history থেকে মুছে Home যাও। else `showErrorDialog=true` - AlertDialog Validation Error।"
> "৪. নিচে TextButton 'Don't have an account? Register' - navigate(register)। Register এ উল্টা - 'Already have account? Login'।"

**[Screen: Emulator এ খালি Login চেপে Error দেখাও]**
> "দেখো proof।"

---
### PART 7: Data + HomeScreen + ProductCard [8-10 min]

**[Screen: `Product.kt`]**
```kotlin
data class Product(id:Int, name:String, price:Int, description:String)
```
**Bolo:**
> "data class মানে ছাঁচ। id, name, price, description। নিচে sampleProducts list এ ৬টা মাল - Smartphone 25000, Headphone 3500, Watch 4500, Backpack 1800, Shoes 3200, Speaker 2200। তুমি চাইলে ৭ নম্বরে নিজের নামে product যোগ করো - Home এ auto দেখাবে।"

**[Screen: `HomeScreen.kt`]**
**Bolo:**
> "Home এ Column padding 16.dp, উপরে Products Title Bold। তারপর LazyColumn - এটা Compose এর RecyclerView। verticalArrangement spacedBy 12.dp মানে Card এর মাঝে gap। items(sampleProducts) দিয়ে loop - প্রতিটার জন্য ProductCard বানায়। onClick এ navigate details with data।"

**[Screen: `ProductCard.kt`]**
**Bolo:**
> "Card elevation 4.dp - হালকা ছায়া। modifier clickable। ভেতরে Row SpaceBetween CenterVertically। বামে Column weight 1f - নাম Bold titleMedium, description Gray bodySmall maxLines 1 মানে বড় লেখা কেটে ... হবে। ডানে দাম titleLarge primary Bold - `25000৳`। মাঝে Spacer 12.dp gap। এই Card টাই বারবার reuse হচ্ছে - এটাই Compose এর মজা।"

---
### PART 8: DetailsScreen + Logout Proof [6 min]

**[Screen: `DetailsScreen.kt`]**
**Bolo:**
> "Details এ NavGraph থেকে ৩টা data আসে - productName, price, description। Column padding 20.dp। উপরে TextButton ← Back - popBackStack() মানে পেছনে যাও। তারপর Title headlineLarge Bold, Price headlineSmall primary Bold - `Price: 25000৳`, Description title, bodyLarge। নিচে Button fillMaxWidth Add to Cart - এখন empty `onClick={}` - Next video তে Cart বানাবো।"

**[Screen: Emulator Home->Details->Back->Logout flow]**
> "দেখো Logout শুধু home এ দেখায়, details এ না। কারণ NavGraph এ condition দেওয়া।"

---
### PART 9: fff.xml বিভ্রান্তি Clear [4 min] - ভুল ভাঙাও

**[Screen: `res/layout/fff.xml` দেখাও]**
**Bolo:**
> "সবচেয়ে বেশি প্রশ্ন: ভাই fff.xml change করলে দেখা যায় না কেন? উত্তর: এই file এখন ব্যবহারই হয় না। এটা পুরনো test file - ভেতরে ৫টা Button। App 100% Compose চলে MainActivity -> MyApp() দিয়ে। তাই XML বদলালে কিছু হবে না। চাইলে delete করে দাও। আগে setContentView + setContent দুইটা একসাথে ছিল, তাই hide হয়ে যেত - এখন শুধু setContent আছে।"

---
### PART 10: Run + Homework + Outro [5 min]

**[Screen: Run > Run app, Emulator + ফোনে]**
**Bolo:**
> "চলো final run। Splash 2 sec, Login, Home 6টা Card, Details, Logout - সব ok। তোমাদের homework ৩টা: ১. Splash এ নিজের নাম বসাও। ২. Product.kt এ নিজের ২টা product যোগ করো। ৩. Details Add to Cart এ Toast লাগাও - 'Added' দেখাবে। Comment এ screenshot দাও। Next video তে Room Database দিয়ে Login save + Real Cart + Dark Mode। Subscribe + Bell চাপো, Part 1 থেকে আবার দেখো। Allah Hafiz!"

**Description Box Timestamps:**
```
00:00 Demo, 02:15 Setup, 08:00 MainActivity, 13:00 NavGraph, 21:00 Splash, 26:00 Login/Register, 34:00 Home/Card, 42:00 Details, 48:00 fff.xml Truth, 52:00 Run + Homework
Tags: #JetpackCompose #Kotlin #BanglaTutorial #AndroidApp #DokanApp
Thumbnail text: দোকান App বানাও | Compose Full Course
```
