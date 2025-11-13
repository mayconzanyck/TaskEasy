package com.maycon.taskeasy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maycon.taskeasy.model.Tarefa

// Define qual é a entidade (tabela) e a versão do banco
@Database(entities = [Tarefa::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // Expõe o DAO para o resto do app
    abstract fun tarefaDao(): TarefaDao
}