function createAddToBodyButtons(properties) {
    let div = document.createElement("div")
    if (properties === null) return div
    let titleDiv = document.createElement("div")
    titleDiv.textContent = "Functions"
    titleDiv.className = "h-style"
    div.appendChild(titleDiv)
    let buttonList = document.createElement("div")
    buttonList.className = "functions-list-style"
    div.appendChild(buttonList)
    let buttons = properties.buttons
    for (let key in buttons) {
        let button = document.createElement("div")
        button.className = "functions-style"
        button.textContent = key
        button.onclick = () => {
            addToBody(buttons[key](properties.toSendData))
            return false
        }
        buttonList.appendChild(button)
    }
    return div
}