function getAllGenres() {
    return getAll(SERVER_URL + "/genres")
}

function createGenre(obj) {
    return create(SERVER_URL + "/genre", obj)
}

function updateGenre(obj) {
    return update(SERVER_URL + "/genre", obj)
}

function removeGenre(id) {
    return remove(SERVER_URL + "/genre", id)
}

function getGenre(id) {
    return get(SERVER_URL + "/genre", { "id": id })
}

function findGenresByName(name) {
    return get(SERVER_URL + "/genres/by-name", { "name": name })
}