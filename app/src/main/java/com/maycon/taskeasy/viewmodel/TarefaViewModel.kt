package com.maycon.taskeasy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.maycon.taskeasy.data.TarefaRepository
import com.maycon.taskeasy.model.Tarefa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

// A ViewModel precisa do Repositório para funcionar
class TarefaViewModel(private val repository: TarefaRepository) : ViewModel() {

    // Começa como uma lista vazia. Só será preenchida após o login.
    private val _tarefas = MutableStateFlow<List<Tarefa>>(emptyList())
    val tarefas: StateFlow<List<Tarefa>> = _tarefas.asStateFlow()

    private var idUsuarioAtual: String? = null

    // Função que a UI (HomeScreen) vai chamar após o login
    fun carregarTarefas(idUsuario: String) {
        // Evita recarregar desnecessariamente se o ID não mudou
        if (idUsuario == idUsuarioAtual) return
        idUsuarioAtual = idUsuario

        viewModelScope.launch {
            // "Ouve" o banco de dados (Flow) e atualiza o _tarefas (StateFlow)
            repository.getTarefasPorUsuario(idUsuario)
                .distinctUntilChanged() // Só atualiza se a lista realmente mudar
                .collect { listaDeTarefas ->
                    _tarefas.value = listaDeTarefas
                }
        }
    }

    // Funções que a tela vai chamar (usando Corrotinas)
    fun inserir(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.inserir(tarefa)
        }
    }

    fun atualizar(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.atualizar(tarefa)
        }
    }

    fun deletar(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.deletar(tarefa)
        }
    }
}

// Isso é uma "Fábrica" (Factory)
// Ela ensina o Android a criar nossa ViewModel passando o Repositório para ela.
class TarefaViewModelFactory(private val repository: TarefaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TarefaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TarefaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}