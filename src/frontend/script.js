async function testar() {
    try {
        const res = await fetch("http://localhost:8080/ping") // nome do serviço Docker!
        document.getElementById("res").innerText = await res.text();
    } catch (e) {
        document.getElementById("res").innerText = "Erro: " + e;
    }
}

async function testarModelos() {
    try {
        const res = await fetch("http://localhost:8080/teste-modelos") // nome do serviço Docker!
        document.getElementById("res2").innerText = await res.text();
    } catch (e) {
        document.getElementById("res2").innerText = "Erro: " + e;
    }
}