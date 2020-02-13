const mongoose = require('mongoose')

const productVisits = require('./../model/productVisits')

module.exports = {
    visitProduct : (req, res) => {

        const product = new mongoose.Types.ObjectId(req.params.id)

        if (product) {
            
            productVisits.findOne({product : product}, (err, doc) => {

                if (doc === null) {

                    const newVisit = { product : product }
                    productVisits.create(newVisit, (err, result) => {
                        
                        if (err) 
                            res.status(500).json({error : err})
                        else
                            res.status(201).json(result)
                    })

                } else {

                    const updatedVisit = doc
                    updatedVisit.visits++

                    productVisits.updateOne({product : product}, updatedVisit, (err, result) => {

                        if (err)
                            res.status(500).json({error : err})
                        else
                            res.status(200).json({product : updatedVisit.product, visits : updatedVisit.visits})
                    })
                }
            })

        } else {
            res.status(500).json({error : 'Product id not valid'})
        }
    }
}