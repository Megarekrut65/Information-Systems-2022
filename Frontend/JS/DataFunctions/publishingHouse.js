function getAllPublishingHouses() {
    return getAll(SERVER_URL + "/publishing-houses")
}

function createPublishingHouse(obj) {
    return create(SERVER_URL + "/publishing-house", obj)
}

function updatePublishingHouse(obj) {
    return update(SERVER_URL + "/publishing-house", obj)
}

function removePublishingHouse(id) {
    return remove(SERVER_URL + "/publishing-house", id)
}

function findPublishingHousesByName(name) {
    return get(SERVER_URL + "/publishing-houses/by-name", { "name": name })
}

function getPublishingHouse(id) {
    return get(SERVER_URL + "/publishing-house", { "id": id })
}