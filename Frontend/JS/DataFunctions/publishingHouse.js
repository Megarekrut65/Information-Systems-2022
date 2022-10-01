function getAllPublishingHouses() {
    return getAll(URLS.publishingHouses)
}

function createPublishingHouse(obj) {
    return create(URLS.publishingHouse, obj)
}

function updatePublishingHouse(obj) {
    return update(URLS.publishingHouse, obj)
}

function removePublishingHouse(id) {
    return remove(URLS.publishingHouse, id)
}

function findPublishingHousesByName(name) {
    return get(SERVER_URL + "/publishing-houses/by-name", { "name": name })
}

function getPublishingHouse(id) {
    return get(URLS.publishingHouse, { "id": id })
}

function createPublishingHouseForm(title, obj, action, toSendData) {
    const nameKey = "name",
        addressKey = "address"
    return createForm({
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Publishing house name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            },
            [addressKey]: {
                "type": "text",
                "name": "Address",
                "value": addressKey in obj ? obj[addressKey] : ""
            }
        },
        "ok": () => {
            toSendData(action({
                [nameKey]: formatText(document.getElementById(nameKey).value),
                [addressKey]: formatText(document.getElementById(addressKey).value)
            }))
        }
    })
}

function createPublishingHouseFormCrate(toSendData) {
    return createPublishingHouseForm("Create new publishing house", {}, createFunction(URLS.publishingHouse), toSendData)
}

function createPublishingHouseFormUpdate(id) {
    return createPublishingHouseForm("Update the publishing house", get(URLS.publishingHouse, { "id": id }),
        updateFunction(id, URLS.publishingHouse), (data) => {})
}

function createPublishingHouseView(obj) {
    const nameKey = "name",
        addressKey = "address"
    return createObjectView({
        "title": "Publishing house",
        "fields": {
            [nameKey]: {
                "type": "text",
                "name": "Name",
                "value": obj[nameKey]
            },
            [addressKey]: {
                "type": "text",
                "name": "Address",
                "value": obj[addressKey]
            }
        },
        "edit": () => {
            document.getElementsByTagName("body")[0].appendChild(createPublishingHouseForm("Update the publishing house", obj,
                updateReloadFunction(obj.id, URLS.publishingHouse)))
        }
    })
}