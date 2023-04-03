package hu.bme.aut.bethere.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: String = "",
    val sentBy: String = "",
    val text: String = ""
) : Parcelable
