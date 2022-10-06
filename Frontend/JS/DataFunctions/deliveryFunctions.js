function getAllDeliveries() {
    return getAll(SERVER_URL + "/deliveries")
}

function createDelivery(obj) {
    return create(SERVER_URL + "/delivery", obj)
}

function updateDelivery(obj) {
    return update(SERVER_URL + "/delivery", obj)
}

function removeDelivery(id) {
    return remove(SERVER_URL + "/delivery", id)
}


function findDeliveriesByDistributor(distributorId) {
    return get(SERVER_URL + "/deliveries/by-distributor-id", { "distributor-id": distributorId })
}

function findDeliveriesByDatePeriod(dateOfDeliveryMin, dateOfDeliveryMax) {
    return get(SERVER_URL + "/deliveries/by-date-of-delivery-period", { "date-of-delivery-min": dateOfDeliveryMin, "date-of-delivery-max": dateOfDeliveryMax })
}

function getDelivery(id) {
    return get(SERVER_URL + "/delivery", { "id": id })
}

function createDeliveryForm(title, obj, action, toSendData = (data) => {}) {
    const dateOfDeliveryKey = "date-of-delivery",
        distributorIdKey = "distributor-id"
    const dateOfDeliveryObjectKey = "dateOfDelivery",
        distributorKey = "distributor"
    return createForm({
        "title": title,
        "inputs": {
            [dateOfDeliveryKey]: {
                "type": "date",
                "name": "Date",
                "value": dateOfDeliveryObjectKey in obj ? obj[dateOfDeliveryObjectKey] : "",
                "required": true
            },
            [distributorIdKey]: {
                "type": "list",
                "name": "Distributor",
                "value": distributorKey in obj ? obj[distributorKey]["id"] : "",
                "required": true,
                "list": getAll(URLS.distributors),
                "plus": (convector) => {
                    addOptionToList(createDistributorFormCreate, distributorIdKey, convector)
                }
            }
        },
        "ok": () => {
            toSendData(action({
                [dateOfDeliveryKey]: normalize(document.getElementById(dateOfDeliveryKey).value),
                [distributorIdKey]: getDataFromList(distributorIdKey)
            }))
        }
    })
}

function createDeliveryFormCreate(toSendData) {
    return createDeliveryForm("Create new delivery", {}, createFunction(URLS.delivery), toSendData)
}

function createDeliveryFormUpdate(id) {
    return createDeliveryForm("Update the delivery", get(URLS.delivery, { "id": id }),
        updateFunction(id, URLS.delivery), (data) => {})
}

function createDeliveryView(obj) {
    const dateOfDeliveryObjectKey = "dateOfDelivery",
        distributorKey = "distributor"
    return createObjectView({
        "title": "Delivery",
        "fields": {
            [dateOfDeliveryObjectKey]: {
                "type": "text",
                "name": "Date of delivery",
                "value": obj[dateOfDeliveryObjectKey]
            },
            [distributorKey]: createReference("Distributor", obj[distributorKey].name,
                obj[distributorKey], "Distributor")
        },
        "edit": () => {
            addToBody(createDeliveryForm("Update the delivery", obj, updateReloadFunction(obj.id, URLS.delivery)))
        },
        "tables": {
            "Book deliveries": bookDeliveriesToTableProperties(
                get(URLS.bookDeliveriesByDelivery, { "delivery-id": obj.id }))
        }
    })
}

function getDeliveriesForTable(item) {
    item = normalizeItem(item, ["name", "min", "max"])
    return get(URLS.deliveriesByAll, {
            "distributor-name": item.name,
            "date-of-delivery-min": item.min,
            "date-of-delivery-max": item.max
        })
        .sort((a, b) => b.dateOfDelivery.localeCompare(a.dateOfDelivery))
        .map(obj => {
            let bookDeliveries = get(URLS.bookDeliveriesByDelivery, { "delivery-id": obj.id });
            return {
                "Id": obj.id,
                "Date": {
                    "text": obj.dateOfDelivery,
                    "id": obj.id,
                    "object": "Delivery",
                    "type": "reference"
                },
                "Distributor": {
                    "text": obj.distributor.name,
                    "id": obj.distributor.id,
                    "object": "Distributor",
                    "type": "reference"
                },
                "Book count": bookDeliveries.map(bookDelivery => bookDelivery.bookCount).reduce((a, b) => a + b, 0),
                "Total price": bookDeliveries.map(bookDelivery => bookDelivery.bookCount * bookDelivery.bookPrice).reduce((a, b) => a + b, 0)
            }
        })
}

function createDeliveriesSearch(createTable, formCreate) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Distributor name..."
            },
            "min": {
                "type": "date",
                "label": "Begin"
            },
            "max": {
                "type": "date",
                "label": "End"
            }
        },
        "createTable": createTable,
        "formCreate": formCreate,
        "title": "Deliveries"
    }
}

function createDeliveryFunction(toSendData) {
    return {
        "buttons": {
            "Book delivery list": (date) => openNewListPage('BookDelivery', 'BookDeliveries')
        },
        "toSendData": toSendData
    }
}