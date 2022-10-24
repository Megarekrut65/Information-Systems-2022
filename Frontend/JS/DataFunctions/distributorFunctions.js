function getAllDistributors() {
    return getAll(SERVER_URL + "/distributors")
}

function createDistributor(obj) {
    return create(SERVER_URL + "/distributor", obj)
}

function updateDistributor(obj) {
    return update(SERVER_URL + "/distributor", obj)
}

function removeDistributor(id) {
    return remove(SERVER_URL + "/distributor", id)
}

function findDistributorsByName(name) {
    return get(SERVER_URL + "/distributors/by-name", { "name": name })
}

function getDistributor(id) {
    return get(SERVER_URL + "/distributor", { "id": id })
}

function createDistributorForm(title, obj, action, toSendData = (data) => {}) {
    const nameKey = "name",
        phoneKey = "phone-number",
        address = "address"
    const phoneObjectKey = "phoneNumber"
    return createForm({
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Distributor name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            },
            [phoneKey]: {
                "type": "text",
                "name": "Phone number",
                "value": phoneObjectKey in obj ? obj[phoneObjectKey] : "",
                "required": true
            },
            [address]: {
                "type": "text",
                "name": "Address",
                "value": address in obj ? obj[address] : ""
            }
        },
        "ok": () => {
            toSendData(action({
                [nameKey]: formatText(document.getElementById(nameKey).value),
                [phoneKey]: formatText(document.getElementById(phoneKey).value),
                [address]: formatText(document.getElementById(address).value)
            }))
        }
    })
}

function createDistributorFormCreate(toSendData) {
    return createDistributorForm("Create new distributor", {}, createFunction(URLS.distributor), toSendData)
}

function createDistributorFormUpdate(id) {
    return createDistributorForm("Update the distributor", get(URLS.distributor, { "id": id }),
        updateFunction(id, URLS.distributor), (data) => {})
}

function createDistributorView(obj) {
    const nameKey = "name",
        phoneKey = "phoneNumber",
        address = "address"
    return createObjectView({
        "title": "Distributor",
        "fields": {
            [nameKey]: {
                "type": "text",
                "name": "Name",
                "value": obj[nameKey]
            },
            [phoneKey]: {
                "type": "text",
                "name": "Phone number",
                "value": obj[phoneKey]
            },
            [address]: {
                "type": "text",
                "name": "Address",
                "value": obj[address]
            }
        },
        "edit": () => {
            addToBody(createDistributorForm("Update the distributor", obj,
                updateReloadFunction(obj.id, URLS.distributor)))
        },
        "tables": {
            "Deliveries": deliveriesToTableProperties((page, perPage)=>getPage(URLS.deliveriesByDistributorPage, page, perPage,
                { "distributor-id": obj.id }))
        }
    })
}

function getDistributorsForTable(item) {
    item = normalizeItem(item, ["name"])
    return distributorsToTableProperties((page, perPage)=>getPage(URLS.distributorsByAllPage, page, perPage, item))
}
function getAllDistributorsForTable(){
    return distributorsToTableProperties(()=>getAll(URLS.distributors))
}
function distributorsToTableProperties(getter) {
    let convector = obj => {
        return {
            "Id": obj.id,
            "Name": {
                "text": obj.name,
                "id": obj.id,
                "object": "Distributor",
                "type": "reference"
            },
            "Phone number": obj.phoneNumber,
            "Address": obj.address
        }
    }
    return {
        "convector":convector,
        "getter":getter
    }
}
function createDistributorsSearch(recreateTable) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Distributor name..."
            }
        },
        "createTable": (item)=>recreateTable(getDistributorsForTable(item)),
        "formCreate": createDistributorFormCreate,
        "title": "Distributors"
    }
}

function createDistributorFunction(toSendData) {
    return null
}