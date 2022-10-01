function formatText(text) {
    if (text === undefined) return text
    return text.replaceAll('+', '%2B')
}

function normalize(obj) {
    if (obj === undefined) return null
    return obj
}

function createFunction(url) {
    return (obj) => {
        return create(url, obj)
    }
}

function updateFunction(id, url) {
    return (obj) => {
        obj.id = id
        return update(url, obj)
    }
}

function updateReloadFunction(id, url) {
    return (obj) => {
        obj["id"] = id
        update(url, obj)
        location.reload()
    }
}

function backAndReload() {
    if ('referrer' in document) {
        window.location = document.referrer;
        return
    }
    window.history.back();
}