async function testar() {
    try {
        const res = await fetch("http://localhost:8080/ping") // nome do serviço Docker!
        document.getElementById("res").innerText = await res.text();
    } catch (e) {
        document.getElementById("res").innerText = "Erro: " + e;
    }
}
