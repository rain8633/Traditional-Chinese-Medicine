module.exports = {
  publicPath: "/",
  // 输出文件目录
  outputDir: "dist",
  // 静态资源存放的文件夹(相对于ouputDir)
  assetsDir: "",
  // eslint-loader 是否在保存的时候检查(果断不用，这玩意儿我都没装)
  lintOnSave: false,
  // 我用的only，打包后小些
  productionSourceMap: true, // 不需要生产环境的设置false可以减小dist文件大小，加速构建
  configureWebpack: {
    resolve: {
      alias: {
        //配置别名
        'assets': '@/assets',
        'common': '@/common',
        'components': '@/components',
        'network': '@/network',
        'views': '@/views',
        'pluginunit': '@/pluginunit'
      }
    }
  },
  devServer: {
    host: '0.0.0.0', //本机电脑 ip 地址
    port: 8081, //端口号
  }
  /*devServer: {
      public: require('os').networkInterfaces()[Object.keys(require('os').networkInterfaces())[0]][1].address + ':8080',
      disableHostCheck: true
  }*/
}
