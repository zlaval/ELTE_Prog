const {Post, User, Category} = require("../models");
const {PostPostSchema} = require("./schema")

const excludeTimestamps = {
    attributes: {
        exclude: ['UserId', 'createdAt', 'updatedAt']
    }
}

const routes = async (fastify, options) => {

    fastify.get('/posts', async (request, reply) => {
        const posts = await Post.findAll({
            include: [
                {
                    model: User,
                    ...excludeTimestamps
                },
                {
                    model: Category,
                    through: {
                        attributes: []
                    },
                    ...excludeTimestamps
                }
            ],
            ...excludeTimestamps
        })
        reply.send(posts)
    })

    fastify.post('/posts', {...PostPostSchema, onRequest: [fastify.authenticate]}, async (request, reply) => {
        const {title, content, categories} = request.body
        const post = await Post.create({
            title,
            content,
            UserId: request.user.user.id
        })

        const persistedCategories = []
        for (const categoryName of categories) {
            let category = await Category.findOne({where: {name: categoryName}})
            if (!category) {
                category = await Category.create({
                    name: categoryName,
                    hidden: false
                })
            }
            persistedCategories.push(category)
        }
        post.addCategories(persistedCategories)
        reply.status(201).send({...post.dataValues, persistedCategories})
    })

}

module.exports = routes