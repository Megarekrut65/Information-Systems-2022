function createObjectPage(createObjectFunction, createObjectSearch) {
    let div = document.createElement("div")
    let tableDiv = document.createElement("div")
    let recreateTable = (tableProperties) => {
        tableDiv.innerHTML = ""
        tableDiv.appendChild(createTable(tableProperties))
    }
    let searchProperties = createObjectSearch(recreateTable)
    searchProperties.createTable({})
    let searches = createSearches(searchProperties)
    let titleDiv = document.createElement("div")
    titleDiv.textContent = searchProperties.title
    titleDiv.className = "h-style"
    let functions = createObjectFunction(data => recreateTable({}))
    let functionDiv = createAddToBodyButtons(functions)
    div.appendChild(functionDiv)
    div.appendChild(titleDiv)
    div.appendChild(searches)
    div.appendChild(tableDiv)
    return div
}