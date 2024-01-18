module.exports = {
    dev: {
      "default-src": ["'self'"],
      "style-src": [
        "'self'",
        "http://localhost:3000",
      ],
      "connect-src": [
        "'self'",
        "http://10.20.229.55",
      ]
    }
  };