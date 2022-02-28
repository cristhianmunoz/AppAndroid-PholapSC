package com.munozcristhian.pholapsc.model

import java.io.Serializable

data class Usuario(val nombre: String? = null,
                   val apellido: String? = null,
                   val genero: String? = null,
                   val telefono: String? = null,
                   val direccion: String? = null,
                   val correo: String? = null): Serializable
