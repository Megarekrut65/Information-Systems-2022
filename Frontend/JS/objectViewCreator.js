function createObjectView(properties) {
    let viewBox = document.createElement("div")
    viewBox.className = "view-box"
    let title = document.createElement("div")
    viewBox.appendChild(title)
    title.className = "view-title"
    title.textContent = properties.title
    let table = createViewTable(properties)
    viewBox.appendChild(table)
    return viewBox
}

function createViewTable(properties) {
    let table = document.createElement("table")
    table.className = "view-table"
    let tbody = document.createElement("tbody")
    table.appendChild(tbody)
    let fields = properties["fields"]
    let keys = Object.keys(fields)
    for (let key in keys) {
        let tr = document.createElement("tr")
        let tdLabel = document.createElement("td")
        tdLabel.className = "view-label-td"
        let tdItem = document.createElement("td")
        tr.appendChild(tdLabel)
        tr.appendChild(tdItem)
        tbody.appendChild(tr)
        let label = document.createElement("div")
        label.className = "view-label"
        label.textContent = fields[keys[key]].name
        tdLabel.appendChild(label)
        tdItem.appendChild(createField(fields[keys[key]]))
    }
    return table
}

function createField(fieldProperties) {
    let item = document.createElement("div")
    switch (fieldProperties.type) {
        case "reference":
            {
                item.className = "view-reference"
                item.textContent = fieldProperties.value
                item.onclick = () => { fieldProperties.onReference(fieldProperties.id) }
            }
            break;
        case "list":
            createViewList(item, fieldProperties)
            break;
        default:
            {
                item.className = "view-item"
                item.textContent = fieldProperties.value
            }
    }
    return item
}

function createViewList(item, fieldProperties) {
    item.className = "view-list"
    let list = fieldProperties.list
    for (let i in list) {
        item.appendChild(createViewListItem(fieldProperties, list[i]))
    }
    let plus = document.createElement("div")
    plus.className = "view-list-item view-list-item-plus"
    plus.onclick = () => {
        fieldProperties.plus((data) => {
            item.insertBefore(createViewListItem(fieldProperties, data))
        })
    }
    item.appendChild(plus)
}

function createViewListItem(fieldProperties, listObject) {
    let listItem = document.createElement("div")
    listItem.className = "view-list-item"
    let label = document.createElement("div")
    label.textContent = listObject.name
    if ("onReference" in fieldProperties) {
        label.className = "view-reference"
        label.onclick = () => { fieldProperties.onReference(listObject.id) }
    }
    listItem.appendChild(label)
    let remove = document.createElement("div")
    remove.className = "view-list-item-remove"
    remove.onclick = () => {
        fieldProperties.remove(listObject.id)
        listItem.remove()
    }
    listItem.appendChild(remove)
    return listItem
}