package com.maycon.taskeasy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maycon.taskeasy.model.Categoria
import com.maycon.taskeasy.model.Tarefa

@Database(entities = [Tarefa::class, Categoria::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tarefaDao(): TarefaDao

    abstract fun categoriaDao(): CategoriaDao
}