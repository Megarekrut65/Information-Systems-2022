function getAllAuthors() {
    return getAll(URLS.authors)
}

function createAuthor(obj) {
    return create(URLS.author, obj)
}

function updateAuthor(obj) {
    return update(URLS.author, obj)
}

function removeAuthor(id) {
    return remove(URLS.author, id)
}

function findAuthorsByName(name) {
    return get(SERVER_URL + "/authors/by-name", { "name": name })
}

function getAuthor(id) {
    return get(URLS.author, { "id": id })
}

function createAuthorForm(title, obj, action) {
    const nameKey = "name",
        dateOfBirthKey = "date-of-birth",
        dateOfDeathKey = "date-of-death"
    const dateOfBirthAuthorKey = "dateOfBirth",
        dateOfDeathAuthorKey = "dateOfDeath"
    return createForm(commandList = {
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Author name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            },
            [dateOfBirthKey]: {
                "type": "date",
                "name": "Date of birth",
                "value": dateOfBirthAuthorKey in obj ? obj[dateOfBirthAuthorKey] : ""
            },
            [dateOfDeathKey]: {
                "type": "date",
                "name": "Date of death",
                "value": dateOfDeathAuthorKey in obj ? obj[dateOfDeathAuthorKey] : ""
            }
        },
        "ok": () => {
            let name = document.getElementById(nameKey)
            let dateOfBirth = document.getElementById(dateOfBirthKey)
            let dateOfDeath = document.getElementById(dateOfDeathKey)
            action({
                [nameKey]: formatText(name.value),
                [dateOfBirthKey]: normalize(dateOfBirth.value),
                [dateOfDeathKey]: normalize(dateOfDeath.value)
            })
        }
    })
}

function createAuthorFormCrate() {
    return createAuthorForm("Create new author", {}, (obj) => create(URLS.author, obj))
}

function createAuthorFormUpdate(id) {
    return createAuthorForm("Update the author", get(URLS.author, { "id": id }), (obj) => {
        obj["id"] = id
        update(URLS.author, obj)
    })
}