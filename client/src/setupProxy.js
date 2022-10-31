const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
    ['/question', '/user'],
    createProxyMiddleware({
      target: 'https://5b03-175-207-149-117.jp.ngrok.io',
      changeOrigin: true,
    })
  );
};
