package com.tkw.composetest

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tkw.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("test", "setContent function")
//            Test()
            ClickTest()
        }
    }
}

@Composable
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
            Greeting("Android $test")
        }
        CustomColumn(
            0,
            modifier = Modifier
                .clickable {
                    test2++
                }
                .align(Alignment.CenterEnd)
        ) {
            Log.d("test", "Column function")
            Greeting("Android $test2")
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
        Greeting("Android $test")
    }.also {
        Log.d("test", "Button scope")
    }
}

@Composable
inline fun CustomColumn(test2: Int, modifier: Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier) {
        content()
    }.also {
        Log.d("test", "Column scope")
    }
}

@Composable
fun CustomColumn2(test2: Int, modifier: Modifier) {
    Column(modifier = modifier) {
        Log.d("test", "Column function")
        Greeting("Android $test2")
    }.also {
        Log.d("test", "Column scope")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Log.d("test", "greeting function")
    Text(
        text = "Hello $name!",
        modifier = modifier
    ).also {
        Log.d("test", "text scope")
    }
}

@Preview(showBackground = true, name = "#1")
@Composable
fun GreetingPreview() {
    ComposeTestTheme {
        Greeting("Android")
    }
}