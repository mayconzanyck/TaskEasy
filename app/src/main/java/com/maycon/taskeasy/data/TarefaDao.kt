package com.maycon.taskeasy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maycon.taskeasy.model.Tarefa
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {

    // Insere uma nova tarefa. Se já existir (pelo id), substitui.
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun inserir(tarefa: Tarefa)

    // Atualiza uma tarefa existente
    @Update
    suspend fun atualizar(tarefa: Tarefa)

    // Deleta uma tarefa
    @Delete
    suspend fun deletar(tarefa: Tarefa)

    // Busca todas as tarefas de um usuário específico
    // Flow: O app "assiste" o banco. Se algo mudar, a lista na tela atualiza sozinha.
    @Query("SELECT * FROM tarefas WHERE usuarioId = :idUsuario ORDER BY concluida ASC")
    fun getTarefasPorUsuario(idUsuario: String): Flow<List<Tarefa>>
}