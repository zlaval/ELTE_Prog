const {Post, User, Category} = require("../models");

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

}

module.exports = routes