package com.munozcristhian.pholapsc.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Sesion(val fecha: String?,
                  val direccion: String?,
                  val hora: String?,
                  val paquete: String?): Serializable
{
    constructor():this(
        "","","",""
    )
}
