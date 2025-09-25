package com.example.lumiapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Home()

        }
    }
}

val color1 = Color(0xFF009688)

@Composable
fun Home(){

    val participants = listOf(
        "Coutinho", "Nuno", "Veggeti", "Léo Jardim", "Rayan", "São Carlo Acutis", "São Francisco", "Santa Rita", "São Bento", "São José", "Nsa. de la Salette"
    )

    var winners by remember { mutableStateOf<List<String>>(emptyList()) }

    fun sorteio(){
        val sorteio = participants.shuffled().take(3)
        winners = sorteio
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Lumi App",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(50.dp)
        )

        Button(
            onClick = {
                sorteio()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = color1
            ),
            modifier = Modifier.padding(0.dp,0.dp, 0.dp, 20.dp)
        ) {
            Text(
                text = "Realizar sorteio",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        if(winners.isNotEmpty()) {
            winners.forEachIndexed { index, winner ->
                Text(
                    text = "Vencedor ${index+1}: ${winner}",
                    fontSize = 18.sp,
                    color = color1,
                    fontWeight = FontWeight.Bold
                )

            }
        }



    }

}


@Preview
@Composable
private fun HomePreview(){
    Home()
}