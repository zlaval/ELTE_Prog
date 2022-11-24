const {LoginSchema} = require("./schema")
const {User} =require('../models')

const routes = async (fastify, options) => {

    fastify.post('/login', LoginSchema, async (request, reply) => {
       const user= await User.findOne({where: {email: request.body.email}})
        if(!user){
            return reply.status(404).send({message: 'User not found'})
        }
        const token = fastify.jwt.sign({user})
        reply.send({token})
    });

    fastify.get('/who', {onRequest: [fastify.authenticate]}, async (request,reply) =>{
        reply.send(request.user)
    })

}

module.exports = routes