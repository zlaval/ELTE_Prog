const fastify = require('fastify')({logger: true})
const jwt = require('@fastify/jwt')
const categoryRoutes = require('./category_routes')
const postRoutes = require('./post_routes')
const userRoutes = require('./user_routes')
const mercurius = require("mercurius");
const {resolvers} = require("./graphql");
const {readFileSync} = require('fs')

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

fastify.register(mercurius, {
    schema: readFileSync("./src/schema.gql").toString(),
    resolvers,
    graphiql: true,
    context: (request) => {
        return {request}
    }
})
const start = async () => {
    try {
        await fastify.listen({port: 3000})
    } catch (err) {
        fastify.log.error(err)
        process.exit(1)
    }
}
start()