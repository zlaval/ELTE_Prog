'use strict';
const {
    Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
    class Post extends Model {

        static associate(models) {
            this.belongsTo(models.User)
            this.belongsToMany(models.Category, {through: 'CategoryPost'})
        }
    }

    Post.init({
        title: DataTypes.STRING,
        content: DataTypes.STRING,
        UserId: DataTypes.INTEGER
    }, {
        sequelize, modelName: 'Post',
    });
    return Post;
};