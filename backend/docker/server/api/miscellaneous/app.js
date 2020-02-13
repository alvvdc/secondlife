const express = require('express')
const bodyParser = require('body-parser')
const userRoutes = require('../route/user')
const productRoutes = require('../route/product')
const app = express()

app.set('secretKey', 'vivaManolo')
app.use(bodyParser.json( {limit: '10mb', extended: true} ))
app.use('/api', userRoutes)
app.use('/api/product/', productRoutes)

module.exports = app
