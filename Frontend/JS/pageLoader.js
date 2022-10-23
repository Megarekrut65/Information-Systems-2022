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
    let obj = window["get" + objectName](id)
    document.getElementById("content").appendChild(creating(obj))
    if(objectName = "MonthStatistic"){
        let rightSide = document.createElement("div")
        rightSide.className = "rightSide"
        rightSide.id = "rightSide"
        document.getElementById("content").appendChild(rightSide)
        createPieChart("rightSide", getStatisticPieChartProperties(obj))
    }
}
async function loadPrintPage() {
    let url = new URL(window.location)
    let objectsName = getData(url, "objects")
    let creating = getFunction("getAll" + objectsName + "ForTable")
    document.getElementById("content").appendChild(createTable(creating({})))
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