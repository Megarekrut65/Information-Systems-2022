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

function createGenreForm(title, obj, action, toSendData = (data) => {}) {
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
    return createGenreForm("Create new genre", {}, createFunction(URLS.genre), toSendData)
}

function createGenreFormUpdate(id, toSendData = (data) => {}) {
    return createGenreForm("Update the genre", get(URLS.genre, { "id": id }), updateFunction(id, URLS.genre), toSendData)
}

function getGenresForTable(item) {
    item = normalizeItem(item, ["name"])
    return genresToTableProperties((page, perPage)=>getPage(URLS.genresByAllPage, page, perPage, item))
}
function getAllGenresForTable() {
    return genresToTableProperties(()=>getAll(URLS.genres))
}
function genresToTableProperties(getter) {
    let convector = obj => {
        return {
            "Id": obj.id,
            "Name": {
                "text": obj.name,
                "id": obj.id,
                "type": "button",
                "onClick": () => {
                    addToBody(createGenreFormUpdate(obj.id, (data) => {
                        document.getElementById("Name" + obj.id).textContent = cutTextForTable(data.name)
                    }))
                }
            }
        }
    }
    return {
        "convector":convector,
        "getter":getter
    }
}
function createGenresSearch(recreateTable) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Genre name..."
            }
        },
        "createTable": (item)=>recreateTable(getGenresForTable(item)),
        "formCreate": createGenreFormCreate,
        "title": "Genres"
    }
}

function createGenreFunction(toSendData) {
    return null
}