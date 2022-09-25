function formatText(text) {
    if (text === undefined) return text
    return text.replaceAll('+', '%2B')
}

function normalize(obj) {
    if (obj === undefined) return null
    return obj
}

function getMetaDataFromDatalist(datalist, pattern) {
    let data = 0
    for (let i in datalist.options) {
        if (datalist.options[i].value === pattern)
            return data = datalist.options[i].getAttribute("meta-data")
    }
    return null
}