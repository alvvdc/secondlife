const express = require('express')
const router = express.Router()

const productController = require('../controller/product')
const productVisitsController = require('../controller/productVisits')

router.get('/', productController.getUnsoldProducts)
router.post('/', productController.insertProduct)
router.put('/:id', productController.updateProduct)
router.get('/:category', productController.getUnsoldProductsByCategory)

router.get('/:id/visit', productVisitsController.visitProduct)

module.exports = router