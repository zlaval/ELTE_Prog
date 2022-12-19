### Commands

`npx sequelize` - parancsok

`npx sequelize init` - project init

`npx sequelize model:generate --name User --attributes name:string,email:string,password:string,isAdmin:boolean` - create model

`npx sequelize seed:generate --name DatabaseSeeder` - create db seeder

`npx sequelize model:generate --name Post --attributes title:string,content:string,UserId:integer` - many-to-one to user

`npx sequelize migration:generate --name create-category-post` -- join table

`npx nodemon .\src\server.js`

### DB
```javascript
const db = require('../models')
const {User} = db

;(async () => {
    await User.create({
        name: 'Joe Doe',
        email: 'joedoe@gmail.com',
        password: 'abcd123',
        isAdmin: false
    })

})()
```
##### Queries
```javascript
await User.findAll()
const u = await User.findByPk(1)
await u.getPosts()

```

### Console;
```
node
>> const db = require('./models')
>> const {User, Post} = db
>> await User.findAll()
```

### Faker

##### Unique random items from an array
```javascript
const t = [12, 34, 45, 67]
faker.helpers.unique(()=>{faker.helpers.arrayElement(t)})
```

### GraphQL

```graphql
query{
  hello(name: "Zalan")
}
```

