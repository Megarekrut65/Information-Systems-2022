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