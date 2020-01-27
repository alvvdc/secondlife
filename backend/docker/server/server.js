const mongoose = require('mongoose')
const properties = require('./api/config/properties')
const app = require('./api/miscellaneous/app')


mongoose.Promise = global.Promise
mongoose.connect(properties.dbUrl, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})

const server = app.listen(properties.port, () => {
    const address = server.address().address
    console.log(`Server running in http://${address}:${properties.port}`);
})


