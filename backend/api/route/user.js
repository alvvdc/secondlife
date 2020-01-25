const express = require('express')
const router = express.Router()
const userController = require('../controller/user')

router.post('/register', userController.create)
router.post('/login', userController.validate)

module.exports = router