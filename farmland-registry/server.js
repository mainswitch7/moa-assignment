const express = require('express');
const app = express();
app.use(express.json());
let registry = [];

app.post('/api/v1/entries', (req, res) => {
  const entry = req.body;
  registry.push(entry);
  console.log("Stored:", entry);
  res.status(200).json({ status: "success", data: entry });
});

app.get('/api/v1/entries', (req, res) => {
  res.status(200).json(registry);
});

// Add a root route
app.get('/', (req, res) => {
  res.status(200).send('Welcome to the Farmland Registry API. Use /api/v1/entries to access the registry.');
});

app.listen(8040, () => console.log("Farmland Registry on 8040"));