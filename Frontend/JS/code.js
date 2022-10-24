function generateData(){
    //generateName(count)
    //generateHouse()
    //generateBook()
    // generateBook()
    // let books = getAll(URLS.books)
    // for(let i in books){
    //     remove(URLS.book, books[i].id)
    // }
    // let houses = getAll(URLS.publishingHouses)
    // for(let i in houses){
    //     remove(URLS.publishingHouse, houses[i].id)
    // }
    // let books = getAll(URLS.books)
    // let genres = getAll(URLS.genres)
    // let tags = getAll(URLS.tags)
    // for(let i in books){
    //     let book = books[i]
    //     let count = Math.floor((Math.random() * 5))
    //     for(let j = 0; j < count; j++){
    //         let genre = pickRandom(genres)
    //         create(URLS.bookGenre, {"book-id":book.id, "genre-id":genre.id})
    //     }
    //     count = Math.floor((Math.random() * 3))
    //     for(let j = 0; j < count; j++){
    //         let tag = pickRandom(tags)
    //         create(URLS.bookTag, {"book-id":book.id, "tag-id":tag.id})
    //     }
    // }
    // let books = getAll(URLS.books)
    // let authors = getAll(URLS.authors)
    // for(let i in books){
    //     let book = books[i]
    //     let count = Math.floor((Math.random() * 3))
    //     for(let j = 0; j < count; j++){
    //         let author = pickRandom(authors)
    //         create(URLS.authorship, {"book-id":book.id, "author-id":author.id, "author-role":pickRandom(["Main", "Other"])})
    //     }
    // }
    // let nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]
    // fetch(`../names-surnames.json`)
    //     .then(response => {
    //        return response.json()
    //     }).then(surname=>{
    //         for(let i = 0; i < 15; i++){
    //             let name = pickRandom(surname.data) + pickRandom([" AM", " PR", " Corparation", " TMK", "-Shop"])
    //             create(URLS.distributor, {"name":name,
    //              "phone-number":createPhoneNumber([3,8,0,pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums),pickRandom(nums)]),
    //             "address":createAdress()})
    //         }
    //     }) 
    let distributors = getAll(URLS.distributors)
    for(let i = 0; i < 100;i++){
        let distributor = pickRandom(distributors)
        let date = getTextDate(randomDate(new Date(2020, 1, 1), new Date()))
        create(URLS.delivery, {"distributor-id":distributor.id, "date-of-delivery": date})
    }
}
  
  function pickRandom(list) {
    return list[Math.floor(Math.random() * list.length)];
  }
  function randomDate(start, end) {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  }
  function createPhoneNumber(arr) {
    return `(${arr.slice(0, 3).join('')}) ${arr.slice(3, 6).join('')}-${arr.slice(6).join('')}`;
}var streetNumber = ['25489', '87459', '35478', '15975', '95125', '78965']

var streetName = ['A street', 'B street', 'C street', 'D street', 'E street', 'F street',]

var cityName = ['Riyadh', 'Dammam', 'Jedda', 'Tabouk', 'Makka', 'Maddena', 'Haiel']

var stateName = ['Qassem State', 'North State', 'East State', 'South State', 'West State']

var zipCode = ['28889', '96459', '35748', '15005', '99625', '71465']

function getRandom(input) {
    return input[Math.floor((Math.random() * input.length))];
}

function createAdress() {
    return `${getRandom(streetNumber)} ${getRandom(streetName)} ${getRandom(cityName)} ${getRandom(stateName)} ${getRandom(zipCode)}`;
}
  async function generateName(count) {
    fetch(`../names-male.json`)
    .then(response => {
       return response.json()
    }).then(male=>{
        fetch(`../names-female.json`)
        .then(response => {
            return response.json()
        }).then(female=>{
            fetch(`../names-surnames.json`)
        .then(response => {
           return response.json()
        }).then(surname=>{
            for(let i = 0; i < count; i++){
                let name = pickRandom([1,2]) == 1?pickRandom(male.data):pickRandom(female.data)
                let sur = pickRandom(surname.data)
                let date = getTextDate(randomDate(new Date(1800, 1, 1), new Date()))
                create(URLS.customer, {"name":name + " " + sur, "date-of-birth":date,
                 "phone-number":createPhoneNumber([1, 2, 3, 4, 5, 6, 7, 8, 9, 0]),
                "address":createAdress(), "email":""})
            }
        }) 
        })
    })  
  }
  function generateBook(){
    fetch(`../books2.json`)
        .then(response => {
           return response.json()
        }).then(books=>{
            let names = [] 
            for(let i in books){
                names.push(books[i].title)
            }
            let houses = getAll(URLS.publishingHouses)
            for(let i = 0; i < books.length; i++){
                let name = names[i].substring(0, 49)
                let year = randomDate(new Date(1800, 1, 1), new Date()).getFullYear()
                let house = pickRandom(houses)
                create(URLS.book, {"title":name,
                 "publish-year":year,
                "publishing-house-id":house.id, "comment":""})
            }
        }) 
  }
  function generateHouse(){
    fetch(`../houses.json`)
    .then(response => {
       return response.json()
    }).then(books=>{
        let houses = [] 
        for(let key in books){
            houses.push(books[key])
        }
        for(let i = 0; i < houses.length; i++){
            let name = houses[i].slice(0, 49)
            create(URLS.publishingHouse, {"name":name,
            "address":createAdress()})
        }
    }) 
  }