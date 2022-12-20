package com.example.mvppetproject

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
private fun UsersList() {
    val list = listOf<User>()
    List(list = list) {

    }
}

@Composable
private fun BooksList() {
    val list = listOf<Book>()
    List(list = list)
}

@Composable
private fun List(list: List<Label>, onClick: (Label) -> Unit = {}) {
    Column {
        list.forEach {
            Row {
                Icon(painter = painterResource(id = it.getIcon()), contentDescription = null)
                Text(text = it.getLabel())
            }
        }
    }
}

class User(
    val id: Int,
    val name: String,
    @DrawableRes val avatar: Int
): Label {
    override fun getLabel(): String {
        return name
    }

    override fun getIcon(): Int {
        return avatar
    }

}

class Book(
    val id: Int,
    val name: String,
    @DrawableRes val cover: Int
): Label {
    override fun getLabel(): String {
        return name
    }

    override fun getIcon(): Int {
        return cover
    }
}

interface Label {
    fun getLabel(): String
    @DrawableRes fun getIcon(): Int
}



class User2(
    name: String,
    @DrawableRes icon: Int,
    val id: Int,
): Label2(name, icon)

class Book2(
    name: String,
    @DrawableRes icon: Int,
    val id: Int,
): Label2(name, icon)

open class Label2(
    val name: String,
    @DrawableRes val icon: Int
)

@Composable
private fun UsersList2() {
    val list = listOf<User2>()
    List2(list = list)
}

@Composable
private fun BooksList2() {
    val list = listOf<Book2>()
    List2(list = list) {

    }
}



@Composable
private inline fun <T: Label2> List2(list: List<T>, crossinline onClick: (T) -> Unit = {}) {
    Column {
        list.forEach {
            Row(modifier = Modifier.clickable { onClick(it) }) {
                Icon(painter = painterResource(id = it.icon), contentDescription = null)
                Text(text = it.name)
            }
        }
    }
}