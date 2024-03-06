package com.example.groupgains.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class TableRowData(val column1: String, val column2: String, val column3: String)

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment 2"
    }

    val text: LiveData<String> = _text

    // Each Pair represents a row in the table.
    private val _tableData = MutableLiveData<List<TableRowData>>().apply {
        value = listOf(
            TableRowData("Exercise 1", "More data", "Even more data"),
            TableRowData("Row 2, Column 1", "Row 2, Column 2", "Row 2, Column 3"),
            TableRowData(" 3,1", "Row 3, 2", "3,3"),
            // Add more rows as needed
        )
    }

    val tableData: LiveData<List<TableRowData>> = _tableData
}