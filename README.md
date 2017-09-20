# GitHub List 

Make a list of Git Hub Repository. Currently it is just working for listing JakeWharton's repository. In the future, I plan to change it.

# How it works

1. Application starts and retrieves data from Internet.
   1. If data from Internet fails:
   1. Data from local database is displayed.
   1. A message on top of screen is shown indicating there is no data from internet
      1. If data from local database fails:
      1. it shows an error page and a button to retry. 
1. Data is displayed in screen with pagination (no matter if came from ).

# Architecture

* Using [retrofit](http://square.github.io/retrofit/) for web calls.
* Using a variation of [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) which I consider the best for this project.
* [Realm DB](https://blog.realm.io/realm-for-android/?)
* Internet connection is checked and maintained in the very beginning start of application. This will avoid to change from local to internet in the middle of operations.

# Material Design

* Using [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
* Using [CardView](https://developer.android.com/training/material/lists-cards.html)

# TODO

* Probably there are more corner cases I did not tested.
* Improve tests. The ones I have right now are poor.
* For now, I just locked to not rotate the screen. 

# Fail:

* Just found that google already its pagination [library](https://developer.android.com/topic/libraries/architecture/paging.html) AFTER I do all the work! Hahahahah. Anyway, there is not much gain on using Google's code and mine are pretty simple tho.
* I am not good in design. Anyway, I do have all the information I need. If in the future we need to add more information, it will be just a matter of UI.