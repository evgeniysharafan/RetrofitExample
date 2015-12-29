# RetrofitExample

This project shows how to use [Retrofit](https://github.com/square/retrofit) with retained fragment.

Unfortunately I've seen a lot of developers who just call Retrofit's request methods without any cases for orientation changes and proper lifecycle handling.
This approach takes care about all such issues.

It adds callbacks in onResume(), removes them in onPause(), and saves the repsonse if we get it between onPause() and onResume().

It won't send the same request again if we haven't got the response from the previous call.

It handles the case when user opens a screen (we send our request here) and rotates the device. User even may open a screen, left it (e.g. choose another fragment from NavigationDrawer), and when the user comes back we will have the data for showing.

It allows to cache responses very easy because we use Activity's fragment manager and any fragment of the same Activity has access to these responses. This cache will be destroyed with this Activity, so we use the same logic which is used in onSaveInstanceState().

-
This project uses Retrofit 1.9. When version 2.0 is released, this project will be updated.
