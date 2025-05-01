package com.louiserennick.treasurehunapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.louiserennick.treasurehunapp.ui.theme.TreasureHunAppTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntApp()
        }
    }
}
@Composable
fun Main (onStartClicked: () -> Unit, onOpenGallery:()->Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFCFF1))) {
        Image(
            painter = painterResource(id = R.drawable.maintomandjerry),
            contentDescription = "first page",
                modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome To The Great Hunt Of Tom Vs Jerry ",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Blue,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(450.dp))
            Button(
                onClick = onStartClicked,
                modifier = Modifier.padding( top = 20.dp)
            ) {
                Text ("Start The Hunt")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureHuntApp() {
    val viewModel: TreasureHuntViewModel = viewModel()
    val currentStep by viewModel.currentStep.collectAsState()
    val found by viewModel.found.collectAsState()
    val totalSteps = viewModel.locations.size
    var started by remember { mutableStateOf(false) }
    var imageU by remember { mutableStateOf<Uri?>(null) }
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {uri:Uri? ->
        imageU = uri
    }
    if (!started) {
        Main(onStartClicked = { started = true } ,onOpenGallery={
            getContent.launch ("image/*")
        })
    } else {

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Tom vs. Jerry: The Great Hunt") })
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color(0xFFFFDFF1))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Clue ${currentStep + 1} of $totalSteps",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Go to: ${viewModel.locations[currentStep]}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    val img = if (!found)
                        R.drawable.city_map
                    else
                        viewModel.images[currentStep]
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = "City Map",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
Button(
    onClick = {
        getContent.launch("image/*")
    }
) {
    Text ("Choose From Gallery")
}
                    Spacer(modifier = Modifier.height(16.dp))
//create a button to indicate the clue is found or no if the clue is found the button will be disable
                    Button(onClick = {
                        viewModel.clueFound()
                    }, enabled = !found) {
                        Text("I found it!")
                    }
//if the clue is found make a new button to go the next clue
                    if (found) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.nextLocation()
                            }, enabled = currentStep < totalSteps - 1
                        ) {
                            Text("next clue")
                        }
                    }
                    if (currentStep == 9) { // if the current is pass 9 the discount code will dis[play
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            " You Getting 20% Discount for Completing 10 Clues "
                        )
                    }
                    if (currentStep == totalSteps - 1) { //if all of the steps are complete the form will be display
                        Spacer(modifier = Modifier.height(16.dp))
                        finalForm { name,email ->
                            println("$name,$email")
                        }
                    }
                }
            }
        )
    }
}
@Composable
fun finalForm (onSubmit:(String,String)-> Unit) { //the function to display the form after complete all the steps
    var name by remember { mutableStateOf("") } //string valuable to save users name
    var email by remember { mutableStateOf("") }// string valuable to save users email
    Column (horizontalAlignment = Alignment.CenterHorizontally) { //making a colum to display the text and text field
        Text(
            "🎉 You've completed the treasure hunt! Enter the draw now!",
            color = Color.DarkGray //final message after finding all the clues
        )
        Spacer(modifier = Modifier.height(16.dp)) //for the user to enter their name
        OutlinedTextField(
            value = name,
            onValueChange = {name=it},
            label = {Text ("Please Enter Your Name")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(  // for user to enter their email
            value = email,
            onValueChange = {email=it},
            label = {Text ("Please Enter Your Email")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
Button(onClick = { // button to submit the form
    onSubmit (name,email)
}) {
    Text("Submit")
}
    }
}