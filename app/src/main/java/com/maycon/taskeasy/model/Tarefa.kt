package com.maycon.taskeasy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas") // Define o nome da tabela no banco
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val data: String, // Vamos salvar como String simples (ex: "29/10/2025")
    val concluida: Boolean = false,
    val usuarioId: String // Para saber de qual usuário é essa tarefa (Firebase User ID)
)