function createSearches(properties) {
    let container = document.createElement("div")
    container.className = "search-style"
    createSearchInputs(container, properties.inputs)
    createButtons(container, properties)
    return container
}

function clearSearchInputs(inputs) {
    for (let key in inputs) {
        document.getElementById(key + "-input").value = ""
    }
}

function getSearchResult(inputs) {
    let res = {}
    for (let key in inputs) {
        res[key] = document.getElementById(key + "-input").value
    }
    return res
}

function createSearchInputs(container, inputs) {
    for (let key in inputs) {
        if ("label" in inputs[key]) {
            let label = document.createElement("label")
            label.className = "label-style"
            label.textContent = inputs[key].label
            container.appendChild(label)
        }
        let input = document.createElement("input")
        input.className = "search-input"
        input.value = ""
        input.id = key + "-input"
        input.type = inputs[key].type
        if ("placeholder" in inputs[key]) input.placeholder = inputs[key].placeholder
        container.appendChild(input)
    }
}

function createButtons(container, properties) {
    container.appendChild(createSearchButton("../../Images/search.png", "Search", () => {
        properties.createTable(getSearchResult(properties.inputs))
    }))
    container.appendChild(createSearchButton("../../Images/clear.png", "Clear", () => {
        clearSearchInputs(properties.inputs)
        properties.createTable(getSearchResult(properties.inputs))
    }))
    if ("formCreate" in properties) container.appendChild(createSearchButton("../../Images/add.png", "Add", () => {
        addToBody(properties.formCreate(data =>
            properties.createTable(getSearchResult(properties.inputs))
        ))
    }))
}

function createSearchButton(img, title, onClick) {
    let button = document.createElement("img")
    button.src = img
    button.className = "button-img"
    button.title = title
    button.onclick = () => {
        onClick()
        return false
    }
    return button
}