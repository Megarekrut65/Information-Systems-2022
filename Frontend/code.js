function httpGet(theUrl) {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("GET", theUrl, false)
    xmlHttpReq.send(null)
    return xmlHttpReq.responseText
}

function loading() {
    alert(httpGet('http://127.0.0.1:8080'))
    alert(httpGet('http://127.0.0.1:8080/hello'))
}

function post() {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("POST", "http://127.0.0.1:8080/genre?name=History", true)
    xmlHttpReq.send(null)
    alert(xmlHttpReq.responseText)
}

function getAll() {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("GET", "http://127.0.0.1:8080/genre", false)
    xmlHttpReq.send(null)
    alert(xmlHttpReq.responseText)
}

function update() {
    let xmlHttpReq = new XMLHttpRequest()
    xmlHttpReq.open("PUT", "http://127.0.0.1:8080/genre?id=11&name=west", false)
    xmlHttpReq.send(null)
    alert(xmlHttpReq.response)
}