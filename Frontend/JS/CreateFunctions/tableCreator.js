function createTable(properties) {
    let table = document.createElement("table")
    table.className = "table-style"
    table.appendChild(createHead(properties[0]))
    for (let i in properties) {
        table.appendChild(createRow(properties[i], i % 2 === 0 ? "tr-style1" : "tr-style2"))
    }
    return table
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
        td.textContent = textCutter(item, 15)
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
    div.textContent = textCutter(res, 15)
    return div
}

function textCutter(text, length) {
    if (text && text.length > length) return text.slice(0, length - text.length) + "..."
    return text
}

function cutTextForTable(text) {
    return textCutter(text, 15)
}