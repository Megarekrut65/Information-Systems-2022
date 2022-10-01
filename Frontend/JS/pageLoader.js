function loadPage() {
    let url = new URL(window.location)
    let objectName = getData(url, "object")
    let id = getData(url, "id")
    let creating = window["create" + objectName + "View"]
    if (typeof creating !== 'function') {
        backAndReload()
        return
    }
    document.getElementById("content").appendChild(creating(window["get" + objectName](id)))
}

function getData(url, key) {
    let data = url.searchParams.get(key)
    return data;
}

function openNewPage(id, objectName) {
    window.location.href = "blank.html?object=" + objectName + "&id=" + id
}