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

function createTagForm(title, obj, action, toSendData) {
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

function createTagFormCrate(toSendData) {
    return createTagForm("Create new tag", {}, (obj) => create(URLS.tag, obj), toSendData)
}

function createTagFormUpdate(id) {
    return createTagForm("Update the tag", get(URLS.tag, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.tag, obj)
    }, (data) => data)
}