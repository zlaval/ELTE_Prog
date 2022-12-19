module.exports = (fn) => async (parent, params, context, info) => {
    context.user = await context.request.jwtVerify()
    return fn(parent, params, context, info)
}