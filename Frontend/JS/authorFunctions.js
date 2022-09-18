function getAllAuthors() {
    return getAll(SERVER_URL + "/authors")
}

function createAuthor(obj) {
    return create(SERVER_URL + "/author", obj)
}

function updateAuthor(obj) {
    return update(SERVER_URL + "/author", obj)
}

function removeAuthor(id) {
    return remove(SERVER_URL + "/author", id)
}

function findAuthorsByName(name) {
    return get(SERVER_URL + "/authors/by-name", { "name": name })
}

function getAuthor(id) {
    return get(SERVER_URL + "/author", { "id": id })
}