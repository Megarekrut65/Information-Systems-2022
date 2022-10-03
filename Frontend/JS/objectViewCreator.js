function createObjectView(properties) {
    let viewBox = document.createElement("div")
    viewBox.className = "view-box"
    let titleContainer = document.createElement("div")
    viewBox.appendChild(titleContainer)
    titleContainer.className = "view-title-container"
    let title = document.createElement("div")
    titleContainer.appendChild(title)
    title.className = "view-title"
    title.textContent = properties.title
    let edit = document.createElement("div")
    titleContainer.appendChild(edit)
    edit.className = "view-edit"
    edit.onclick = properties.edit
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
                item.onclick = () => { fieldProperties.onReference() }
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
            item.insertBefore(createViewListItem(fieldProperties, fieldProperties.converter(data)), plus)
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

function createList(name, obj, list, removeUrl, formCreateFunction, converter) {
    return {
        "type": "list",
        "name": name,
        "list": list,
        "remove": (id) => {
            remove(removeUrl, id)
        },
        "plus": (toSendData) => {
            let parent = document.getElementsByTagName("body")[0]
            parent.appendChild(formCreateFunction(obj, toSendData))
        },
        "converter": converter
    }
}

function createReference(name, value, obj, functionName) {
    return {
        "type": "reference",
        "name": name,
        "value": value,
        "onReference": () => {
            openNewPage(obj.id, functionName)
        }
    }
}

function createDetails(title, obj) {
    let details = document.createElement("details")
    details.open = true
    let summary = document.createElement("summary")
    summary.textContent = title
    summary.className = "summary-style"
    details.appendChild(summary)
    details.appendChild(obj)
    return details
}