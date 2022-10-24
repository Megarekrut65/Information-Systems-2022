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

function createPublishingHouseForm(title, obj, action, toSendData = (data) => {}) {
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

function createPublishingHouseFormCreate(toSendData) {
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
            addToBody(createPublishingHouseForm("Update the publishing house", obj,
                updateReloadFunction(obj.id, URLS.publishingHouse)))
        },
        "tables": {
            "Books": booksToTableProperties((page, perPage)=>getPage(URLS.booksByPublishingHousePage,page, perPage,
                { "publishing-house-id": obj.id }))
        }
    })
}

function getPublishingHousesForTable(item) {
    item = normalizeItem(item, ["name"])
    return publishingHousesToTableProperties((page, perPage)=>getPage(URLS.publishingHousesByAllPage,page, perPage, item))
}
function getAllPublishingHousesForTable(){
    return publishingHousesToTableProperties(()=>getAll(URLS.publishingHouses))
}
function publishingHousesToTableProperties(getter) {
    let convector = obj => {
        return {
            "Id": obj.id,
            "Name": {
                "text": obj.name,
                "id": obj.id,
                "type": "reference",
                "object": "PublishingHouse"
            },
            "Address": obj.address
        }
    }
    return {
        "convector":convector,
        "getter":getter
    }
}
function createPublishingHousesSearch(recreateTable) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Publishing house name..."
            }
        },
        "createTable": (item)=>recreateTable(getPublishingHousesForTable(item)),
        "formCreate": createPublishingHouseFormCreate,
        "title": "Publishing houses"
    }
}

function createPublishingHouseFunction(toSendData) {
    return null
}