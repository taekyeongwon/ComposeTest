package com.tkw.composetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tkw.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("test", "setContent function")
//            Test()
//            ClickTest()
            Button(onClick = {
                val intent = Intent(this, SubActivity::class.java)
                startActivity(intent)
            }) {

            }
        }
    }
}

@Composable
@Preview
fun ClickTest() {
    var test by remember {
        mutableStateOf(0)
    }
    var test2 by remember {
        mutableStateOf(0)
    }
    Log.d("test", "ClickTest recomposition")

    Box(modifier = Modifier.fillMaxWidth()) {
        Log.d("test", "Box recomposition")
        Button(onClick = {
            test++
        }) {
            Log.d("test", "Button recomposition")
            Greeting(
                { test.toString() }
            )
        }
//        CustomColumn(
//            0,
//            modifier = Modifier
//                .clickable {
//                    test2++
//                }
//                .align(Alignment.CenterEnd)
//
//        ){
//            Log.d("test", "Column function2")
//            test2.toString()
//        }
        Column(modifier = Modifier
            .clickable {
                test2++
            }
            .align(Alignment.CenterEnd)) {
            Log.d("test", "Column function")
            Greeting(
                { test.toString() }
            )
            Greeting(
                { test2.toString() }
            )
        }
    }
}

@Composable
fun Test() {    //Test 전체 리컴포지션, 하위 컴포저블은 상태 안변하면 스킵
    Box(modifier = Modifier.fillMaxWidth()) {
        var test by remember {
            mutableStateOf(0)
        }
        var test2 by remember {
            mutableStateOf(0)
        }
        val modifier = remember {
            mutableStateOf(
                Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { test2++ }
            )
        }
        Log.d("test", "Box function")
        CustomButton(test) {
            test++
        }
        CustomColumn2(
            test2,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { test2++ }
        )
    }.also {
        Log.d("test", "Box scope")
    }
}

@Composable
fun CustomButton(test: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Log.d("test", "Button function")
//        Greeting("Android $test")
    }.also {
        Log.d("test", "Button scope")
    }
}

@Composable
fun CustomColumn(test2: Int, modifier: Modifier, content: () -> String) { //이렇게 컴포저블 떼고 람다로만
    Column(modifier = modifier) {
        Greeting(content)   //Greeting도 람다로 넘기면 CustomColumn은 리컴포지션 대상이 아님.
        // 그냥 String매개변수로 받는다면 CustomColumn까지 리컴포지션 대상이라 아래 Greeting은 skip됨.
        Greeting({ "asdf" })
    }.also {
        Log.d("test", "Column scope")
    }
}

@Composable
fun CustomColumn2(test2: Int, modifier: Modifier) {
    Column(modifier = modifier) {
        Log.d("test", "Column function")
//        Greeting("Android $test2")
    }.also {
        Log.d("test", "Column scope")
    }
}

@Composable
fun Greeting(name: () -> String, modifier: Modifier = Modifier) {
    Log.d("test", "greeting function")
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(
            text = "Hello ${name()}!",
            modifier = modifier.padding(24.dp)
        ).also {
            Log.d("test", "text scope")
        }
    }

}

@Preview(showBackground = true, name = "#1")
@Composable
fun GreetingPreview() {
    ComposeTestTheme {
//        Greeting("Android")
    }
}