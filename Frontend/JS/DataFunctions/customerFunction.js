function getAllCustomers() {
    return getAll(SERVER_URL + "/customers")
}

function createCustomer(obj) {
    return create(SERVER_URL + "/customer", obj)
}

function updateCustomer(obj) {
    return update(SERVER_URL + "/customer", obj)
}

function removeCustomer(id) {
    return remove(SERVER_URL + "/customer", id)
}

function findCustomersByName(name) {
    return get(SERVER_URL + "/customers/by-name", { "name": name })
}

function findCustomersByPhoneNumber(phoneNumber) {
    return get(SERVER_URL + "/customers/by-phone-number", { "phone-number": phoneNumber })
}

function findCustomersByEmail(email) {
    return get(SERVER_URL + "/customers/by-email", { "email": email })
}

function getCustomer(id) {
    return get(SERVER_URL + "/customer", { "id": id })
}

function createCustomerForm(title, obj, action, toSendData) {
    const nameKey = "name",
        phoneKey = "phone-number",
        emailKey = "email",
        addressKey = "address",
        dateOfBirthKey = "date-of-birth"
    const dateOfBirthCustomerKey = "dateOfBirth",
        phoneCustomerKey = "phoneNumber"
    return createForm({
        "title": title,
        "inputs": {
            [nameKey]: {
                "type": "text",
                "name": "Customer name",
                "value": nameKey in obj ? obj[nameKey] : "",
                "required": true
            },
            [phoneKey]: {
                "type": "text",
                "name": "Phone number",
                "value": phoneCustomerKey in obj ? obj[phoneCustomerKey] : "",
                "required": true
            },
            [addressKey]: {
                "type": "text",
                "name": "Address",
                "value": addressKey in obj ? obj[addressKey] : "",
                "required": true
            },
            [emailKey]: {
                "type": "text",
                "name": "Email",
                "value": emailKey in obj ? obj[emailKey] : "",
            },
            [dateOfBirthKey]: {
                "type": "date",
                "name": "Date of birth",
                "value": dateOfBirthCustomerKey in obj ? obj[dateOfBirthCustomerKey] : "",
            },
        },
        "ok": () => {
            let name = document.getElementById(nameKey)
            let phone = document.getElementById(phoneKey)
            let email = document.getElementById(emailKey)
            let address = document.getElementById(addressKey)
            let dateOfBirth = document.getElementById(dateOfBirthKey)
            toSendData(action({
                [nameKey]: formatText(name.value),
                [phoneKey]: formatText(phone.value),
                [emailKey]: formatText(email.value),
                [addressKey]: formatText(address.value),
                [dateOfBirthKey]: normalize(dateOfBirth.value)
            }))
        }
    })
}

function createCustomerFormCreate(toSendData) {
    return createCustomerForm("Add new customer", {}, createFunction(URLS.customer), toSendData)
}

function createCustomerFormUpdate(id) {
    return createCustomerForm("Update the customer", get(URLS.customer, { "id": id }),
        updateFunction(id, URLS.customer), (data) => {})
}

function getCustomersForTable(name, phoneNumber, email) {
    return get(URLS.customersByAll, { "name": name, "phone-number": phoneNumber, "email": email })
        .sort((a, b) => a.name.localeCompare(b.name))
        .map(obj => {
            return {
                "Id": obj.id,
                "Name": {
                    "text": obj.name,
                    "id": obj.id,
                    "object": "Customer",
                    "type": "reference"
                },
                "Phone number": obj.phoneNumber,
                "Email": obj.email,
                "Address": obj.address,
                "Birth date": obj.dateOfBirth
            }
        })
}