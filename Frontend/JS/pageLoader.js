async function loadPage() {
    let url = new URL(window.location)
    let objectName = getData(url, "object")
    let id = getData(url, "id")
    if (id === null) {
        let objectsName = getData(url, "objects")
        loadObjectPage("content", objectName, objectsName)
        return
    }
    let creating = getFunction("create" + objectName + "View")
    document.getElementById("content").appendChild(creating(window["get" + objectName](id)))
}

async function loadObjectPage(parentId, objectName, objectsName) {
    document.getElementById(parentId).appendChild(createObjectPage(
        getFunction("create" + objectName + "Function"),
        getFunction("create" + objectsName + "Search")))
}

function getFunction(name) {
    let func = window[name]
    if (typeof func !== 'function') {
        console.log(name)
        backAndReload()
        return null
    }
    return func
}

function getData(url, key) {
    let data = url.searchParams.get(key)
    return data;
}

function openNewPage(id, objectName) {
    window.location.href = "blank.html?object=" + objectName + "&id=" + id
}

function openNewListPage(objectName, objectsName) {
    if (objectsName === undefined) objectsName = objectName + "s"
    window.location.href = "blank.html?object=" + objectName + "&objects=" + objectsName
}