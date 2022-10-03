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

function createDeliveryForm(title, obj, action, toSendData) {
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

function getDeliveriesForTable(distributorName, minDate, maxDate) {
    return get(URLS.deliveriesByAll, {
            "distributor-name": distributorName,
            "date-of-delivery-min": minDate,
            "date-of-delivery-max": maxDate
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