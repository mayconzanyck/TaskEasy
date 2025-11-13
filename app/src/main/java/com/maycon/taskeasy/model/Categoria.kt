package com.maycon.taskeasy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categorias")
data class Categoria(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nome: String = ""
)