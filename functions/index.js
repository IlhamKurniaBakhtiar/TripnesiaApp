const express = require('express');
const cors = require('cors');
const midtransClient = require('midtrans-client');
const nodemailer = require('nodemailer');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

const snap = new midtransClient.Snap({
  isProduction: false,
  serverKey: process.env.MIDTRANS_SERVER_KEY || 'SB-Mid-server-vWBw6sO1wPbKUeUNPyXIuymH',
});

app.post('/create-transaction', async (req, res) => {
  const { orderId, amount, name, email } = req.body;

  if (!orderId || !amount || !name || !email) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  const parameter = {
    transaction_details: {
      order_id: orderId,
      gross_amount: amount
    },
    customer_details: {
      first_name: name,
      email: email
    }
  };

  try {
    const transaction = await snap.createTransaction(parameter);
    console.log(" Midtrans transaction created:", transaction.token);
    res.json({ token: transaction.token });
  } catch (err) {
    console.error(' Midtrans error:', err.message);
    res.status(500).json({ error: 'Failed to create transaction' });
  }
});

app.post('/send-invoice', async (req, res) => {
  const { name, email, orderId, amount } = req.body;

  if (!email || !orderId || !amount || !name) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  console.log(" Email request received:", { name, email, orderId, amount });
  console.log(" Using EMAIL_USER:", process.env.EMAIL_USER);

  const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
      user: process.env.EMAIL_USER,
      pass: process.env.EMAIL_PASS
    }
  });

  const mailOptions = {
    from: `"Tripnesia" <${process.env.EMAIL_USER}>`,
    to: email,
    subject: `Invoice Tripnesia - ${orderId}`,
    html: `
      <h2>Terima Kasih, ${name}</h2>
      <p>Pesanan Anda dengan ID <strong>${orderId}</strong> telah berhasil.</p>
      <p>Jumlah pembayaran: <strong>Rp${Number(amount).toLocaleString('id-ID')}</strong></p>
      <p>Silakan simpan email ini sebagai bukti transaksi.</p>
      <br><hr><small>Tripnesia Indonesia</small>
    `
  };

  try {
    const info = await transporter.sendMail(mailOptions);
    console.log('Invoice sent:', info.response);
    res.status(200).json({ message: 'Invoice sent successfully' });
  } catch (error) {
    console.error(' Send mail error:', error);
    res.status(500).json({ error: 'Failed to send invoice', detail: error.message });
  }
});

// Run server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(` Server ready on port ${PORT}`);
});
