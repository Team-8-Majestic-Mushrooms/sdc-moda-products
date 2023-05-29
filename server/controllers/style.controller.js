const models = require('../models');

module.exports.getAll = (req, res) => {
  const productId = req.params.product_id;
  models.style
    .getAll(productId)
    .then((data) => {
      res.status(200).json(data);
    })
    .catch((err) => {
      res.sendStatus(400);
    });
};
