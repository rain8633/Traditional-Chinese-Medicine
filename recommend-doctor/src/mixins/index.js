
import { mapGetters } from 'vuex'

export const mixin = {
  computed: {
  },
  methods: {
    // 提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
     getSong () {
      if (!this.$route.query.keywords) {
        this.$store.commit('setListOfSongs', [])
        this.notify('您输入内容为空', 'warning')
      } else {
        getSongOfSingerName(this.$route.query.keywords)
          .then(res => {
            if (!res.length) {
              this.$store.commit('setListOfSongs', [])
              this.notify('系统暂无该歌曲', 'warning')
            } else {
              this.$store.commit('setListOfSongs', res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  }
}
