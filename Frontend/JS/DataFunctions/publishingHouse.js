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

function createPublishingHouseForm(title, obj, action) {
    const nameKey = "name",
        addressKey = "address"
    return createForm(commandList = {
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
            let name = document.getElementById(nameKey)
            let address = document.getElementById(addressKey)
            action({
                [nameKey]: formatText(name.value),
                [addressKey]: formatText(address.value)
            })
        }
    })
}

function createPublishingHouseFormCrate() {
    return createPublishingHouseForm("Create new publishing house", {}, (obj) => create(URLS.publishingHouse, obj))
}

function createPublishingHouseFormUpdate(id) {
    return createPublishingHouseForm("Update the publishing house", get(URLS.publishingHouse, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.publishingHouse, obj)
    })
}