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