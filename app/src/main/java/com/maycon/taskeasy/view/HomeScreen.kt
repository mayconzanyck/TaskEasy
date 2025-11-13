package com.maycon.taskeasy.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.maycon.taskeasy.model.Tarefa
import com.maycon.taskeasy.viewmodel.TarefaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tarefaViewModel: TarefaViewModel,
    userId: String,
    onNavigateToLogin: () -> Unit
) {
    // Carrega as tarefas do usuário assim que a tela abre
    LaunchedEffect(key1 = userId) {
        if (userId != "id_invalido") {
            tarefaViewModel.carregarTarefas(userId)
        }
    }

    // "Ouve" a lista de tarefas da ViewModel
    val tarefas by tarefaViewModel.tarefas.collectAsState()

    // Estado para controlar o diálogo de "Nova Tarefa"
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
                    // Botão de Logout
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut() // Desloga do Firebase
                        onNavigateToLogin() // Navega para o Login
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Sair")
                    }
                }
            )
        },
        floatingActionButton = {
            // Botão Flutuante (FAB) para adicionar tarefa
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding interno do Scaffold
        ) {
            if (tarefas.isEmpty()) {
                Text(
                    text = "Nenhuma tarefa cadastrada.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Lista de Tarefas (RF03)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(tarefas) { tarefa ->
                        TarefaItem(
                            tarefa = tarefa,
                            onToggleComplete = {
                                // Atualiza a tarefa com o novo estado 'concluida'
                                tarefaViewModel.atualizar(tarefa.copy(concluida = !tarefa.concluida))
                            },
                            onDelete = {
                                tarefaViewModel.deletar(tarefa)
                            }
                        )
                    }
                }
            }
        }

        // Diálogo para Nova Tarefa (RF02)
        if (showDialog) {
            NovaTarefaDialog(
                userId = userId,
                onDismiss = { showDialog = false },
                onSave = { novaTarefa ->
                    tarefaViewModel.inserir(novaTarefa)
                    showDialog = false
                }
            )
        }
    }
}

// Composable para o item da lista (RF04, RF05)
@Composable
fun TarefaItem(
    tarefa: Tarefa,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggleComplete() } // Clicar no Card marca como concluído
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarefa.concluida,
                onCheckedChange = { onToggleComplete() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarefa.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    // Se concluída, risca o texto
                    textDecoration = if (tarefa.concluida) TextDecoration.LineThrough else null
                )
                if (tarefa.descricao.isNotBlank()) {
                    Text(
                        text = tarefa.descricao,
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = if (tarefa.concluida) TextDecoration.LineThrough else null
                    )
                }
            }
            // Botão de Deletar (RF04)
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar Tarefa", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// Composable para o diálogo de "Nova Tarefa" (RF02)
@Composable
fun NovaTarefaDialog(
    userId: String,
    onDismiss: () -> Unit,
    onSave: (Tarefa) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Tarefa") },
        text = {
            Column {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição (Opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        val novaTarefa = Tarefa(
                            titulo = titulo,
                            descricao = descricao,
                            data = "", // Opcional, deixamos vazio por enquanto
                            usuarioId = userId
                        )
                        onSave(novaTarefa)
                    }
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}