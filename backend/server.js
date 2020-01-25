const express = require('express')
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const app = express()

const categoryRoutes = require('./api/route/category')

mongoose.Promise = global.Promise
mongoose.connect('mongodb://172.17.0.2/secondlife', {useNewUrlParser: true, useUnifiedTopology: true})

app.get('/', (req, res) => {
    res.json({"server" : "ok"})
})

// Middleware
app.use(bodyParser.json())

// Registramos las rutas
app.use('/category', categoryRoutes)


const port = process.env.PORT || 7777

const server = app.listen(port, () => {
    const address = server.address().address
    console.log(`Servidor abierto en http://${address}:${port}`)
})