'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.createTable("CategoryPost", {
            id: {
                allowNull: false,
                autoIncrement: true,
                primaryKey: true,
                type: Sequelize.INTEGER
            },
            CategoryId: {
                type: Sequelize.INTEGER,
                allowNull: false,
                references: {
                    model: "Categories",
                    key: "id"
                },
                onDelete: "cascade"
            },
            PostId: {
                type: Sequelize.INTEGER,
                allowNull: false,
                references: {
                    model: "Posts",
                    key: "id"
                },
                onDelete: "cascade"
            },
            createdAt: {
                allowNull: false,
                type: Sequelize.DATE
            },
            updatedAt: {
                allowNull: false,
                type: Sequelize.DATE
            }
        })
        await queryInterface.addConstraint("CategoryPost",{
            fields: ["CategoryId", "PostId"],
            type: "unique",
        })
    },

    async down(queryInterface, Sequelize) {
        await  queryInterface.dropTable("CategoryPost")
    }
};
