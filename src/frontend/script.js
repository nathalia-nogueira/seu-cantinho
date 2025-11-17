// Verifica e retorna conexão com BD
async function testarConexaoComBD() {
    try {
        const res = await fetch("http://localhost:8080/ping") 
        document.getElementById("resBD").innerText = await res.text();
    } catch (e) {
        document.getElementById("resBD").innerText = "Erro: " + e;
    }
}

// Verifica e retorna camada modelo
async function testarCamadaModelo() {
    try {
        const res = await fetch("http://localhost:8080/teste-modelos") 
        document.getElementById("resModelo").innerText = await res.text();
    } catch (e) {
        document.getElementById("resModelo").innerText = "Erro: " + e;
    }
}