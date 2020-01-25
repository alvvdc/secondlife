const Category = require('../model/category')
const Product = require('../model/product')

module.exports = {

    insertProduct : (req, res) => {
        const categoryId = req.param.id
        const category = Category.findOne({_id : 1})
        res.json(category)
        //res.json({"id":categoryId})
    }
}