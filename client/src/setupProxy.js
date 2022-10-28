const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
    '/question',
    createProxyMiddleware({
      target: 'https://b9ac-175-207-149-117.jp.ngrok.io',
      changeOrigin: true,
    })
  );
};
