const fastify = require('fastify')({logger: true})
const jwt = require('@fastify/jwt')
const categoryRoutes = require('./category_routes')
const postRoutes = require('./post_routes')
const userRoutes = require('./user_routes')

fastify.decorate("authenticate", async (request, reply) => {
    try {
        await request.jwtVerify()
    } catch (err) {
        reply.send(err)
    }
})

fastify.register(jwt, {
    secret: 'secret'
})
fastify.register(categoryRoutes)
fastify.register(postRoutes)
fastify.register(userRoutes)

const start = async () => {
    try {
        await fastify.listen({port: 3000})
    } catch (err) {
        fastify.log.error(err)
        process.exit(1)
    }
}
start()