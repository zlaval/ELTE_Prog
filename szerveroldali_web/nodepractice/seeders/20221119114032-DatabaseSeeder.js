'use strict';
const db = require('../models')
const {faker} = require('@faker-js/faker')
const {User, Post, Category} = db


const generateUsers = async () => {
    const userCount = faker.datatype.number({min: 5, max: 10})
    const users = []
    for (let i = 0; i < userCount; i++) {
        const user = await User.create({
            name: faker.name.fullName(),
            email: faker.internet.email(),
            password: 'abcd123',
            isAdmin: false
        })
        users.push(user)
    }
    return users
}

const generatePosts = async (authors, categories) => {
    const postCount = faker.datatype.number({min: 15, max: 30})
    const posts = []
    for (let i = 0; i < postCount; i++) {
        const post = await Post.create({
            title: faker.lorem.word(),
            content: faker.lorem.paragraph(),
            UserId: faker.helpers.arrayElement(authors).id
        })
        post.setCategories(faker.helpers.arrayElements(categories))
        posts.push(post)
    }
    return posts
}

const generateCategories = async () => {
    const categoryCount = faker.datatype.number({min: 5, max: 15})
    const categories = []
    for (let i = 0; i < categoryCount; i++) {
        const category = await Category.create({
            name: faker.helpers.unique(faker.lorem.word),
            hidden: faker.datatype.boolean()
        })
        categories.push(category)
    }
    return categories
}

/** @type {import('sequelize-cli').Migration} */
module.exports = {
    async up(queryInterface, Sequelize) {
        const users = await generateUsers()
        const categories = await generateCategories()
        const posts = await generatePosts(users, categories)
    },

    async down(queryInterface, Sequelize) {
    }
};
