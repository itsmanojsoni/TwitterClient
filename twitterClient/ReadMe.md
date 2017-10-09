# Project 4 - Twitter

Twitter is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://developer.twitter.com/en/docs/api-reference-index).

Time spent 14 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] The app includes **all required user stories** from Week 3 Twitter Client
* [X] User can **switch between Timeline and Mention views using tabs**
* [X] User can view their home timeline tweets.
* [X] User can view the recent mentions of their username.
* [X] User can navigate to **view their own profile**
* [X] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [X] User can **click on the profile image** in any tweet to see **another user's** profile.
* [X] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [X] Profile view includes that user's timeline
* [X] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [ ] User can view following / followers list through the profile
* [X] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **"reply" to any tweet on their home timeline**
* [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [X] User can click on a tweet to be **taken to a "detail view"** of that tweet
* [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] User can **search for tweets matching a particular query** and see results
* [ ] Usernames and hashtags are styled and clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans)

The following **bonus** features are implemented:

* [X] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] On the profile screen, leverage the [CoordinatorLayout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events) to [apply scrolling behavior](https://hackmd.io/s/SJyDOCgU) as the user scrolls through the profile timeline.
* [ ] User can view their direct messages (or send new ones)

The following **additional** features are implemented:

* [X] Implemented tweets in a card view
* [X] Used Retrofit and OkHttp as http client and network library 
* [X] Truncated Follower and Following number into readable form 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

https://github.com/itsmanojsoni/TwitterClient/blob/master/twitterClient/TwitterAssignment4.mp4

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Retrofi](http://square.github.io/retrofit/) - An HTTP Client for Android
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [OkHttp](http://square.github.io/okhttp/) - networking library
- [Parceler](https://github.com/johncarl81/parceler) - Parcel Library
- [ButterKnife](http://jakewharton.github.io/butterknife/) - View Binding Library
- [RxJAVA](https://github.com/ReactiveX/RxJava) - Reactive Programming


## License

    Copyright [2017] [Manoj Soni]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
