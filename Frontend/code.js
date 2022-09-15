function httpGet(theUrl) {
    let xmlHttpReq = new XMLHttpRequest();
    xmlHttpReq.open("GET", theUrl, false);
    xmlHttpReq.send(null);
    return xmlHttpReq.responseText;
}

function loading() {
    alert(httpGet('http://127.0.0.1:8080'))
    alert(httpGet('http://127.0.0.1:8080/hello'))
}