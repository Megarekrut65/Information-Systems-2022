function createErrorBox(message) {
    let box = document.createElement("div")
    box.className = "error-box"
    let text = document.createElement("div")
    text.textContent = message
    box.appendChild(text)
    let ok = document.createElement("div")
    ok.className = "ok-error-style"
    ok.textContent = "Ok"
    ok.onclick = () => {
        box.remove()
        return false
    }
    box.appendChild(ok)
    return box
}