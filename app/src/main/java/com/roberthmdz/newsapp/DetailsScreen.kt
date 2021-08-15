package com.roberthmdz.newsapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.roberthmdz.newsapp.models.News
import com.roberthmdz.newsapp.ui.theme.NewsAppTheme

@Composable
fun DetailsScreen(
    newTitle: String,
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val new by viewModel.getNewsByTitle(newTitle).observeAsState(initial = null)
    DetailsScreen(newTitle, navController, new)
}

@Composable
fun DetailsScreen(
    newTitle: String,
    navController: NavController,
    new: News?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = newTitle,  maxLines = 1) },
                navigationIcon = {
                    IconButton( onClick = {navController.popBackStack()} ){
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        new?.let {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        painter = rememberImagePainter(
                            data = new.urlToImage,
                            builder = {
                                placeholder(R.drawable.placeholder)
                                error(R.drawable.placeholder)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Column(Modifier.padding(8.dp)) {
                        val context = LocalContext.current

                        Text(new.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(new.content ?: "", maxLines = 3)
                        Box(modifier = Modifier.size(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(new.url))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Ver mas...")
                        }
                    }
                }

            }
        } ?: run {
            
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

// Build a preview for the screen
@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    NewsAppTheme() {

        DetailsScreen(
            newTitle = "Hello",
            navController = rememberNavController(),
            new = News(
                title = "Title 1",
                content = "Content description",
                author = "",
                url = "",
                urlToImage = "",
                publishedAt = "",
                description = ""
            ),
        )
    }
}