function loadPage() {
    let url = new URL(window.location)
    let objectName = getData(url, "object")
    let id = getData(url, "id")
    if (id === null) {
        let objectsName = getData(url, "objects")
        loadObjectPage("content", objectName, objectsName)
        return
    }
    let creating = window["create" + objectName + "View"]
    if (typeof creating !== 'function') {
        backAndReload()
        return
    }
    document.getElementById("content").appendChild(creating(window["get" + objectName](id)))
}

function loadObjectPage(parentId, objectName, objectsName) {
    document.getElementById(parentId).appendChild(createObjectPage(window["create" + objectName + "Function"],
        window["get" + objectsName + "ForTable"],
        window["create" + objectsName + "Search"],
        window["create" + objectName + "FormCreate"]))
}

function getData(url, key) {
    let data = url.searchParams.get(key)
    return data;
}

function openNewPage(id, objectName) {
    window.location.href = "blank.html?object=" + objectName + "&id=" + id
}