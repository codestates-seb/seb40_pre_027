const { createProxyMiddleware } = require('http-proxy-middleware');
const url = 'https://636c-175-207-149-117.jp.ngrok.io';
module.exports = (app) => {
  app.use(
    ['/user', '/question', '/answer'],
    createProxyMiddleware({
      target: url,
      changeOrigin: true,
    })
  );
};