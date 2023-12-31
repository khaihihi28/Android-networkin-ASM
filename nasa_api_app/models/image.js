// models/image.js
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const imageSchema = new Schema({
    date: { type: String, required: true },
    title: { type: String, required: true },
    description: { type: String, required: true },
    imageUrl: { type: String, required: true },
    imageBase64: { type: String, required: true },
});

const Image = mongoose.model('Image', imageSchema);

module.exports = Image;
