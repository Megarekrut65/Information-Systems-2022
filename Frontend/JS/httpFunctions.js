function getAll(url) {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("GET", url, false)
    xmlHttpReq.send(null)
    return normalizeHttpResponse(xmlHttpReq.responseText)
}

function create(url, obj) {
    return getPostPut(url, obj, "POST")
}

function update(url, obj) {
    return getPostPut(url, obj, "PUT")
}

function remove(url, id) {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("DELETE", makeParametersUrl(url, { "id": id }), false)
    xmlHttpReq.send(null)
    return normalizeHttpResponse(xmlHttpReq.responseText)
}

function get(url, obj) {
    return getPostPut(url, obj, "GET")
}

function getPostPut(url, obj, type) {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open(type, makeParametersUrl(url, obj), false)
    xmlHttpReq.send(null)
    return normalizeHttpResponse(xmlHttpReq.responseText)
}

function makeParametersUrl(url, obj) {
    let res = url + "?"
    let keys = Object.keys(obj)
    for (let i in keys) {
        res += keys[i] + "=" + obj[keys[i]] + "&"
    }
    return res.slice(0, -1)
}

function normalizeHttpResponse(str) {
    if (str === undefined || str === "" || str === null) return ""
    let res = JSON.parse(str)
    if (typeof res === "object" && "trace" in res && res.message === "Bad operation") addToBody(createErrorBox(res.trace.split("!")[0].split(":")[1] + "!"))
    return res
}