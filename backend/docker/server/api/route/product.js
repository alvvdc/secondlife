const express = require('express')
const router = express.Router()

const productController = require('../controller/product')

router.get('/', productController.getUnsoldProducts)
router.post('/', productController.insertProduct)
router.put('/:id', productController.updateProduct)
router.get('/:category', productController.getUnsoldProductsByCategory)

module.exports = router