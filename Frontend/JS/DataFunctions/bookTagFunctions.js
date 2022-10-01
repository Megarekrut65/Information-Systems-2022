function createBookTagForm(title, obj, action, toSendData) {
    const bookKey = "book-id",
        tagKey = "tag-id"
    return createForm({
        "title": title,
        "inputs": {
            [bookKey]: {
                "type": "text",
                "name": "Book",
                "value": obj.title,
                "readOnly": true
            },
            [tagKey]: {
                "type": "list",
                "name": "Tag",
                "value": "",
                "required": true,
                "list": getAll(URLS.tags),
                "plus": () => {
                    addOptionToList(createTagFormCrate, tagKey)
                }
            }
        },
        "ok": () => {
            toSendData(action({
                [bookKey]: obj.id,
                [tagKey]: getDataFromList(tagKey)
            }))
        }
    })
}

function createBookTagFormCrate(book, toSendData) {
    return createBookTagForm("Add tag to book", book, (obj) => create(URLS.bookTag, obj), toSendData)
}