const express = require('express')
const router = express.Router()

const productController = require('../controller/product')
const productVisitsController = require('../controller/productVisits')
const tokenProductAuth = require('../miscellaneous/tokenProductAuth')

router.get('/', productController.getUnsoldProducts)
router.post('/', tokenProductAuth, productController.insertProduct)
router.put('/:id', tokenProductAuth, productController.updateProduct)
router.get('/:id', productController.getProductById)
router.delete('/:id', tokenProductAuth, productController.deleteProductById)
router.get('/:category', productController.getUnsoldProductsByCategory)

router.get('/:id/visit', productVisitsController.visitProduct)

module.exports = router