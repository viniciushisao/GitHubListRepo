# githublistrepo
Make a list of Git Hub Repository

# Architecture

* Using [retrofit](http://square.github.io/retrofit/) for web calls.
* Using a variation of [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) which I consider the best for this project.
* [Realm DB](https://blog.realm.io/realm-for-android/?)
* Internet connection is checked and maintained in the very beginning start of application. This will avoid to change from local to internet in the middle of operations.

# Material Design

* Using [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)

# Fail:

Just found that google already its pagination [library](https://developer.android.com/topic/libraries/architecture/paging.html) AFTER I do all the work! Hahahahah. Anyway, there is not much gain on using Google's code and mine are pretty simple tho.
