const {Category, Post, User} = require("../models");
const auth = require("./auth")
const fastify = require("fastify");

const resolvers = {
    Query: {
        hello: async (_, {name}) => `hello ${name || 'word'}`,
        categories: async () => await Category.findAll(),
        posts: async () => await Post.findAll(),
        post: async (_, {id}) => await Post.findByPk(id),
        postByTitle: async (_, {title}) => await Post.findOne({where: {title}}),
        users: async () => await User.findAll(),
        who: auth(async (_parent, _params, context) => await context.user.email),
        token: async (_, {email}, context) => {
            const user = await User.findOne({where: {email}})
            return context.app.jwt.sign(user.toJSON())
        }
    },
    Mutation: {
        createPost: auth(async (_, {data}, context) => {
            data.UserId = context.user.id
            const post = await Post.create(data)

            const addedCategories = []
            const invalidCategories = []

            for (const categoryName of data.categoryNames) {
                const category = await Category.findOne({where: {name: categoryName}})
                if (!category) {
                    invalidCategories.push(categoryName)
                } else {
                    addedCategories.push(category)
                    // await post.addCategory(category)
                }
            }
            await post.setCategories(addedCategories)

            return {
                post,
                invalidCategories
            }
        })
    },
    Post: {
        categories: async (post) => await post.getCategories(),
        user: async (post) => await post.getUser()
    },
    Category: {
        posts: async (category) => await category.getPosts()
    }
}

module.exports = {
    resolvers
}