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