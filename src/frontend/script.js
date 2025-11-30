const API = "http://localhost:8080";
const headers = { "Content-Type": "application/json" };

function mostrarSecao(id) {
    document.querySelectorAll("section").forEach(s => s.classList.remove("ativo"));
    document.getElementById(id).classList.add("ativo");
}

async function getJSON(url) {
    const res = await fetch(url);
    return res.ok ? res.json() : [];
}

async function postJSON(url, data) {
    await fetch(url, { 
        method: "POST", 
        headers, 
        body: JSON.stringify(data) 
    });
}

async function patchJSON(url, data) {
    const res = await fetch(url, {
        method: 'PATCH',
        headers,
        body: JSON.stringify(data)
    });
    
    return res.ok ? res.json() : []; 
}

async function deleteItem(url) {
    await fetch(url, { 
        method: "DELETE" 
    });
}

async function carregarClientes() {
    const clientes = await getJSON(`${API}/clientes`);
    const tabela = document.getElementById("tabelaClientes");
    tabela.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Ações</th>
        </tr>
        ${clientes.map(cliente => `
            <tr>
                <td>${cliente.id}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.cpf}</td>
                <td>${cliente.email}</td>
                <td>${cliente.telefone}</td>
                <td><button class="acao" onclick="removerCliente(${cliente.id})">Remover</button></td>
            </tr>`).join("")}
    `;
}

document.getElementById("formCliente").onsubmit = async evento => {
    evento.preventDefault();
    const data = {
        nome: nomeCliente.value,
        cpf: cpfCliente.value,
        email: emailCliente.value,
        telefone: telefoneCliente.value
    };
    await postJSON(`${API}/clientes`, data);
    evento.target.reset();
    carregarClientes();
};

async function removerCliente(id) {
    if (confirm("Remover cliente?")) {
        await deleteItem(`${API}/clientes/${id}`);
        carregarClientes();
    }
}

async function carregarAdministradores() {
    const administradores = await getJSON(`${API}/administradores`);
    const tabela = document.getElementById("tabelaAdministradores");
    tabela.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>ID da filial</th>
            <th>Ações</th>
        </tr>
        ${administradores.map(administrador => `
            <tr>
                <td>${administrador.id}</td>
                <td>${administrador.nome}</td>
                <td>${administrador.cpf}</td>
                <td>${administrador.email}</td>
                <td>${administrador.telefone}</td>
                <td>${administrador.filial?.id}</td>
                <td><button class="acao" onclick="removerAdministrador(${administrador.id})">Remover</button></td>
            </tr>`).join("")}
    `;
}

document.getElementById("formAdministrador").onsubmit = async evento => {
    evento.preventDefault();
    const data = {
        nome: nomeAdministrador.value,
        cpf: cpfAdministrador.value,
        email: emailAdministrador.value,
        telefone: telefoneAdministrador.value,
        filial: {
            id: Number(filialAdministrador.value)
        }
    };
    await postJSON(`${API}/administradores`, data);
    evento.target.reset();
    carregarAdministradores();
};

async function removerAdministrador(id) {
    if (confirm("Remover administrador?")) {
        await deleteItem(`${API}/administradores/${id}`);
        carregarAdministradores();
    }
}

