# HyprMX Android Usage Guide

Now that you have the HyprMX library installed in your project, it's time to put it to work! To help you integrate HyprMX into your application, we will first provide a brief overview of the workflow and concepts, and then concrete examples of how to use the library. Please note that the current release of our Android SDK does not serve ads on Lollipop or Marshmallow due to a bug in Google's YouTube API which can cause crashes on these OS versions. 

## Overview

The HyprMX library consists of 3 different parts. The Helper, the Activity Layer, and the Service Layer. The HyprMXHelper is used to initialize the library as well as by the Activity Layer to perform various actions. The Activity Layer is where the user interaction takes place. From an Activity, a button, banner, splashscreen, or custom control will be presented to the user. The Activity layer will transform the user interaction into an intent that launches the appropriate HyprMX Activity or set of Activities based on the current application state, information required, and offers available. The Service Layer is used to track longer running offers such as application installs. The Service Layer is in charge of tracking changes and notifying the user when a reward has been earned, delivering them back to the Activity Layer.

## Examples

The HyprMX SDK project contains two example projects. The HyprMX-Example-App is a full featured example, aimed at showcasing the entire featureset. The Simple-Example project is aimed at containing only the minimum amount of code required to integrate the HyprMX Library.

## Initialization

It's recommended that you initialize the HyprMX library in your `Application` subclass. If you do not have an `Application` subclass, you can initialize it in your main `Activity`. If you choose to initialize the helper in your activity, make sure it happens before `super.onCreate` is called. It is initialized like so:

```java
Context context = this; // The context is used by the HyprMX Jar to get references to the layouts which are contained in the SDK bundle, since a JAR cannot contain XML files.  
String distributorID = "[Your Distributor ID]";  
String propertyID = "[Your Property ID]";  
String userID = "[Unique ID Identifying This User]"; // Or 'null'.  
  
HyprMXHelper.getInstance(context, distributorID, propertyID, userID);
```

It is important to note that the values for distributorID and propertyID are assigned to your app by HyprMX. The userID is supplied by the application and should remain the same as long as the user of the application is the same (without disclosing sensitive details such as email address).

Once the `HyprMXHelper `is initialized, interact with it using the singleton accessor `HyprMXHelper.getInstance()`.

## Activities

Your application may contain a single or many Activities. Anywhere in your application that you wish to present HyprMX Offers, your `Activity` must meet the following requirements:

Inherit from `HyprMXActivity`, `HyprMXFragmentActivity`, `HyprMXListActivity`, or `HyprMXExpandableListActivity`  
- OR -  
Implement the `HyprMXListener` interface  
Override the following 3 methods inherited from `Activity`:  
  - In `onCreate`, call `HyprMXHelper.handleOnCreate(this, savedInstanceState);`  
  - In `onActivityResult` call `HyprMXHelper.processActivityResult(this, requestCode, resultCode, data, this);`  
  - in `onBackPressed` call `HyprMXHelper.handleOnBackPressed();`  

The `HyprMXActivity` classes are provided for your convenience, while the second option is provided to allow maximum flexibility within your application. You must complete this integration to use the HyprMX SDK.

### No UI

If you wish to create your own controls to control user interaction, you will want to use this method to present offers to your app users. This method uses the `HyprMXPresentation` class to get and display offers. The following steps will walk you through using the `HyprMXPresentation` class to present offers. An example can also be found in the `NoUIPresentationActivity` class of the HyprMX-Example-App.

First, you must create and prepare the presentation. You will create a new presentation for each offer you wish to present. You can create and prepare the presentation whenever you like, but you will want to re-create it if your application is resumed. You needn't worry about re-creating your HyprMX presentation if it has already been displayed to the user, as in this case HyprMX manages the lifecycle. You must provide the prepare method an `OnOffersAvailableResponseReceivedListener` 

```java
HyprMXPresentation presentation = new HyprMXPresentation();  
presentation.prepare(listener)  
```

Your listener will receive one of the following callbacks:

`onNoOffersAvailable`  - Indicating there are no offers to display.
`onError` - Indicating there was an error requesting offers.
`onOffersAvailable` - Indicating that we now have offers (or required information) available to display.

Only once you have received `onOffersAvailable` can you proceed to the second step. Once you have offers, and the user taps your control, you can trigger the display of the offers as follows:

`presentation.show(this);`

`this` being a reference to your `Activity` meeting the requirements outlined above in the Activities section.

### Buttons

To use a `HyprMXButton`, all you need to do is add one to your layout. Buttons come in two sizes, 'large' and 'small'. Small buttons will generally be 50x50, while large buttons will generally be 150x150. Your XML might look as follows:

