package com.example.androidencryption

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidencryption.ui.theme.AndroidencryptionTheme
import com.example.androidencryption.ui.theme.CryptoManger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManger=CryptoManger()
        setContent {
            AndroidencryptionTheme {
                  var messageToEncrypt by remember {
                      mutableStateOf("")
                  }
                var messageToDecrypt by remember{
                    mutableStateOf("")
                }
                Column (modifier= Modifier
                    .fillMaxSize()
                    .padding(32.dp))
                {
                   TextField(value = messageToEncrypt,
                       onValueChange ={messageToEncrypt=it},
                       modifier=Modifier.fillMaxWidth(),
                       placeholder = { Text(
                       text =("Encrypt String")
                   )} )
                    Spacer(modifier=Modifier.height(8.dp))
                    Row {
                        Button(onClick = {
                            val bytes=messageToEncrypt.encodeToByteArray()
                            val file=File(filesDir,"secret.txt")
                            if(!file.exists()){
                                file.createNewFile()
                            }
                            val fos=FileOutputStream(file)
                            messageToDecrypt=cryptoManger.encrypt(
                                bytes=bytes,
                                outputStream = fos
                            ).decodeToString()

                        }) {
                            Text(text="Encrypt")
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        val file=File(filesDir,"secret.txt")
                        messageToEncrypt=cryptoManger.decrypt(
                            inputStream = FileInputStream(file)
                        ).decodeToString()
                        
                    }) {
                        Text(text="Decrypt")
                    }
                    }

                Text(text = messageToDecrypt)
                }
            }
        }
    }

