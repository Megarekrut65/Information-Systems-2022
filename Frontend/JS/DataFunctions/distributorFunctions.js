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