```xml
<com.hyprmx.android.sdk.HyprMXButton  
  android:id="@+id/hyprmx_button"  
  android:layout_width="wrap_content"  
  android:layout_height="wrap_content"  
  android:layout_margin="5dp"  
  app:buttonSize="large" />  
```

### Banners

Banners, similar to buttons, only require you to add them to your layout. HyprMX will use the image that best fits your allocated banner width. Height will be sized to match, so be sure to use the 'wrap\_content' property for height. The actual banner size will vary based on device size and resolution.

Your XML might look like this:

```xml
<com.hyprmx.android.sdk.HyprMXBanner  
  android:id="@+id/hyprmx_banner"  
  android:layout_width="fill_parent"  
  android:layout_height="wrap_content"  
  android:layout_alignParentTop="true"  
  android:layout_centerHorizontal="true" />  
```

### Splash Screens

A splash screen, unlike banners or buttons is not added to your layout. It will be displayed over your application.

To display a splash screen, you invoke `displaySplashScreen` on the `HyprMXHelper` instance and provide a conforming `Activity` instance (see the Activities section) and, optionally, pass in an `ArrayList` of `HyprMXReward` objects specific to this splash screen. Your code might look like so:

`HyprMXHelper.getInstance().displaySplashScreen(MainActivity.this, null);`

## Required Information

HyprMX may require certain information from your users to be able to display advertisements. If this is the case, a required information form will be presented to the user. Common pieces of information may be Gender or Date of Birth. Sometimes you'll know the current age of the user without knowing their birthdate. If you know any of this information, you may provide it programmatically using the `setRequiredInformation` method on the `HyprMXHelper` instance. You might do something like this:

```java
HashMap<String, String> information = new HashMap<String, String >();
information.put("dob", "1983-07-08");
information.put("age", "20"); // Don't set age if you're sending date of birth
information.put("gender", "m");
HyprMXHelper.getInstance().setRequiredInformation(information);
```

There may be other pieces of information you can set to expedite your user's experience. Contact your HyprMX representative for details.

## Rewards

Your application can specify what rewards are available to a user. These can be either global rewards, which will be set on the `HyprMXHelper` instance, or specific to one offer request (No UI, Button, Banner, or Splash Screen).

You might customize the rewards for your entire application as follows:

```java
HyprMXReward rewards[] = new HyprMXReward[2];
rewards[0] = new HyprMXReward(0, 0.01f, 1, "One Hundred Coins", -1);
rewards[1] = new HyprMXReward(1, 0.005f, 1000, "Fifty Coins", R.drawable.my\_reward\_icon);
HyprMXHelper.getInstance().setRewards(rewards);
```

Customizing rewards for specific elements is also easy. Examples:

No UI:

```java
ArrayList<HyprMXReward> rewards = new ArrayList<HyprMXReward>();  
// add rewards to list.  
presentation.setRewards(rewards);
```

Button:

```java
ArrayList<HyprMXReward> rewards = new ArrayList<HyprMXReward>();  
// add rewards to list.  
button.setRewards(rewards);
```

Banner:

```java
ArrayList<HyprMXReward> rewards = new ArrayList<HyprMXReward>();  
// add rewards to list.  
banner.setRewards(rewards);
```

Splash Screen:

```java
ArrayList<HyprMXReward> rewards = new ArrayList<HyprMXReward>();  
// add rewards to list.  
HyprMXHelper.getInstance().displaySplashScreen(MainActivity.this, rewards);
```

## Callbacks

You're going to want to be notified when your users earn rewards. This is communicated to you via the `HyprMXListener` interface, which your Activities using HyprMX must implement. The callbacks are used as follows:

`public void onOfferCompleted(Offer offer)`  
This is called when the user completes the requirements for an offer. It is safe to now deliver their reward.

`public void onOfferCancelled(Offer offer)`  
Called when a user cancels an offer (at any point). No reward should be delivered.

`public void onNoContentAvailable()`  
There are no offers to show the user. No banner/button/splash screen will be displayed.

`public void onUserOptedOut()`  
The user has opted out of splash screens. No more splash screens will be shown to the user. This callback is only used when splash screens are displayed.

## Pending Rewards

In some cases, the user may not return to your application after having completed a reward. This may be the case when the user completes an Application Install offer and then reboots their phone or kills your application before claiming their reward. To mitigate this issue and ensure that rewards are delivered, use the `deliverPendingRewards` method on the `HyprMXHelper`. This takes a `HyprMXListener` as it's only argument. This may be a `HyprMXActivity` or any other object implementing the `HyprMXListener` interface.

Depending on your usage, it may be appropriate to call `deliverPendingRewards` as soon as your application launches. In other cases, you may want to limit it to a specific activity where you are already displaying offers. When you call `deliverPendingRewards`, your listener will be notified immediately. It is then responsible for notifying the user that they have received the reward (just as you would normally when the user completes an offer).
