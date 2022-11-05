const { createProxyMiddleware } = require('http-proxy-middleware');
const url = 'https://3bb1-175-207-149-117.jp.ngrok.io/';
module.exports = (app) => {
  app.use(
    ['/user', '/question', '/answer', '/comment', '/reply'],
    createProxyMiddleware({
      target: url,
      changeOrigin: true,
    })
  );
};
