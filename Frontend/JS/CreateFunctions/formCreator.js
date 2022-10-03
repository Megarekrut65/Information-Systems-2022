function createForm(properties) {
    let formBox = document.createElement("div")
    formBox.className = "form-box"
    let title = document.createElement("div")
    formBox.appendChild(title)
    title.textContent = properties.title
    title.className = "form-title"
    let form = document.createElement("form")
    formBox.appendChild(form)
    form.className = "form-style"
    form.appendChild(createTablePart(properties))
    form.appendChild(createSubmitPart(() => { formBox.remove() }))
    form.onsubmit = () => {
        properties.ok()
        formBox.remove()
        return false
    }
    return formBox
}


function createSubmitPart(cancelFunction) {
    let submitBox = document.createElement("div")
    submitBox.className = "form-submit-box"

    let submitOk = document.createElement("input")
    submitBox.appendChild(submitOk)
    submitOk.className = "form-submit form-ok"
    submitOk.type = "submit"
    submitOk.value = "Ok"
    let submitCancel = document.createElement("input")
    submitBox.appendChild(submitCancel)
    submitCancel.className = "form-submit form-cancel"
    submitCancel.type = "button"
    submitCancel.value = "Cancel"
    submitCancel.onclick = cancelFunction

    return submitBox
}

function createTablePart(properties) {
    let table = document.createElement("table")
    table.className = "form-table"
    let tbody = document.createElement("tbody")
    table.appendChild(tbody)
    let inputs = properties["inputs"]
    let keys = Object.keys(inputs)
    for (let key in keys) {
        let tr = document.createElement("tr")
        let td1 = document.createElement("td")
        let td2 = document.createElement("td")
        td2.className = "form-item"
        tr.appendChild(td1)
        tr.appendChild(td2)
        let label = document.createElement("label")
        label.textContent = inputs[keys[key]].name
        td1.appendChild(label)
        createInput(keys[key], inputs[keys[key]], td2)
        tbody.appendChild(tr)
    }
    return table
}

function createInput(id, inputProperties, parent) {
    let input = document.createElement("input")
    parent.appendChild(input)
    input.id = id
    input.name = id
    if ("required" in inputProperties) input.required = inputProperties.required
    if ("readOnly" in inputProperties) input.readOnly = inputProperties.readOnly
    let type = inputProperties.type
    switch (type) {
        case "list":
            createInputList(input, id, inputProperties, parent)
            break;
        case "number":
            {
                if ("min" in inputProperties) input.min = inputProperties.min
                if ("max" in inputProperties) input.max = inputProperties.max
            }
        default:
            {
                input.type = type
                if ("value" in inputProperties) input.value = inputProperties.value
            }
    }

}

function createInputList(input, id, inputProperties, parent) {
    if (!("convector" in inputProperties)) inputProperties.convector = item => item
    inputProperties.list = inputProperties.list.map(inputProperties.convector)
    input.autocomplete = "off"
    let datalist = document.createElement("datalist")
    datalist.id = id + "-datalist"
    input.setAttribute('list', datalist.id);
    for (let i in inputProperties.list) {
        let item = inputProperties.list[i]
        let option = createDatalistOption(item)
        datalist.appendChild(option)
        if ("value" in inputProperties && inputProperties.value === item.id) input.value = option.value
    }
    parent.appendChild(datalist)
    if ("plus" in inputProperties) {
        let plus = document.createElement("input")
        plus.type = "button"
        plus.value = "+"
        plus.onclick = () => inputProperties.plus(inputProperties.convector)
        parent.appendChild(plus)
    }
}

function createDatalistOption(item) {
    let option = document.createElement("option")
    option.value = item.id + " - " + item.name
    option.setAttribute('meta-data', item.id);
    return option
}

function addOptionToList(creatingFunction, key, convector) {
    addToBody(creatingFunction((data) => {
        let item = createDatalistOption(convector(data))
        document.getElementById(key + "-datalist").appendChild(item)
        document.getElementById(key).value = item.value
    }))
}

function getDataFromList(key) {
    let item = document.getElementById(key)
    let list = document.getElementById(key + "-datalist")
    return getMetaDataFromDatalist(list, item.value)
}

function getMetaDataFromDatalist(datalist, pattern) {
    for (let i in datalist.options) {
        if (datalist.options[i].value === pattern)
            return datalist.options[i].getAttribute("meta-data")
    }
    return null
}

function errorFormCreate(title, cause) {
    return createForm({
        "title": title,
        "inputs": {
            "error": {
                "type": "text",
                "value": cause,
                "name": "Error",
                "readOnly": true
            }
        },
        "ok": () => {}
    })
}