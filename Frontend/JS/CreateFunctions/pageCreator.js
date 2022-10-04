function createObjectPage(createObjectFunction, getObjectsForTable, createObjectSearch, createObjectForm) {
    let div = document.createElement("div")
    let tableDiv = document.createElement("div")
    let recreateTable = item => {
        tableDiv.innerHTML = ""
        tableDiv.appendChild(createTable(getObjectsForTable(item)))
    }
    recreateTable({})
    let searchProperties = createObjectSearch(recreateTable, createObjectForm)
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