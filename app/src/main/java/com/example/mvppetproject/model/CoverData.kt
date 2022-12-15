package com.example.mvppetproject.model

data class CoverData(
    val author: String?,
    val description: String?,
    val image: String?,
    val rating: Double?,
    val textId: String?,
    val title: String?
) {
    companion object {
        val getEmpty = CoverData(
            author = "Alina Hmel",
            description = "Ты добрался сюда? Молодец. Но знай, тебе скоро конец.",
            image = "https://bmap-addicted-stories.eu-central-1.linodeobjects.com/images/desperate-travelers/cover-original.jpg",
            rating = 7.5,
            textId = "desperate-travelers",
            title = "Отчаянные путники"
        )
    }
}