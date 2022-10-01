function createTable(list) {
    if (list.length == 0) return
    let keys = Object.keys(list[0])
    console.log(Object.keys(list[0]))
    let table = document.createElement("table")
    table.style.border = "1px solid black"
    let thead = document.createElement("thead")
    thead.appendChild(createHead(keys))
    let tbody = document.createElement("tbody")
    for (let obj in list) {
        tbody.appendChild(createRow(keys, list[obj]))
    }
    table.appendChild(thead)
    table.appendChild(tbody)
    return table
}

function createHead(keys) {
    let tr = document.createElement("tr")
    for (let key in keys) {
        let td = document.createElement("td")
        td.textContent = keys[key]
        tr.appendChild(td)
    }
    return tr;
}

function createRow(keys, obj) {
    let tr = document.createElement("tr")
    for (let key in keys) {
        let td = document.createElement("td")
        td.textContent = obj[keys[key]]
        tr.appendChild(td)
    }
    return tr;
}

function addTableToPage(parentId, list) {
    let parent = document.getElementById(parentId)
    parent.appendChild(createTable(list))
}