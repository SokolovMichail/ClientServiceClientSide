package com.example.clientserviceclientside.clientmodel

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ClientDTO(

        var surname: String = "Pass",
        var name: String = "Pass",
        var secondName: String = "Pass",
        var dr: Date = Date(),
        var account: String = "ERR_NO_ACC_DET",
        var bd:String=Date().toString())
{
fun checkValidity():Boolean {
    val surnameCheck = (!surname.isEmpty()) and (surname != "Pass")
    val nameCheck = (!name.isEmpty()) and (name != "Pass")
    val secondNameCheck = (!secondName.isEmpty()) and (secondName != "Pass")
    val accountCheck = (account.length == 14) and (Pattern.matches("\\d{4}\\-\\d{4}\\-\\d{4}",account)
            or (account == "ERR_NO_ACC_DET") or (account == "ERR_NO_ACC_REP"))
    return surnameCheck and nameCheck and secondNameCheck and accountCheck
}
}
