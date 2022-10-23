function createTable(properties) {
    let container = document.createElement("div")
    let table = document.createElement("table")
    container.appendChild(table)
    table.className = "table-style"
    table.appendChild(createHead(properties[0]))
    let createPage = (page, perPage)=>{
        let list = properties.getter(page, perPage)
        for (let i in list) {
            table.appendChild(createRow(properties.convector(list[i]), i % 2 === 0 ? "tr-style1" : "tr-style2"))
        }
    }
    let perPage = 20
    createPage(1, perPage)
    container.appendChild(createPageButtons(createPage, perPage, table))
    return container
}
function makeNoSelect(obj){
    obj.onmousedown = obj.onselectstart = function() {
        return false
    }
}
function createPageButtons(createPage, perPage, table){
    let page = 1
    let buttons = document.createElement("div")
    makeNoSelect(buttons)
    buttons.className = "page-buttons"
    let prev = document.createElement("div")
    buttons.appendChild(prev)
    prev.className = "page-button"
    prev.textContent = "<"
    prev.onclick = ()=>{
        if(page === 1) return
        page--
        count.textContent = page
        table.innerHTML = ''
        createPage(page, perPage)

    }
    let count = document.createElement("div")
    buttons.appendChild(count)
    count.className = "page-button"
    count.textContent = page
    let next = document.createElement("div")
    buttons.appendChild(next)
    next.className = "page-button"
    next.textContent = ">"
    next.onclick = ()=>{
        if(table.innerHTML === '') return
        page++
        count.textContent = page
        table.innerHTML = ''
        createPage(page, perPage)

    }
    return buttons
}
function createHead(titles) {
    let tr = document.createElement("tr")
    for (let i in titles) {
        let td = document.createElement("th")
        td.textContent = i
        tr.appendChild(td)
    }
    return tr;
}

function createRow(properties, style) {
    let tr = document.createElement("tr")
    tr.className = style
    for (let key in properties) {
        let td = document.createElement("td")
        let item = properties[key]
        td.textContent = cutTextForTable(item)
        td.title = item
        if (item !== null && item !== undefined && typeof item === 'object' && "type" in item) {
            switch (item.type) {
                case "reference":
                    td = makeReference(item.text, item.id, item.object)
                    break;
                case "list":
                    td = makeList(item.list)
                    break;
                case "button":
                    td = makeButton(item, key)
                    break;
                default:

            }

        }
        tr.appendChild(td)
    }
    return tr
}

function makeButton(item, key) {
    let div = makeReference(item.text, item.id, "")
    div.onclick = item.onClick
    div.id = key + item.id
    return div
}

function makeReference(text, id, object) {
    let div = document.createElement("td")
    div.className = "table-reference"
    div.textContent = cutTextForTable(text)
    div.title = text
    div.onclick = () => {
        openNewPage(id, object)
    }
    return div
}

function makeList(list) {
    let div = document.createElement("td")
    let res = ""
    for (let i in list) {
        if (i > 1) break
        res += list[i] + ", "
    }
    res = res.slice(0, -2)
    div.textContent = cutTextForTable(res)
    div.title = res
    return div
}

function textCutter(text, length) {
    if (text && text.length > length) return text.slice(0, length - text.length) + "..."
    return text
}

function cutTextForTable(text) {
    return textCutter(text, 15)
}