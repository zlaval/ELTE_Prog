const {Category} = require('../models')
const {
    IdSchema,
    CategoryPostSchema,
    CategoryPutSchema,
    CategoryPatchSchema
} = require("./schema")

const routes = async (fastify, options) => {

    fastify.get('/categories', async (request, reply) => {
        const categories = await Category.findAll()
        reply.send(categories)
    })

    fastify.get('/categories/:id', IdSchema, async (request, reply) => {
        const {id} = request.params
        const category = await Category.findByPk(id)
        if (!category) {
            return reply.status(404).send({message: 'Category not found'})
        }
        return reply.send(category)
    })

    fastify.post('/categories', CategoryPostSchema, async (request, reply) => {
        const categories = await Category.create(request.body)
        reply.status(201).send(categories)
    })

    fastify.put('/categories/:id', CategoryPutSchema, async (request, reply) => {
        const {id} = request.params
        const category = await Category.findByPk(id)
        if (!category)
            return reply.status(404).send({message: 'Category not found'})
        await category.update(request.body)
        reply.send(category)
    })

    fastify.patch('/categories/:id', CategoryPatchSchema, async (request, reply) => {
        const {id} = request.params
        const category = await Category.findByPk(id)
        if (!category)
            return reply.status(404).send({message: 'Category not found'})
        await category.update(request.body)
        reply.send(category)
    })

    fastify.delete('/categories', async (request, reply) => {
        // await Category.destroy({where: []})
        await Category.destroy({truncate: true})
        reply.send({message: 'Deleted'})
    })

    fastify.delete('/categories/:id', IdSchema, async (request, reply) => {
        const {id} = request.params
        const category = await Category.findByPk(id)
        if (!category)
            return reply.status(404).send({message: 'Category not found'})
        await category.destroy()
        return reply.send({message: 'Category deleted'})
    })

}

module.exports = routes