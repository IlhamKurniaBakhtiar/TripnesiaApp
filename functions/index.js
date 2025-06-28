const express = require('express');
const cors = require('cors');
const midtransClient = require('midtrans-client');
const nodemailer = require('nodemailer');
require('dotenv').config(); // jika menggunakan .env lokal saat testing

const app = express();
app.use(cors());
app.use(express.json());

// Konfigurasi Midtrans Snap
const snap = new midtransClient.Snap({
  isProduction: false, // ganti true jika sudah live
  serverKey: process.env.MIDTRANS_SERVER_KEY || 'SB-Mid-server-vWBw6sO1wPbKUeUNPyXIuymH',
});

// Endpoint membuat transaksi dan mendapatkan Snap Token
app.post('/create-transaction', async (req, res) => {
  const { orderId, amount, name, email } = req.body;

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
    res.json({ token: transaction.token });
  } catch (err) {
    console.error('Midtrans error:', err.message);
    res.status(500).json({ error: 'Failed to create transaction' });
  }
});

// Endpoint mengirim invoice melalui email
app.post('/send-invoice', async (req, res) => {
  const { name, email, orderId, amount } = req.body;

  if (!email || !orderId || !amount) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
      user: process.env.EMAIL_USER, // harus diset di Railway
      pass: process.env.EMAIL_PASS  // harus diset di Railway
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
    await transporter.sendMail(mailOptions);
    console.log('Invoice sent to', email);
    res.status(200).json({ message: 'Invoice sent successfully' });
  } catch (error) {
    console.error('Send mail error:', error);
    res.status(500).json({ error: 'Failed to send invoice' });
  }
});

// Start server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`✅ Server ready on port ${PORT}`);
});
