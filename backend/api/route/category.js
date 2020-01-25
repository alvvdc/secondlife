const express = require('express')
const router = express.Router()
const categoryController = require('../controller/category')

router.post('/:id/product', categoryController.insertProduct)

module.exports = router