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