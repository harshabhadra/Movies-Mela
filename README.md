# Movies-Mela
This is application is made by me for Android Developer Nanedegree at Udacity. 
User can see movies list by sort order Popular or Top Rated. 
If they tap on any image on the MainActivity then user can see details about that Movie like Release data, average rating, summary, public reviews , movie trailers and other videos. 
User can store movies to Favorite and access them offline.

## What I am using to build this app?
# I am using
1. MVVM Architecture to build this app
2. RecyclerView with GridLayoutManager(No. of columns is calculated accroding to screensize) to populate the MainActivity with movie's poster. 
2. Retrofit library for Network Call from MovieDB API.
3. Room Database to store list of movies marked as favorite by the user.
4. Repository Class to maintain Network Call and Store items in Database.
5. Picasso Library to populate images.
6. Implicit Intent to start YouTube app when user want to watch the movie trailer.
7. SharedPreference to store user choice "Most Popular" or "Top Rated".

