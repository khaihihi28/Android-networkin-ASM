// server.js
const express = require('express');
const axios = require('axios');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3000; // hoặc cổng bạn muốn

app.use(bodyParser.json());
app.use(cors());

// Kết nối tới MongoDB
mongoose.connect('mongodb+srv://khaitqph25638:1234@cluster1.fevxhlz.mongodb.net/test?retryWrites=true&w=majority', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'Lỗi kết nối MongoDB:'));
db.once('open', function () {
    console.log('Đã kết nối thành công đến cơ sở dữ liệu MongoDB!');
});

// Định nghĩa schema và model cho ảnh
const imageSchema = new mongoose.Schema({
    date: String,
    title: String,
    explanation: String,
    url: String,
});
const Image = mongoose.model('Image', imageSchema);

// Route để xử lý yêu cầu từ ứng dụng Android
app.get('/get_image', async (req, res) => {
    const { date } = req.query;
    try {
        const response = await axios.get(`https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=${date}`);
        const { title, explanation, url } = response.data;

        // Lưu thông tin ảnh vào cơ sở dữ liệu MongoDB
        const image = new Image({
            date,
            title,
            explanation,
            url,
        });
        await image.save();

        // Gửi phản hồi về cho ứng dụng Android
        res.json({
            date,
            title,
            explanation,
            url,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Something went wrong.' });
    }
});

app.get('/', function (req, res) {
    res.send('ahihi')
});
// 192.168.1.3
app.listen(port, '192.168.1.3', () => {
    console.log(`Server is running on port ${port}`);
});
