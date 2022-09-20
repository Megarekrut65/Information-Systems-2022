function getAllTags() {
    return getAll(SERVER_URL + "/tags")
}

function createTag(obj) {
    return create(SERVER_URL + "/tag", obj)
}

function updateTag(obj) {
    return update(SERVER_URL + "/tag", obj)
}

function removeTag(id) {
    return remove(SERVER_URL + "/tag", id)
}

function getTag(id) {
    return get(SERVER_URL + "/tag", { "id": id })
}

function findTagsByName(name) {
    return get(SERVER_URL + "/tags/by-name", { "name": name })
}