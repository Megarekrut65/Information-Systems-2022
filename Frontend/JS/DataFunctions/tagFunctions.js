function getAllTags() {
    return getAll(URLS.tags)
}

function createTag(obj) {
    return create(URLS.tag, obj)
}

function updateTag(obj) {
    return update(URLS.tag, obj)
}

function removeTag(id) {
    return remove(URLS.tag, id)
}

function getTag(id) {
    return get(URLS.tag, { "id": id })
}

function findTagsByName(name) {
    return get(SERVER_URL + "/tags/by-name", { "name": name })
}

function createTagForm(title, obj, action, toSendData = (data) => {}) {
    const nameKey = "name"
    return createForm(commandList = {
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Tag name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            }
        },
        "ok": () => {
            let name = document.getElementById(nameKey)
            toSendData(action({
                [nameKey]: formatText(name.value)
            }))
        }
    })
}

function createTagFormCreate(toSendData) {
    return createTagForm("Create new tag", {}, createFunction(URLS.tag), toSendData)
}

function createTagFormUpdate(id, toSendData = (data) => {}) {
    return createTagForm("Update the tag", get(URLS.tag, { "id": id }), updateFunction(id, URLS.tag), toSendData)
}

function getTagsForTable(item) {
    item = normalizeItem(item, ["name"])
    return get(URLS.tagsByName, item).sort((a, b) => a.name.localeCompare(b.name))
        .map(obj => {
            return {
                "Id": obj.id,
                "Name": {
                    "text": obj.name,
                    "id": obj.id,
                    "type": "button",
                    "onClick": () => {
                        addToBody(createTagFormUpdate(obj.id, (data) => {
                            document.getElementById("Name" + obj.id).textContent = cutTextForTable(data.name)
                        }))
                    }
                }
            }
        })
}

function createTagsSearch(createTable, formCreate) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Tag name..."
            }
        },
        "createTable": createTable,
        "formCreate": formCreate,
        "title": "Tags"
    }
}

function createTagFunction(toSendData) {
    return null
}