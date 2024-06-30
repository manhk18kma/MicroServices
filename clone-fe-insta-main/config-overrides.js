const NodePolyfillPlugin = require('node-polyfill-webpack-plugin');

module.exports = function override(config, env) {
  // Thêm NodePolyfillPlugin vào cấu hình Webpack
  config.plugins.push(new NodePolyfillPlugin());

  // Thiết lập fallback cho module 'net'
  config.resolve.fallback = {
    ...config.resolve.fallback,
    net: false,
  };

  return config;
};
