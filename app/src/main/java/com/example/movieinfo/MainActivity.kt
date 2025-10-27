package com.example.movieinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieinfo.ui.theme.MovieInfoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieInfoTheme(darkTheme = false) {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
@Preview
fun HomeScreen(){
    var query by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false)}
    val movieTitles = rememberSaveable {mutableStateListOf<String>()}
    val movieReleaseDate = rememberSaveable {mutableStateListOf<String>()}
    val moviePosterUrl = rememberSaveable {mutableStateListOf<String>()}
    val typeOfMovie = rememberSaveable {mutableStateListOf<String>()}

    var allIsPressed = remember {mutableStateOf(true)}
    var moviesIsPressed = remember {mutableStateOf(false)}
    var seriesIsPressed = remember {mutableStateOf(false)}
    Box {
        Image(modifier=Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.grad),
            contentDescription = "bg",
            contentScale = ContentScale.FillBounds
        )
            Column(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Column(Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Text("Search for any movie or series:",
                        fontSize = 20.sp,
                        )
                }

                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Enter Movie Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    maxLines = 1,
                    placeholder = {Text("Search movies or TV Shows...")}

                )

                val scope = rememberCoroutineScope()
                Row(Modifier.fillMaxWidth()){
                if (allIsPressed.value){
                    moviesIsPressed.value=false
                    seriesIsPressed.value=false
                }


                    if (allIsPressed.value){
                        Button(onClick = {allIsPressed.value=false},Modifier
                            .weight(0.5f)
                            .padding(10.dp, 5.dp, 5.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("All")}
                    } else{
                        OutlinedButton(onClick = {allIsPressed.value=true},Modifier
                            .weight(0.5f)
                            .padding(10.dp, 5.dp, 5.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("All")}}

                    if(moviesIsPressed.value){
                        Button(onClick = {
                            moviesIsPressed.value=false},Modifier
                            .weight(0.5f)
                            .padding(5.dp, 5.dp, 5.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("Movies")}
                    } else {
                        OutlinedButton(onClick =
                            {moviesIsPressed.value=true
                            if (allIsPressed.value){
                                allIsPressed.value=false
                            }}
                            ,Modifier
                            .weight(0.5f)
                            .padding(5.dp, 5.dp, 5.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("Movies")}
                    }
                    if (seriesIsPressed.value){
                        Button(onClick = {seriesIsPressed.value = false},Modifier
                            .weight(0.5f)
                            .padding(5.dp, 5.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("Series")}
                    } else {
                        OutlinedButton(onClick = {seriesIsPressed.value = true
                                                 if (allIsPressed.value){
                                                     allIsPressed.value=false
                                                 }},Modifier
                            .weight(0.5f)
                            .padding(5.dp, 5.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp)){Text("Series")}
                    }


                    }
                Row {
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    isLoading = true
                                    movieTitles.clear()
                                    movieReleaseDate.clear()
                                    moviePosterUrl.clear()
                                    val safeQuery = query.replace(" ", "_")
                                    val response = RetrofitInstance.api.searchMovies(safeQuery)
                                    if (response.search == null) {
                                        movieTitles.add("No results found")
                                        movieReleaseDate.add("")
                                        moviePosterUrl.add("")
                                        typeOfMovie.add("")
                                    }
                                    if (response.search != null) {
                                        for (movie in response.search) {
                                            movieTitles.add(movie.title ?: "Unknown")
                                            movieReleaseDate.add(movie.year ?: "Unknown")
                                            moviePosterUrl.add(movie.poster ?: "Unknown")
                                            typeOfMovie.add(movie.type ?: "Unknown")
                                        }
                                    }
                                } catch (e: Exception) {
                                    movieTitles.add("No results found")
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(10.dp, 10.dp, 5.dp, 10.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Search")
                    }
                    Button(onClick = {
                        movieTitles.clear()
                        movieReleaseDate.clear()
                        moviePosterUrl.clear()
                        typeOfMovie.clear()
                    }, modifier = Modifier.padding(5.dp,10.dp,10.dp,10.dp),
                        shape = RoundedCornerShape(5.dp)){Text("Clear")}
                }

                Spacer(Modifier.size(5.dp))
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    items(movieTitles.size) {
                        ElevatedCard(
                            Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
//                                .combinedClickable(
//                                    onClick = {}
//                                )

                        ) {
                            if (allIsPressed.value || (moviesIsPressed.value && seriesIsPressed.value)) {
                                Row {
                                    Text(
                                        movieTitles[it],
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .weight(1f)
                                    )
                                    Text(
                                        movieReleaseDate[it],
                                        modifier = Modifier
                                            .padding(20.dp)
                                    )
                                    GlideImage(
                                        model = moviePosterUrl[it],
                                        contentDescription = "$it"
                                    )
                                }}
                            else if(seriesIsPressed.value) {
                                if (typeOfMovie[it] == "series") {
                                    Row {
                                        Text(
                                            movieTitles[it],
                                            modifier = Modifier
                                                .padding(20.dp)
                                                .weight(1f)
                                        )
                                        Text(
                                            movieReleaseDate[it],
                                            modifier = Modifier
                                                .padding(20.dp)
                                        )
                                        GlideImage(
                                            model = moviePosterUrl[it],
                                            contentDescription = "$it"
                                        )
                                    }
                                }
                            }
                            else if(moviesIsPressed.value){
                                if (typeOfMovie[it] == "movie") {
                                    Row {
                                        Text(
                                            movieTitles[it],
                                            modifier = Modifier
                                                .padding(20.dp)
                                                .weight(1f)
                                        )
                                        Text(
                                            movieReleaseDate[it],
                                            modifier = Modifier
                                                .padding(20.dp)
                                        )
                                        GlideImage(
                                            model = moviePosterUrl[it],
                                            contentDescription = "$it"
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}