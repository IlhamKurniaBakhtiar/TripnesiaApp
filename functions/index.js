const express = require('express');
const cors = require('cors');
const midtransClient = require('midtrans-client');

const app = express();
app.use(cors());
app.use(express.json());

const snap = new midtransClient.Snap({
  isProduction: false,
  serverKey: 'SB-Mid-server-vWBw6sO1wPbKUeUNPyXIuymH',
});

app.post('/create-transaction', async (req, res) => {
  const { orderId, amount, name, email } = req.body;

  const parameter = {
    transaction_details: { order_id: orderId, gross_amount: amount },
    customer_details: { first_name: name, email: email },
  };

  try {
    const transaction = await snap.createTransaction(parameter);
    res.json({ token: transaction.token });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to create transaction' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server ready on port ${PORT}`);
});
