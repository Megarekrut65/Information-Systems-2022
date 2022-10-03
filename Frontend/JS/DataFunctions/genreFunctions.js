function getAllGenres() {
    return getAll(URLS.genres)
}

function createGenre(obj) {
    return create(URLS.genre, obj)
}

function updateGenre(obj) {
    return update(URLS.genre, obj)
}

function removeGenre(id) {
    return remove(URLS.genre, id)
}

function getGenre(id) {
    return get(URLS.genre, { "id": id })
}

function findGenresByName(name) {
    return get(SERVER_URL + "/genres/by-name", { "name": name })
}

function createGenreForm(title, obj, action, toSendData) {
    const nameKey = "name"
    return createForm(commandList = {
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Genre name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            }
        },
        "ok": () => {
            let name = document.getElementById(nameKey)
            toSendData(action({
                [nameKey]: formatText(name.value)
            }))
        }
    })
}

function createGenreFormCreate(toSendData) {
    return createGenreForm("Create new genre", {}, (obj) => create(URLS.genre, obj), toSendData)
}

function createGenreFormUpdate(id) {
    return createGenreForm("Update the genre", get(URLS.genre, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.genre, obj)
    }, (data) => {})
}