package de.rogallab.mobile.domain.entities
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Person(
   val firstName: String = "",
   val lastName: String = "",
   val email: String? = null,
   val phone:String? = null,
   val imagePath: String? = "",
   val id: String = UUID.randomUUID().toString()
)