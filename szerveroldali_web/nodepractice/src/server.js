const fastify = require('fastify')({logger: true})
const categoryRoutes = require('./category_routes')
const postRoutes = require('./post_routes')

fastify.register(categoryRoutes)
fastify.register(postRoutes)

const start = async () => {
    try {
        await fastify.listen({port: 3000})
    } catch (err) {
        fastify.log.error(err)
        process.exit(1)
    }
}
start()