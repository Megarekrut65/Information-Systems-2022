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

function createAuthorForm(title, obj, action, toSendData = (data) => {}) {
    const nameKey = "name",
        dateOfBirthKey = "date-of-birth",
        dateOfDeathKey = "date-of-death"
    const dateOfBirthAuthorKey = "dateOfBirth",
        dateOfDeathAuthorKey = "dateOfDeath"
    return createForm({
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
            toSendData(action({
                [nameKey]: formatText(name.value),
                [dateOfBirthKey]: normalize(dateOfBirth.value),
                [dateOfDeathKey]: normalize(dateOfDeath.value)
            }))
        }
    })
}

function createAuthorFormCreate(toSendData) {
    return createAuthorForm("Create new author", {}, createFunction(URLS.author), toSendData)
}

function createAuthorFormUpdate(id) {
    return createAuthorForm("Update the author", get(URLS.author, { "id": id }), updateFunction(id, URLS.author), (data) => {})
}

function createAuthorView(obj) {
    const nameKey = "name",
        dateOfBirthKey = "dateOfBirth",
        dateOfDeathKey = "dateOfDeath"
    return createObjectView({
        "title": "Author",
        "fields": {
            [nameKey]: {
                "type": "text",
                "name": "Name",
                "value": obj[nameKey]
            },
            [dateOfBirthKey]: {
                "type": "text",
                "name": "Date of birth",
                "value": obj[dateOfBirthKey]
            },
            [dateOfDeathKey]: {
                "type": "text",
                "name": "Date of death",
                "value": obj[dateOfDeathKey]
            }
        },
        "edit": () => {
            addToBody(createAuthorForm("Update the author", obj, updateReloadFunction(obj.id, URLS.author)))
        },
        "tables": {
            "Books": booksToTableProperties(get(URLS.authorshipByAuthor, { "author-id": obj.id }).map(item => item.book))
        }
    })
}

function getAuthorsForTable(item) {
    item = normalizeItem(item, ["name", "title"])
    return authorsToTableProperties((page, perPage)=>
    getPage(URLS.authorsByAllPage, page, perPage, { "author-name": item.name, "book-title": item.title }))
}
function getAllAuthorsForTable() {
    return authorsToTableProperties(()=>getAll(URLS.authors))
}
function authorsToTableProperties(getter) {
    let convector = obj => {
            return {
                "Id": obj.id,
                "Name": {
                    "text": obj.name,
                    "id": obj.id,
                    "object": "Author",
                    "type": "reference"
                },
                "Birth date": obj.dateOfBirth,
                "Death date": obj.dateOfDeath,
                "Books": {
                    "type": "list",
                    "list": get(URLS.authorshipByAuthor, { "author-id": obj.id }).map(item => item.book.title)
                }
            }
        }
    return {
        "convector":convector,
        "getter":getter
    }
}

function createAuthorsSearch(recreateTable) {
    return {
        "inputs": {
            "name": {
                "type": "text",
                "placeholder": "Author name..."
            },
            "title": {
                "type": "text",
                "placeholder": "Books title..."
            }
        },
        "createTable": (item)=>recreateTable(getAuthorsForTable(item)),
        "formCreate": createAuthorFormCreate,
        "title": "Authors"
    }
}

function createAuthorFunction(toSendData) {
    return {
        "buttons": {
            "Add authorship": createAuthorshipFormCreate
        },
        "toSendData": toSendData
    }
}