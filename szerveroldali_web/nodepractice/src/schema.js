const idParam = {
    params: {
        type: 'object',
        properties: {
            id: {type: 'integer'}
        }
    }
}

const IdSchema = {
    schema: {
        ...idParam
    }
}

const categoryBody = (requiredFields = []) => {
    return {
        body: {
            type: 'object',
            required: requiredFields,
            properties: {
                name: {type: 'string'},
                hidden: {type: 'boolean'}
            }
        }
    }
}

const CategoryPostSchema = {
    schema: {
        ...categoryBody(['name', 'hidden'])
    }
}

const CategoryPutSchema = {
    schema: {
        ...idParam,
        ...categoryBody(['name', 'hidden'])
    }
}

const CategoryPatchSchema = {
    schema: {
        ...idParam,
        ...categoryBody()
    }
}

const LoginSchema = {
    schema: {
        body: {
            type: 'object',
            required: ['email'],
            properties: {
                email: {type: 'string', format: 'email'}
            }
        }
    }
}

const PostPostSchema = {
    schema: {
        body: {
            type: 'object',
            required: ['title', 'content', 'categories'],
            properties: {
                title: {type: 'string'},
                content: {type: 'string'},
                categories: {type: 'array', items: {type: 'string'}}
            }
        }
    }
}

module.exports = {
    IdSchema,
    CategoryPostSchema,
    CategoryPutSchema,
    CategoryPatchSchema,
    LoginSchema,
    PostPostSchema
}
