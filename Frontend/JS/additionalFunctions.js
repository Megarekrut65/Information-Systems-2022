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
    window.history.back();
}

function getToday() {
    var local = new Date();
    local.setMinutes(local.getMinutes() - local.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
}

function getTextDate(local) {
    local.setMinutes(local.getMinutes() - local.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
}

function addToBody(obj) {
    document.getElementsByTagName("body")[0].appendChild(obj)
}

function normalizeItem(item, keys) {
    for (let i in keys) {
        if (!(keys[i] in item)) item[keys[i]] = ""
    }
    return item
}