async function carregarEspacos() {
    const espacos = await getJSON(`${API}/espacos`);
    const tabela = document.getElementById("tabelaEspacos");
    tabela.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Capacidade</th>
            <th>Preço da diária</th>
            <th>Filial</th>
            <th>Rua</th>
            <th>Cidade</th>
            <th>Estado</th>
            <th>Número</th>
            <th>Ações</th>
        </tr>
        ${espacos.map(espaco => `
            <tr>
                <td>${espaco.id}</td>
                <td>${espaco.nome}</td>
                <td>${espaco.descricao}</td>
                <td>${espaco.capacidade}</td>
                <td>R$ ${espaco.precoDiaria.toFixed(2)}</td>
                <td>${espaco.filial?.id}</td>
                <td>${espaco.endereco?.rua}</td>
                <td>${espaco.endereco?.cidade}</td>
                <td>${espaco.endereco?.estado}</td>
                <td>${espaco.endereco?.numero}</td>
                <td><button class="acao" onclick="removerEspaco(${espaco.id})">Remover</button></td>
            </tr>`).join("")}
    `;
}

document.getElementById("formEspaco").onsubmit = async evento => {
    evento.preventDefault();
    const data = {
        nome: nomeEspaco.value,
        descricao: descricaoEspaco.value,
        capacidade: Number(capacidadeEspaco.value),
        precoDiaria: Number(precoDiariaEspaco.value),
        filial: {
            id: Number(filialEspaco.value)
        },
        endereco: {
            rua: ruaEspaco.value,
            cidade: cidadeEspaco.value,
            estado: estadoEspaco.value,
            numero: Number(numeroEspaco.value)
        }
    };
    await postJSON(`${API}/espacos`, data);
    evento.target.reset();
    carregarEspacos();
};

async function removerEspaco(id) {
    if (confirm("Remover espaço?")) {
        await deleteItem(`${API}/espacos/${id}`);
        carregarEspacos();
    }
}

async function carregarFiliais() {
    const filiais = await getJSON(`${API}/filiais`);
    const tabela = document.getElementById("tabelaFiliais");
    tabela.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Rua</th>
            <th>Cidade</th>
            <th>Estado</th>
            <th>Número</th>
            <th>Ações</th>
        </tr> 
        ${filiais.map(filial => `
            <tr>
                <td>${filial.id}</td>
                <td>${filial.nome}</td>
                <td>${filial.endereco?.rua}</td>
                <td>${filial.endereco?.cidade}</td>
                <td>${filial.endereco?.estado}</td>
                <td>${filial.endereco?.numero}</td>
                <td><button class="acao" onclick="removerFilial(${filial.id})">Remover</button></td>
            </tr>`).join("")}
    `;
}

document.getElementById("formFilial").onsubmit = async evento => {
    evento.preventDefault();
    const data = {
        nome: nomeFilial.value,
        endereco: {
            rua: ruaFilial.value,
            cidade: cidadeFilial.value,
            estado: estadoFilial.value,
            numero: Number(numeroFilial.value)
        }
    };
    await postJSON(`${API}/filiais`, data);
    evento.target.reset();
    carregarFiliais();
};

async function removerFilial(id) {
    if (confirm("Remover filial?")) {
        await deleteItem(`${API}/filiais/${id}`);
        carregarFiliais();
    }
}

async function carregarReservas() {
    const reservas = await getJSON(`${API}/reservas`);
    const tabela = document.getElementById("tabelaReservas");
    tabela.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Início</th>
            <th>Fim</th>
            <th>Sinal</th>
            <th>Valor total</th>
            <th>ID do Cliente</th>
            <th>ID do Espaço</th>
            <th>Status</th>
            <th>Pagamento</th>
            <th>Ações</th>
        </tr>
        ${reservas.map(reserva => `
            <tr>
                <td>${reserva.id}</td>
                <td>${reserva.dataHoraInicio}</td>
                <td>${reserva.dataHoraFim}</td>
                <td>${reserva.sinal}</td>
                <td>${reserva.valorTotal}</td>
                <td>${reserva.cliente?.id}</td>
                <td>${reserva.espaco?.id}</td>
                <td>${reserva.statusReserva}</td>
                <td>${reserva.statusPagamento}</td>
                <td>
                    <button class="acao pagar" onclick="pagarReserva(${reserva.id})">Pagar</button>
                    <button class="acao cancelar" onclick="cancelarReserva(${reserva.id})">Cancelar</button>
                </td>
            </tr>`).join("")}
        `;
}

document.getElementById("formReserva").onsubmit = async evento => {
    evento.preventDefault();
    const data = {
        cliente: { 
            id: Number(idClienteReserva.value) 
        },
        espaco: { 
            id: Number(idEspacoReserva.value) 
        },
        dataHoraInicio: inicioReserva.value,
        dataHoraFim: fimReserva.value,
        sinal: Number(sinalReserva.value)
    };
    await postJSON(`${API}/reservas`, data);
    evento.target.reset();
    carregarReservas();
};

async function cancelarReserva(id) {
    if (confirm("Cancelar reserva?")) {
        await deleteItem(`${API}/reservas/${id}`, {});
        carregarReservas();
    }
}

async function pagarReserva(id) {
    const valor = prompt("Digite o valor a ser pago:");
    if (valor && !isNaN(valor)) {
        try {
            const response = await patchJSON(`${API}/reservas/${id}/pagar`, { 
                valor: parseFloat(valor) 
            });
            alert(response.message);
            carregarReservas();
        } catch (error) {
            alert('Erro ao processar pagamento: ' + error.message);
        }
    } else {
        alert('Valor inválido!');
    }
}

window.onload = () => {
    carregarClientes();
    carregarEspacos();
    carregarFiliais();
    carregarReservas();
};