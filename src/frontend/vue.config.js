module.exports = {
  lintOnSave: true,

  css: {
    loaderOptions: {
      less: {
        javascriptEnabled: true
      }
    }
  },

  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  },

  outputDir: '../main/resources/public/',
  assetsDir: 'static',

  chainWebpack: (config) => {
    // config.plugins.delete('prefetch')
    // config.plugins.delete('preload')
  },

  configureWebpack: {
    performance: {
      maxAssetSize: 50000
    },
    plugins: [
    ]
  },

  devServer: {
    host: '0.0.0.0',
    https: false,
    port: 12222,
    hotOnly: false,
    proxy: {
      '/api': {
        target: 'http://localhost:2222',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        },
        cookieDomainRewrite: {
          '*': 'localhost'
        }
      }
    }
  }
}
