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

module.exports = {
    IdSchema,
    CategoryPostSchema,
    CategoryPutSchema,
    CategoryPatchSchema
}
