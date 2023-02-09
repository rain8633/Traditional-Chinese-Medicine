const configure = {
  state: {
    HOST: 'http://localhost:9090',
    loginIn: false, // 用户是否登录
    searchword: '', // 搜索关键词
    showAside: false, // 是否显示侧边栏
    activeName: '', // 类型名
    BingLi: {}, // 单个景点信息
    index: 0, // 列表中的序号
    isActive: false,
    searchResult:{}
  },
  getters: {
    isActive: state => {
      let isActive = state.isActive
      if (!isActive) {
        isActive = JSON.parse(window.sessionStorage.getItem('isActive') || null)
      }
      return isActive
    },
    loginIn: state => {
      let loginIn = state.loginIn
      if (!loginIn) {
        loginIn = JSON.parse(window.sessionStorage.getItem('loginIn') || null)
      }
      return loginIn
    },
    activeName: state => {
      let activeName = state.activeName
      if (!activeName) {
        activeName = JSON.parse(window.sessionStorage.getItem('activeName') || null)
      }
      return activeName
    },
    showAside: state => {
      let showAside = state.showAside
      if (!showAside) {
        showAside = JSON.parse(window.sessionStorage.getItem('showAside') || null)
      }
      return showAside
    },
     BingLi: state => {
      let BingLi = state.BingLi
      if (JSON.stringify(BingLi) === '{}') {
        BingLi = JSON.parse(window.sessionStorage.getItem('BingLi') || null)
      }
      return BingLi
    },
    searchResult: state => {
      let searchResult = state.searchResult
      if (JSON.stringify(searchResult) === '{}') {
        searchResult = JSON.parse(window.sessionStorage.getItem('searchResult') || null)
      }
      return searchResult
    },
    index: state => {
      let index = state.index
      if (!index) {
        index = JSON.parse(window.sessionStorage.getItem('index') || null)
      }
      return index
    },
    searchword: state => state.searchword
  },
  mutations: {
    setIsActive: (state, isActive) => {
      state.isActive = isActive
      window.sessionStorage.setItem('isActive', JSON.stringify(isActive))
    },
    setLoginIn: (state, loginIn) => {
      state.loginIn = loginIn
      window.sessionStorage.setItem('loginIn', JSON.stringify(loginIn))
    },
    setActiveName: (state, activeName) => {
      state.activeName = activeName
      window.sessionStorage.setItem('activeName', JSON.stringify(activeName))
    },
    setShowAside: (state, showAside) => {
      state.showAside = showAside
      window.sessionStorage.setItem('showAside', JSON.stringify(showAside))
    },
    setIndex: (state, index) => { state.index = index },
    setSearchword: (state, searchword) => { state.searchword = searchword },
     setBingLi: (state, BingLi) => {
      state.BingLi = BingLi
      window.sessionStorage.setItem('BingLi', JSON.stringify(BingLi))
    },
     setSearchResult: (state, searchResult) => {
      state.searchResult = searchResult
      window.sessionStorage.setItem('searchResult', JSON.stringify(searchResult))
    },
  },

  actions: {}
}

export default configure
