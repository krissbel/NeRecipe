package ru.netology.myrecipebook.adapter

interface FilterInteractionListener {

    fun checkboxIsActive(category: String)
    fun checkboxNotActive(category: String)
    fun getStatusCheckbox(category: String): Boolean
}